package io.zeroneqin.service;

import io.zeroneqin.api.dto.DeleteAPITestRequest;
import io.zeroneqin.api.dto.QueryAPITestRequest;
import io.zeroneqin.api.service.APITestService;
import io.zeroneqin.base.domain.LoadTest;
import io.zeroneqin.base.domain.LoadTestExample;
import io.zeroneqin.base.domain.Project;
import io.zeroneqin.base.domain.ProjectExample;
import io.zeroneqin.base.mapper.LoadTestMapper;
import io.zeroneqin.base.mapper.ProjectMapper;
import io.zeroneqin.base.mapper.ext.*;
import io.zeroneqin.commons.exception.MSException;
import io.zeroneqin.commons.utils.ServiceUtils;
import io.zeroneqin.commons.utils.SessionUtils;
import io.zeroneqin.controller.request.ProjectRequest;
import io.zeroneqin.dto.ProjectDTO;
import io.zeroneqin.i18n.Translator;
import io.zeroneqin.performance.service.PerformanceTestService;
import io.zeroneqin.track.request.testplan.DeleteTestPlanRequest;
import io.zeroneqin.track.service.TestCaseService;
import io.zeroneqin.track.service.TestPlanProjectService;
import io.zeroneqin.track.service.TestPlanService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectService {
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ExtProjectMapper extProjectMapper;
    @Resource
    private PerformanceTestService performanceTestService;
    @Resource
    private LoadTestMapper loadTestMapper;
    @Resource
    private ExtTestCaseMapper extTestCaseMapper;
    @Resource
    private ExtTestPlanMapper extTestPlanMapper;
    @Resource
    private ExtLoadTestMapper extLoadTestMapperMapper;
    @Resource
    private ExtApiTestMapper extApiTestMapper;
    @Resource
    private TestPlanService testPlanService;
    @Resource
    private TestCaseService testCaseService;
    @Resource
    private APITestService apiTestService;
    @Resource
    private TestPlanProjectService testPlanProjectService;

    public Project addProject(Project project) {
        if (StringUtils.isBlank(project.getName())) {
            MSException.throwException(Translator.get("project_name_is_null"));
        }
        ProjectExample example = new ProjectExample();
        example.createCriteria()
                .andOrganizationIdEqualTo(SessionUtils.getCurrentOrganizationId())
                .andNameEqualTo(project.getName());
        if (projectMapper.countByExample(example) > 0) {
            MSException.throwException(Translator.get("project_name_already_exists"));
        }
        project.setId(UUID.randomUUID().toString());
        long createTime = System.currentTimeMillis();
        project.setCreateTime(createTime);
        project.setUpdateTime(createTime);
        // set workspace id
        project.setOrganizationId(SessionUtils.getCurrentOrganizationId());
        projectMapper.insertSelective(project);
        return project;
    }

    public List<ProjectDTO> getProjectList(ProjectRequest request) {
        if (StringUtils.isNotBlank(request.getName())) {
            request.setName(StringUtils.wrapIfMissing(request.getName(), "%"));
        }
        request.setOrders(ServiceUtils.getDefaultOrder(request.getOrders()));
        return extProjectMapper.getProjectWithWorkspace(request);
    }

    public void deleteProject(String projectId) {
        // delete test
        LoadTestExample loadTestExample = new LoadTestExample();
        loadTestExample.createCriteria().andProjectIdEqualTo(projectId);
        List<LoadTest> loadTests = loadTestMapper.selectByExample(loadTestExample);
        List<String> loadTestIdList = loadTests.stream().map(LoadTest::getId).collect(Collectors.toList());
        loadTestIdList.forEach(loadTestId -> {
            DeleteTestPlanRequest deleteTestPlanRequest = new DeleteTestPlanRequest();
            deleteTestPlanRequest.setId(loadTestId);
            deleteTestPlanRequest.setForceDelete(true);
            performanceTestService.delete(deleteTestPlanRequest);
        });

        // 删除项目下 测试跟踪 相关
        deleteTrackResourceByProjectId(projectId);

        // 删除项目下 接口测试 相关
        deleteAPIResourceByProjectId(projectId);
        // delete project
        projectMapper.deleteByPrimaryKey(projectId);
    }

    private void deleteTrackResourceByProjectId(String projectId) {
        List<String> testPlanIds = testPlanProjectService.getPlanIdByProjectId(projectId);
        if (!CollectionUtils.isEmpty(testPlanIds)) {
            testPlanIds.forEach(testPlanId -> {
                testPlanService.deleteTestPlan(testPlanId);
            });
        }
        testCaseService.deleteTestCaseByProjectId(projectId);
    }

    private void deleteAPIResourceByProjectId(String projectId) {
        QueryAPITestRequest request = new QueryAPITestRequest();
        request.setProjectId(projectId);
        apiTestService.list(request).forEach(test -> {
            DeleteAPITestRequest deleteAPITestRequest = new DeleteAPITestRequest();
            deleteAPITestRequest.setId(test.getId());
            deleteAPITestRequest.setForceDelete(true);
            apiTestService.delete(deleteAPITestRequest);
        });
    }

    public void updateProject(Project project) {
        project.setCreateTime(null);
        project.setUpdateTime(System.currentTimeMillis());
        checkProjectExist(project);
        projectMapper.updateByPrimaryKeySelective(project);
    }

    private void checkProjectExist(Project project) {
        if (project.getName() != null) {
            ProjectExample example = new ProjectExample();
            example.createCriteria()
                    .andNameEqualTo(project.getName())
                    .andWorkspaceIdEqualTo(SessionUtils.getCurrentWorkspaceId())
                    .andIdNotEqualTo(project.getId());
            if (projectMapper.selectByExample(example).size() > 0) {
                MSException.throwException(Translator.get("project_name_already_exists"));
            }
        }
    }

    public List<Project> listAll() {
        return projectMapper.selectByExample(null);
    }

    public List<Project> getRecentProjectList(ProjectRequest request) {
        ProjectExample example = new ProjectExample();
        ProjectExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(request.getOrganizationId())) {
            criteria.andOrganizationIdEqualTo(request.getOrganizationId());
        }
        // 按照修改时间排序
        example.setOrderByClause("update_time desc");
        return projectMapper.selectByExample(example);
    }

    public Project getProjectById(String id) {
        return projectMapper.selectByPrimaryKey(id);
    }
}
