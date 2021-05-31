package io.zeroneqin.track.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.zeroneqin.base.domain.Project;
import io.zeroneqin.base.domain.TestPlan;
import io.zeroneqin.commons.constants.RoleConstants;
import io.zeroneqin.commons.utils.PageUtils;
import io.zeroneqin.commons.utils.Pager;
import io.zeroneqin.commons.utils.SessionUtils;
import io.zeroneqin.service.CheckPermissionService;
import io.zeroneqin.track.dto.TestCaseReportMetricDTO;
import io.zeroneqin.track.dto.TestPlanDTO;
import io.zeroneqin.track.dto.TestPlanDTOWithMetric;
import io.zeroneqin.track.request.testcase.PlanCaseRelevanceRequest;
import io.zeroneqin.track.request.testcase.QueryTestPlanRequest;
import io.zeroneqin.track.request.testplan.AddTestPlanRequest;
import io.zeroneqin.track.request.testplancase.TestCaseRelevanceRequest;
import io.zeroneqin.track.service.TestPlanProjectService;
import io.zeroneqin.track.service.TestPlanService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/test/plan")
@RestController
public class TestPlanController {

    @Resource
    TestPlanService testPlanService;
    @Resource
    TestPlanProjectService testPlanProjectService;
    @Resource
    CheckPermissionService checkPermissionService;

    @PostMapping("/list/{goPage}/{pageSize}")
    public Pager<List<TestPlanDTOWithMetric>> list(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody QueryTestPlanRequest request) {
        String currentWorkspaceId = SessionUtils.getCurrentWorkspaceId();
        request.setWorkspaceId(currentWorkspaceId);
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, testPlanService.listTestPlan(request));
    }

    /*jenkins测试计划*/
    @GetMapping("/list/all/{projectId}/{workspaceId}")
    public List<TestPlanDTOWithMetric> listByProjectId(@PathVariable String projectId, @PathVariable String workspaceId) {
        QueryTestPlanRequest request = new QueryTestPlanRequest();
        request.setWorkspaceId(workspaceId);
        request.setProjectId(projectId);
        return testPlanService.listTestPlanByProject(request);
    }

    @PostMapping("/list/all")
    public List<TestPlan> listAll() {
        String currentWorkspaceId = SessionUtils.getCurrentWorkspaceId();
        return testPlanService.listTestAllPlan(currentWorkspaceId);
    }

    @PostMapping("/list/all/relate")
    public List<TestPlanDTOWithMetric> listRelateAll() {
        return testPlanService.listRelateAllPlan();
    }

    @GetMapping("recent/{count}")
    public List<TestPlan> recentTestPlans(@PathVariable int count) {
        String currentWorkspaceId = SessionUtils.getCurrentWorkspaceId();
        PageHelper.startPage(1, count, true);
        return testPlanService.recentTestPlans(currentWorkspaceId);
    }

    @PostMapping("/get/{testPlanId}")
    public TestPlan getTestPlan(@PathVariable String testPlanId) {
        checkPermissionService.checkTestPlanOwner(testPlanId);
        return testPlanService.getTestPlan(testPlanId);
    }

    @PostMapping("/add")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void addTestPlan(@RequestBody AddTestPlanRequest testPlan) {
        testPlanService.addTestPlan(testPlan);
    }

    @PostMapping("/edit")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void editTestPlan(@RequestBody TestPlanDTO testPlanDTO) {
        testPlanService.editTestPlan(testPlanDTO);
    }

    @PostMapping("/edit/status/{planId}")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void editTestPlanStatus(@PathVariable String planId) {
        checkPermissionService.checkTestPlanOwner(planId);
        testPlanService.editTestPlanStatus(planId);
    }

    @PostMapping("/delete/{testPlanId}")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public int deleteTestPlan(@PathVariable String testPlanId) {
        checkPermissionService.checkTestPlanOwner(testPlanId);
        return testPlanService.deleteTestPlan(testPlanId);
    }

    @PostMapping("/relevance")
    public void testPlanRelevance(@RequestBody PlanCaseRelevanceRequest request) {
        testPlanService.testPlanRelevance(request);
    }

    @GetMapping("/get/metric/{planId}")
    public TestCaseReportMetricDTO getMetric(@PathVariable String planId) {
        return testPlanService.getMetric(planId);
    }

    @GetMapping("/get/statistics/metric/{planId}")
    public TestCaseReportMetricDTO getStatisticsMetric(@PathVariable String planId) {
        return testPlanService.getStatisticsMetric(planId);
    }

    @GetMapping("/project/name/{planId}")
    public String getProjectNameByPlanId(@PathVariable String planId) {
        return testPlanService.getProjectNameByPlanId(planId);
    }

    @PostMapping("/project")
    public List<Project> getProjectByPlanId(@RequestBody TestCaseRelevanceRequest request) {
        List<String> projectIds = testPlanProjectService.getProjectIdsByPlanId(request.getPlanId());
        request.setProjectIds(projectIds);
        return testPlanProjectService.getProjectByPlanId(request);
    }

    @PostMapping("/project/{goPage}/{pageSize}")
    public Pager<List<Project>> getProjectByPlanId(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody TestCaseRelevanceRequest request) {
        List<String> projectIds = testPlanProjectService.getProjectIdsByPlanId(request.getPlanId());
        request.setProjectIds(projectIds);
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, testPlanProjectService.getProjectByPlanId(request));
    }
}
