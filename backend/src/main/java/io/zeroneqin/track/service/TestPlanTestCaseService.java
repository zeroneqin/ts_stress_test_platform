package io.zeroneqin.track.service;

import com.github.pagehelper.PageHelper;
import io.zeroneqin.base.domain.TestPlan;
import io.zeroneqin.base.domain.TestPlanTestCaseExample;
import io.zeroneqin.base.domain.TestPlanTestCaseWithBLOBs;
import io.zeroneqin.base.domain.User;
import io.zeroneqin.base.mapper.TestPlanTestCaseMapper;
import io.zeroneqin.base.mapper.ext.ExtTestPlanTestCaseMapper;
import io.zeroneqin.commons.constants.TestPlanTestCaseStatus;
import io.zeroneqin.commons.user.SessionUser;
import io.zeroneqin.commons.utils.BeanUtils;
import io.zeroneqin.commons.utils.ServiceUtils;
import io.zeroneqin.commons.utils.SessionUtils;
import io.zeroneqin.controller.request.member.QueryMemberRequest;
import io.zeroneqin.service.UserService;
import io.zeroneqin.track.dto.TestPlanCaseDTO;
import io.zeroneqin.track.request.testcase.TestPlanCaseBatchRequest;
import io.zeroneqin.track.request.testplancase.QueryTestPlanCaseRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class TestPlanTestCaseService {

    @Resource
    TestPlanTestCaseMapper testPlanTestCaseMapper;

    @Resource
    UserService userService;

    @Resource
    TestPlanService testPlanService;

    @Resource
    ExtTestPlanTestCaseMapper extTestPlanTestCaseMapper;

    public List<TestPlanCaseDTO> list(QueryTestPlanCaseRequest request) {
        request.setOrders(ServiceUtils.getDefaultOrder(request.getOrders()));
        List<TestPlanCaseDTO> list = extTestPlanTestCaseMapper.list(request);
        QueryMemberRequest queryMemberRequest = new QueryMemberRequest();
        queryMemberRequest.setWorkspaceId(SessionUtils.getCurrentWorkspaceId());
        Map<String, String> userMap = userService.getMemberList(queryMemberRequest)
                .stream().collect(Collectors.toMap(User::getId, User::getName));
        list.forEach(item -> {
            item.setExecutorName(userMap.get(item.getExecutor()));
        });
        return list;
    }
    public List<TestPlanCaseDTO> listByPlanId(QueryTestPlanCaseRequest request) {
        List<TestPlanCaseDTO> list = extTestPlanTestCaseMapper.listByPlanId(request);

        return list;
    }

    public List<TestPlanCaseDTO> listByNode(QueryTestPlanCaseRequest request) {
        List<TestPlanCaseDTO> list = extTestPlanTestCaseMapper.listByNode(request);
        return list;
    }

    public List<TestPlanCaseDTO> listByNodes(QueryTestPlanCaseRequest request) {
        List<TestPlanCaseDTO> list = extTestPlanTestCaseMapper.listByNodes(request);
        return list;
    }

    public void editTestCase(TestPlanTestCaseWithBLOBs testPlanTestCase) {
        if (StringUtils.equals(TestPlanTestCaseStatus.Prepare.name(), testPlanTestCase.getStatus())) {
            testPlanTestCase.setStatus(TestPlanTestCaseStatus.Underway.name());
        }
        testPlanTestCase.setExecutor(SessionUtils.getUser().getId());
        testPlanTestCase.setUpdateTime(System.currentTimeMillis());
        testPlanTestCaseMapper.updateByPrimaryKeySelective(testPlanTestCase);
    }

    public int deleteTestCase(String id) {
        return testPlanTestCaseMapper.deleteByPrimaryKey(id);
    }

    public void editTestCaseBath(TestPlanCaseBatchRequest request) {
        TestPlanTestCaseExample testPlanTestCaseExample = new TestPlanTestCaseExample();
        testPlanTestCaseExample.createCriteria().andIdIn(request.getIds());

        TestPlanTestCaseWithBLOBs testPlanTestCase = new TestPlanTestCaseWithBLOBs();
        BeanUtils.copyBean(testPlanTestCase, request);
        testPlanTestCase.setUpdateTime(System.currentTimeMillis());
        testPlanTestCaseMapper.updateByExampleSelective(
                testPlanTestCase,
                testPlanTestCaseExample);
    }

    public List<TestPlanCaseDTO> getRecentTestCases(QueryTestPlanCaseRequest request, int count) {
        buildQueryRequest(request, count);
        if (request.getPlanIds().isEmpty()) {
            return new ArrayList<>();
        }

        List<TestPlanCaseDTO> recentTestedTestCase = extTestPlanTestCaseMapper.getRecentTestedTestCase(request);
        List<String> planIds = recentTestedTestCase.stream().map(TestPlanCaseDTO::getPlanId).collect(Collectors.toList());

        if (planIds.isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, String> testPlanMap = testPlanService.getTestPlanByIds(planIds).stream()
                .collect(Collectors.toMap(TestPlan::getId, TestPlan::getName));

        recentTestedTestCase.forEach(testCase -> {
            testCase.setPlanName(testPlanMap.get(testCase.getPlanId()));
        });
        return recentTestedTestCase;
    }

    public List<TestPlanCaseDTO> getPendingTestCases(QueryTestPlanCaseRequest request, int count) {
        buildQueryRequest(request, count);
        if (request.getPlanIds().isEmpty()) {
            return new ArrayList<>();
        }
        return extTestPlanTestCaseMapper.getPendingTestCases(request);
    }

    public void buildQueryRequest(QueryTestPlanCaseRequest request, int count) {
        SessionUser user = SessionUtils.getUser();
        List<String> relateTestPlanIds = extTestPlanTestCaseMapper.findRelateTestPlanId(user.getId(), SessionUtils.getCurrentWorkspaceId(), SessionUtils.getCurrentProjectId());
        PageHelper.startPage(1, count, true);
        request.setPlanIds(relateTestPlanIds);
        request.setExecutor(user.getId());
    }

    public TestPlanCaseDTO get(String testplanTestCaseId) {
        return extTestPlanTestCaseMapper.get(testplanTestCaseId);
    }

    public void deleteTestCaseBath(TestPlanCaseBatchRequest request) {
        TestPlanTestCaseExample example = new TestPlanTestCaseExample();
        example.createCriteria().andIdIn(request.getIds());
        testPlanTestCaseMapper.deleteByExample(example);
    }

    public List<String> getTestPlanTestCaseIds(String testId) {
        return extTestPlanTestCaseMapper.getTestPlanTestCaseIds(testId);
    }

    public int updateTestCaseStates(List<String> ids, String reportStatus) {
        return extTestPlanTestCaseMapper.updateTestCaseStates(ids, reportStatus);
    }
}
