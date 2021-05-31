package io.zeroneqin.track.controller;

import io.zeroneqin.base.domain.TestCaseReport;
import io.zeroneqin.commons.constants.RoleConstants;
import io.zeroneqin.track.request.testCaseReport.CreateReportRequest;
import io.zeroneqin.track.service.TestCaseReportService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/case/report")
@RestController
public class TestCaseReportController {

    @Resource
    TestCaseReportService testCaseReportService;

    @PostMapping("/list")
    public List<TestCaseReport> list(@RequestBody TestCaseReport request) {
        return testCaseReportService.listTestCaseReport(request);
    }

    @GetMapping("/get/{id}")
    public TestCaseReport get(@PathVariable String id) {
        return testCaseReportService.getTestCaseReport(id);
    }

    @PostMapping("/add")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public String addByTemplateId(@RequestBody CreateReportRequest request) {
        return testCaseReportService.addTestCaseReportByTemplateId(request);
    }

    @PostMapping("/edit")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void edit(@RequestBody TestCaseReport TestCaseReport) {
        testCaseReportService.editTestCaseReport(TestCaseReport);
    }

    @PostMapping("/delete/{id}")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public int delete(@PathVariable String id) {
        return testCaseReportService.deleteTestCaseReport(id);
    }

}
