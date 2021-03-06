package io.zeroneqin.api.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.zeroneqin.api.dto.ApiCaseBatchRequest;
import io.zeroneqin.api.dto.datacount.ApiDataCountResult;
import io.zeroneqin.api.dto.definition.*;
import io.zeroneqin.api.dto.definition.request.MsTestElement;
import io.zeroneqin.api.dto.definition.request.MsTestPlan;
import io.zeroneqin.api.dto.definition.request.MsThreadGroup;
import io.zeroneqin.api.dto.definition.request.ParameterConfig;
import io.zeroneqin.api.jmeter.JMeterService;
import io.zeroneqin.base.domain.*;
import io.zeroneqin.base.mapper.ApiDefinitionExecResultMapper;
import io.zeroneqin.base.mapper.ApiDefinitionMapper;
import io.zeroneqin.base.mapper.ApiTestCaseMapper;
import io.zeroneqin.base.mapper.ApiTestFileMapper;
import io.zeroneqin.base.mapper.ext.ExtApiDefinitionExecResultMapper;
import io.zeroneqin.base.mapper.ext.ExtApiTestCaseMapper;
import io.zeroneqin.base.mapper.ext.ExtTestPlanApiCaseMapper;
import io.zeroneqin.base.mapper.ext.ExtTestPlanTestCaseMapper;
import io.zeroneqin.commons.constants.ApiRunMode;
import io.zeroneqin.commons.exception.MSException;
import io.zeroneqin.commons.utils.*;
import io.zeroneqin.i18n.Translator;
import io.zeroneqin.service.FileService;
import io.zeroneqin.service.QuotaService;
import io.zeroneqin.service.UserService;
import io.zeroneqin.track.request.testcase.ApiCaseRelevanceRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ApiTestCaseService {
    @Resource
    private ApiTestCaseMapper apiTestCaseMapper;
    @Resource
    private SqlSessionFactory sqlSessionFactory;
    @Resource
    private UserService userService;
    @Resource
    private ExtApiTestCaseMapper extApiTestCaseMapper;
    @Resource
    private ApiTestFileMapper apiTestFileMapper;
    @Resource
    private ExtTestPlanTestCaseMapper extTestPlanTestCaseMapper;
    @Resource
    private FileService fileService;
    @Resource
    private ExtApiDefinitionExecResultMapper extApiDefinitionExecResultMapper;
    @Resource
    private ApiDefinitionMapper apiDefinitionMapper;
    @Resource
    private JMeterService jMeterService;
    @Resource
    private ApiDefinitionExecResultMapper  apiDefinitionExecResultMapper;

    private static final String BODY_FILE_DIR = "/opt/zeroneqin/data/body";

    public List<ApiTestCaseResult> list(ApiTestCaseRequest request) {
        request.setOrders(ServiceUtils.getDefaultOrder(request.getOrders()));
        return extApiTestCaseMapper.list(request);
    }

    public List<ApiTestCaseDTO> listSimple(ApiTestCaseRequest request) {
        request.setOrders(ServiceUtils.getDefaultOrder(request.getOrders()));
        List<ApiTestCaseDTO> apiTestCases = extApiTestCaseMapper.listSimple(request);
        if (CollectionUtils.isEmpty(apiTestCases)) {
            return apiTestCases;
        }
        buildUserInfo(apiTestCases);
        return apiTestCases;
    }

    public void buildUserInfo(List<? extends ApiTestCaseDTO> apiTestCases) {
        List<String> userIds = new ArrayList();
        userIds.addAll(apiTestCases.stream().map(ApiTestCaseDTO::getCreateUserId).collect(Collectors.toList()));
        userIds.addAll(apiTestCases.stream().map(ApiTestCaseDTO::getUpdateUserId).collect(Collectors.toList()));
        if (!CollectionUtils.isEmpty(userIds)) {
            Map<String, User> userMap = userService.queryNameByIds(userIds);
            apiTestCases.forEach(caseResult -> {
                caseResult.setCreateUser(userMap.get(caseResult.getCreateUserId()).getName());
                caseResult.setUpdateUser(userMap.get(caseResult.getUpdateUserId()).getName());
            });
        }
    }

    public ApiTestCaseWithBLOBs get(String id) {
        return apiTestCaseMapper.selectByPrimaryKey(id);
    }

    public void create(SaveApiTestCaseRequest request, List<MultipartFile> bodyFiles) {
        List<String> bodyUploadIds = new ArrayList<>(request.getBodyUploadIds());
        ApiTestCase test = createTest(request);
        createBodyFiles(test, bodyUploadIds, bodyFiles);
    }

    private void checkQuota() {
        QuotaService quotaService = CommonBeanFactory.getBean(QuotaService.class);
        if (quotaService != null) {
            quotaService.checkAPITestQuota();
        }
    }

    public void update(SaveApiTestCaseRequest request, List<MultipartFile> bodyFiles) {

        deleteFileByTestId(request.getId());
        List<String> bodyUploadIds = new ArrayList<>(request.getBodyUploadIds());
        request.setBodyUploadIds(null);
        ApiTestCase test = updateTest(request);
        createBodyFiles(test, bodyUploadIds, bodyFiles);
    }

    private void createBodyFiles(ApiTestCase test, List<String> bodyUploadIds, List<MultipartFile> bodyFiles) {
        if (bodyUploadIds.size() > 0) {
            String dir = BODY_FILE_DIR + "/" + test.getId();
            File testDir = new File(dir);
            if (!testDir.exists()) {
                testDir.mkdirs();
            }
            for (int i = 0; i < bodyUploadIds.size(); i++) {
                MultipartFile item = bodyFiles.get(i);
                File file = new File(testDir + "/" + bodyUploadIds.get(i) + "_" + item.getOriginalFilename());
                try (InputStream in = item.getInputStream(); OutputStream out = new FileOutputStream(file)) {
                    file.createNewFile();
                    FileUtil.copyStream(in, out);
                } catch (IOException e) {
                    LogUtil.error(e);
                    MSException.throwException(Translator.get("upload_fail"));
                }
            }
        }
    }

    public void delete(String testId) {
        extTestPlanTestCaseMapper.deleteByTestCaseID(testId);
        deleteFileByTestId(testId);
        extApiDefinitionExecResultMapper.deleteByResourceId(testId);
        apiTestCaseMapper.deleteByPrimaryKey(testId);
        deleteBodyFiles(testId);
    }

    public void deleteTestCase(String apiId) {
        ApiTestCaseExample testCaseExample = new ApiTestCaseExample();
        testCaseExample.createCriteria().andApiDefinitionIdEqualTo(apiId);
        List<ApiTestCase> testCases = apiTestCaseMapper.selectByExample(testCaseExample);
        if (testCases.size() > 0) {
            for (ApiTestCase testCase : testCases) {
                this.delete(testCase.getId());
            }
        }
    }

    /**
     * ?????????????????????????????????
     */
    public void checkIsRelateTest(String apiId) {
        ApiTestCaseExample testCaseExample = new ApiTestCaseExample();
        testCaseExample.createCriteria().andApiDefinitionIdEqualTo(apiId);
        List<ApiTestCase> testCases = apiTestCaseMapper.selectByExample(testCaseExample);
        StringBuilder caseName = new StringBuilder();
        if (testCases.size() > 0) {
            for (ApiTestCase testCase : testCases) {
                caseName = caseName.append(testCase.getName()).append(",");
            }
            String str = caseName.toString().substring(0, caseName.length() - 1);
            MSException.throwException(Translator.get("related_case_del_fail_prefix") + " " + str + " " + Translator.get("related_case_del_fail_suffix"));
        }
    }

    public void deleteBodyFiles(String testId) {
        File file = new File(BODY_FILE_DIR + "/" + testId);
        FileUtil.deleteContents(file);
        if (file.exists()) {
            file.delete();
        }
    }

    private void checkNameExist(SaveApiTestCaseRequest request) {
        ApiTestCaseExample example = new ApiTestCaseExample();
        example.createCriteria().andNameEqualTo(request.getName()).andApiDefinitionIdEqualTo(request.getApiDefinitionId()).andIdNotEqualTo(request.getId());
        if (apiTestCaseMapper.countByExample(example) > 0) {
            MSException.throwException(Translator.get("load_test_already_exists"));
        }
    }


    private ApiTestCase updateTest(SaveApiTestCaseRequest request) {
        checkNameExist(request);
        final ApiTestCaseWithBLOBs test = new ApiTestCaseWithBLOBs();
        test.setId(request.getId());
        test.setName(request.getName());
        test.setApiDefinitionId(request.getApiDefinitionId());
        test.setUpdateUserId(Objects.requireNonNull(SessionUtils.getUser()).getId());
        test.setProjectId(request.getProjectId());
        test.setRequest(JSONObject.toJSONString(request.getRequest()));
        test.setResponse(JSONObject.toJSONString(request.getResponse()));
        test.setPriority(request.getPriority());
        test.setUpdateTime(System.currentTimeMillis());
        test.setDescription(request.getDescription());
        apiTestCaseMapper.updateByPrimaryKeySelective(test);
        return test;
    }

    private ApiTestCase createTest(SaveApiTestCaseRequest request) {
        request.setId(UUID.randomUUID().toString());
        checkNameExist(request);
        final ApiTestCaseWithBLOBs test = new ApiTestCaseWithBLOBs();
        test.setId(request.getId());
        test.setName(request.getName());
        test.setApiDefinitionId(request.getApiDefinitionId());
        test.setCreateUserId(Objects.requireNonNull(SessionUtils.getUser()).getId());
        test.setUpdateUserId(Objects.requireNonNull(SessionUtils.getUser()).getId());
        test.setProjectId(request.getProjectId());
        test.setRequest(JSONObject.toJSONString(request.getRequest()));
        test.setResponse(JSONObject.toJSONString(request.getResponse()));
        test.setCreateTime(System.currentTimeMillis());
        test.setPriority(request.getPriority());
        test.setUpdateTime(System.currentTimeMillis());
        test.setDescription(request.getDescription());
        test.setNum(getNextNum(request.getApiDefinitionId()));
        apiTestCaseMapper.insert(test);
        return test;
    }

    private int getNextNum(String definitionId) {
        ApiTestCase apiTestCase = extApiTestCaseMapper.getNextNum(definitionId);
        if (apiTestCase == null) {
            int n = apiDefinitionMapper.selectByPrimaryKey(definitionId).getNum();
            return n * 1000 + 1;
        } else {
            return Optional.of(apiTestCase.getNum() + 1)
                    .orElse(apiDefinitionMapper.selectByPrimaryKey(definitionId).getNum() * 1000 + 1);
        }
    }

    private void saveFile(String testId, MultipartFile file) {
        final FileMetadata fileMetadata = fileService.saveFile(file);
        ApiTestFile apiTestFile = new ApiTestFile();
        apiTestFile.setTestId(testId);
        apiTestFile.setFileId(fileMetadata.getId());
        apiTestFileMapper.insert(apiTestFile);
    }

    private void deleteFileByTestId(String testId) {
        ApiTestFileExample ApiTestFileExample = new ApiTestFileExample();
        ApiTestFileExample.createCriteria().andTestIdEqualTo(testId);
        final List<ApiTestFile> ApiTestFiles = apiTestFileMapper.selectByExample(ApiTestFileExample);
        apiTestFileMapper.deleteByExample(ApiTestFileExample);

        if (!CollectionUtils.isEmpty(ApiTestFiles)) {
            final List<String> fileIds = ApiTestFiles.stream().map(ApiTestFile::getFileId).collect(Collectors.toList());
            fileService.deleteFileByIds(fileIds);
        }
    }

    public void removeToGc(List<String> ids) {
        // todo
    }

    public void editApiBath(ApiCaseBatchRequest request) {
        ApiTestCaseExample apiDefinitionExample = new ApiTestCaseExample();
        apiDefinitionExample.createCriteria().andIdIn(request.getIds());
        ApiTestCaseWithBLOBs apiDefinitionWithBLOBs = new ApiTestCaseWithBLOBs();
        BeanUtils.copyBean(apiDefinitionWithBLOBs, request);
        apiDefinitionWithBLOBs.setUpdateTime(System.currentTimeMillis());
        apiTestCaseMapper.updateByExampleSelective(apiDefinitionWithBLOBs, apiDefinitionExample);
    }

    public void deleteBatch(List<String> ids) {
        for (String testId : ids) {
            extTestPlanTestCaseMapper.deleteByTestCaseID(testId);
        }
        ApiTestCaseExample example = new ApiTestCaseExample();
        example.createCriteria().andIdIn(ids);
        apiTestCaseMapper.deleteByExample(example);
    }

    public void relevanceByApi(ApiCaseRelevanceRequest request) {
        if (CollectionUtils.isEmpty(request.getSelectIds())) {
            return;
        }
        ApiTestCaseExample example = new ApiTestCaseExample();
        example.createCriteria().andApiDefinitionIdIn(request.getSelectIds());
        List<ApiTestCase> apiTestCases = apiTestCaseMapper.selectByExample(example);
        relevance(apiTestCases, request);
    }

    public void relevanceByCase(ApiCaseRelevanceRequest request) {
        List<String> ids = request.getSelectIds();
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        ApiTestCaseExample example = new ApiTestCaseExample();
        example.createCriteria().andIdIn(ids);
        List<ApiTestCase> apiTestCases = apiTestCaseMapper.selectByExample(example);
        relevance(apiTestCases, request);
    }

    private void relevance(List<ApiTestCase> apiTestCases, ApiCaseRelevanceRequest request) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        ExtTestPlanApiCaseMapper batchMapper = sqlSession.getMapper(ExtTestPlanApiCaseMapper.class);
        apiTestCases.forEach(apiTestCase -> {
            TestPlanApiCase testPlanApiCase = new TestPlanApiCase();
            testPlanApiCase.setId(UUID.randomUUID().toString());
            testPlanApiCase.setApiCaseId(apiTestCase.getId());
            testPlanApiCase.setTestPlanId(request.getPlanId());
            testPlanApiCase.setEnvironmentId(request.getEnvironmentId());
            testPlanApiCase.setCreateTime(System.currentTimeMillis());
            testPlanApiCase.setUpdateTime(System.currentTimeMillis());
            batchMapper.insertIfNotExists(testPlanApiCase);
        });
        sqlSession.flushStatements();
    }

    public List<String> selectIdsNotExistsInPlan(String projectId, String planId) {
        return extApiTestCaseMapper.selectIdsNotExistsInPlan(projectId, planId);
    }

    public List<ApiDataCountResult> countProtocolByProjectID(String projectId) {
        return extApiTestCaseMapper.countProtocolByProjectID(projectId);
    }

    public long countByProjectIDAndCreateInThisWeek(String projectId) {
        Map<String, Date> startAndEndDateInWeek = DateUtils.getWeedFirstTimeAndLastTime(new Date());

        Date firstTime = startAndEndDateInWeek.get("firstTime");
        Date lastTime = startAndEndDateInWeek.get("lastTime");

        if (firstTime == null || lastTime == null) {
            return 0;
        } else {
            return extApiTestCaseMapper.countByProjectIDAndCreateInThisWeek(projectId, firstTime.getTime(), lastTime.getTime());
        }
    }

    public List<ApiTestCase> selectCasesBydIds(List<String> caseIds) {
        ApiTestCaseExample example = new ApiTestCaseExample();
        example.createCriteria().andIdIn(caseIds);
        return apiTestCaseMapper.selectByExample(example);
    }

    public Map<String, String> getRequest(ApiTestCaseRequest request) {
        List<ApiTestCaseWithBLOBs> list = extApiTestCaseMapper.getRequest(request);
        return list.stream().collect(Collectors.toMap(ApiTestCaseWithBLOBs::getId, ApiTestCaseWithBLOBs::getRequest));
    }

    public void deleteBatchByParam(ApiTestBatchRequest request) {
        List<String> ids = request.getIds();
        if (request.isSelectAllDate()) {
            ids = this.getAllApiCaseIdsByFontedSelect(request.getFilters(), request.getModuleIds(), request.getName(), request.getProjectId(), request.getProtocol(), request.getUnSelectIds(), request.getStatus());
        }
        this.deleteBatch(ids);
    }

    public void editApiBathByParam(ApiTestBatchRequest request) {
        List<String> ids = request.getIds();
        if (request.isSelectAllDate()) {
            ids = this.getAllApiCaseIdsByFontedSelect(request.getFilters(), request.getModuleIds(), request.getName(), request.getProjectId(), request.getProtocol(), request.getUnSelectIds(), request.getStatus());
        }
        request.cleanSelectParam();
        ApiTestCaseExample apiDefinitionExample = new ApiTestCaseExample();
        apiDefinitionExample.createCriteria().andIdIn(ids);
        ApiTestCaseWithBLOBs apiDefinitionWithBLOBs = new ApiTestCaseWithBLOBs();
        BeanUtils.copyBean(apiDefinitionWithBLOBs, request);
        apiDefinitionWithBLOBs.setUpdateTime(System.currentTimeMillis());
        apiTestCaseMapper.updateByExampleSelective(apiDefinitionWithBLOBs, apiDefinitionExample);
    }

    private List<String> getAllApiCaseIdsByFontedSelect(Map<String, List<String>> filters, List<String> moduleIds, String name, String projectId, String protocol, List<String> unSelectIds, String status) {
        ApiTestCaseRequest selectRequest = new ApiTestCaseRequest();
        selectRequest.setFilters(filters);
        selectRequest.setModuleIds(moduleIds);
        selectRequest.setName(name);
        selectRequest.setProjectId(projectId);
        selectRequest.setProtocol(protocol);
        selectRequest.setStatus(status);
        selectRequest.setWorkspaceId(SessionUtils.getCurrentWorkspaceId());
        List<ApiTestCaseResult> list = extApiTestCaseMapper.list(selectRequest);
        List<String> allIds = list.stream().map(ApiTestCaseResult::getId).collect(Collectors.toList());
        List<String> ids = allIds.stream().filter(id -> !unSelectIds.contains(id)).collect(Collectors.toList());
        return ids;
    }

    public String run(RunCaseRequest request) {
        ApiTestCaseWithBLOBs testCaseWithBLOBs = apiTestCaseMapper.selectByPrimaryKey(request.getCaseId());
        // ??????JSON?????????????????????????????????????????? ObjectMapper ??????
        if (testCaseWithBLOBs != null && StringUtils.isNotEmpty(testCaseWithBLOBs.getRequest())) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                MsTestElement element = mapper.readValue(testCaseWithBLOBs.getRequest(), new TypeReference<MsTestElement>() {
                });
                element.setName(request.getCaseId());
                // ????????????
                MsTestPlan testPlan = new MsTestPlan();
                testPlan.setHashTree(new LinkedList<>());
                HashTree jmeterHashTree = new ListedHashTree();

                // ?????????
                MsThreadGroup group = new MsThreadGroup();
                group.setLabel(testCaseWithBLOBs.getName());
                group.setName(testCaseWithBLOBs.getId());

                LinkedList<MsTestElement> hashTrees = new LinkedList<>();
                hashTrees.add(element);
                group.setHashTree(hashTrees);
                testPlan.getHashTree().add(group);

                testPlan.toHashTree(jmeterHashTree, testPlan.getHashTree(), new ParameterConfig());

                String runMode = ApiRunMode.DELIMIT.name();
                // ??????????????????
                jMeterService.runDefinition(request.getReportId(), jmeterHashTree, request.getReportId(), runMode);

            } catch (Exception ex) {
                LogUtil.error(ex.getMessage());
            }
        }
        return request.getReportId();
    }

    public String getExecResult(String id){
        ApiDefinitionExecResultExample apidefinitionexecresultexample = new ApiDefinitionExecResultExample();
        ApiDefinitionExecResultExample.Criteria criteria = apidefinitionexecresultexample.createCriteria();
        criteria.andResourceIdEqualTo(id);
        String status=apiDefinitionExecResultMapper.selectByExample(apidefinitionexecresultexample).get(0).getStatus();
        return status;
    }
}
