package io.zeroneqin.track.service;

import io.zeroneqin.api.dto.automation.ApiScenarioDTO;
import io.zeroneqin.api.dto.automation.ApiScenarioRequest;
import io.zeroneqin.api.dto.automation.RunScenarioRequest;
import io.zeroneqin.api.dto.automation.TestPlanScenarioRequest;
import io.zeroneqin.api.service.ApiAutomationService;
import io.zeroneqin.api.service.ApiScenarioReportService;
import io.zeroneqin.base.domain.TestPlanApiScenario;
import io.zeroneqin.base.domain.TestPlanApiScenarioExample;
import io.zeroneqin.base.mapper.TestPlanApiScenarioMapper;
import io.zeroneqin.base.mapper.ext.ExtTestPlanScenarioCaseMapper;
import io.zeroneqin.commons.constants.ApiRunMode;
import io.zeroneqin.commons.utils.ServiceUtils;
import io.zeroneqin.track.request.testcase.TestPlanApiCaseBatchRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class TestPlanScenarioCaseService {

    @Resource
    ApiAutomationService apiAutomationService;
    @Resource
    TestPlanApiScenarioMapper testPlanApiScenarioMapper;
    @Resource
    ExtTestPlanScenarioCaseMapper extTestPlanScenarioCaseMapper;
    @Resource
    ApiScenarioReportService apiScenarioReportService;

    public List<ApiScenarioDTO> list(TestPlanScenarioRequest request) {
        request.setProjectId(null);
        request.setOrders(ServiceUtils.getDefaultOrder(request.getOrders()));
        List<ApiScenarioDTO> apiTestCases = extTestPlanScenarioCaseMapper.list(request);
        if (CollectionUtils.isEmpty(apiTestCases)) {
            return apiTestCases;
        }
        return apiTestCases;
    }

    public List<ApiScenarioDTO> relevanceList(ApiScenarioRequest request) {
        List<String> ids = apiAutomationService.selectIdsNotExistsInPlan(request.getProjectId(), request.getPlanId());
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        request.setIds(ids);
        return apiAutomationService.list(request);
    }

    public int delete(String id) {
        TestPlanApiScenario testPlanApiScenario = testPlanApiScenarioMapper.selectByPrimaryKey(id);
        String reportId = testPlanApiScenario.getReportId();
        if (!StringUtils.isEmpty(reportId)) {
            apiScenarioReportService.delete(reportId);
        }
        TestPlanApiScenarioExample example = new TestPlanApiScenarioExample();
        example.createCriteria()
                .andIdEqualTo(id);

        return testPlanApiScenarioMapper.deleteByExample(example);
    }

    public void deleteApiCaseBath(TestPlanApiCaseBatchRequest request) {
        if (CollectionUtils.isEmpty(request.getIds())) {
            return;
        }
        TestPlanApiScenarioExample example = new TestPlanApiScenarioExample();
        example.createCriteria()
                .andIdIn(request.getIds());
        List<String> reportIds = testPlanApiScenarioMapper.selectByExample(example).stream()
                .map(TestPlanApiScenario::getReportId).collect(Collectors.toList());
        apiScenarioReportService.deleteByIds(reportIds);
        testPlanApiScenarioMapper.deleteByExample(example);
    }

    public String run(RunScenarioRequest request) {
        TestPlanApiScenarioExample example = new TestPlanApiScenarioExample();
        example.createCriteria().andIdIn(request.getPlanCaseIds());
        List<String> scenarioIds = testPlanApiScenarioMapper.selectByExample(example).stream()
                .map(TestPlanApiScenario::getApiScenarioId)
                .collect(Collectors.toList());
        scenarioIds.addAll(scenarioIds);
        request.setScenarioIds(scenarioIds);
        request.setRunMode(ApiRunMode.SCENARIO_PLAN.name());
        return apiAutomationService.run(request);
    }

    public List<TestPlanApiScenario> getCasesByPlanId(String planId) {
        TestPlanApiScenarioExample example = new TestPlanApiScenarioExample();
        example.createCriteria().andTestPlanIdEqualTo(planId);
        return testPlanApiScenarioMapper.selectByExample(example);
    }

    public List<String> getExecResultByPlanId(String planId) {
        return extTestPlanScenarioCaseMapper.getExecResultByPlanId(planId);
    }

    public void deleteByPlanId(String planId) {
        TestPlanApiCaseBatchRequest request = new TestPlanApiCaseBatchRequest();
        List<String> ids = extTestPlanScenarioCaseMapper.getIdsByPlanId(planId);
        request.setIds(ids);
        deleteApiCaseBath(request);
    }

    public void deleteByRelevanceProjectIds(String planId, List<String> relevanceProjectIds) {
        TestPlanApiCaseBatchRequest request = new TestPlanApiCaseBatchRequest();
        request.setIds(extTestPlanScenarioCaseMapper.getNotRelevanceCaseIds(planId, relevanceProjectIds));
        request.setPlanId(planId);
        deleteApiCaseBath(request);
    }
}
