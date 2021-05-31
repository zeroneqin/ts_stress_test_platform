package io.zeroneqin.track.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.zeroneqin.base.domain.TestCaseReviewTestCase;
import io.zeroneqin.commons.constants.RoleConstants;
import io.zeroneqin.commons.utils.PageUtils;
import io.zeroneqin.commons.utils.Pager;
import io.zeroneqin.track.dto.TestReviewCaseDTO;
import io.zeroneqin.track.request.testplancase.TestReviewCaseBatchRequest;
import io.zeroneqin.track.request.testreview.DeleteRelevanceRequest;
import io.zeroneqin.track.request.testreview.QueryCaseReviewRequest;
import io.zeroneqin.track.service.TestReviewTestCaseService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/test/review/case")
@RestController
public class TestReviewTestCaseController {

    @Resource
    TestReviewTestCaseService testReviewTestCaseService;

    @PostMapping("/list/{goPage}/{pageSize}")
    public Pager<List<TestReviewCaseDTO>> getTestPlanCases(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody QueryCaseReviewRequest request) {
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, testReviewTestCaseService.list(request));
    }

    @PostMapping("/delete")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public int deleteTestCase(@RequestBody DeleteRelevanceRequest request) {
        return testReviewTestCaseService.deleteTestCase(request);
    }

    @PostMapping("/batch/delete")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void deleteTestCaseBath(@RequestBody TestReviewCaseBatchRequest request) {
        testReviewTestCaseService.deleteTestCaseBath(request);
    }

    @PostMapping("/list/all")
    public List<TestReviewCaseDTO> getTestReviewCases(@RequestBody QueryCaseReviewRequest request) {
        return testReviewTestCaseService.list(request);
    }

    @PostMapping("/edit")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void editTestCase(@RequestBody TestCaseReviewTestCase testCaseReviewTestCase) {
        testReviewTestCaseService.editTestCase(testCaseReviewTestCase);
    }

    @GetMapping("/get/{reviewId}")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public TestReviewCaseDTO get(@PathVariable String reviewId) {
        return testReviewTestCaseService.get(reviewId);
    }

    @PostMapping("/list/ids")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public List<TestReviewCaseDTO> getTestReviewCaseList(@RequestBody QueryCaseReviewRequest request) {
        return testReviewTestCaseService.getTestCaseReviewDTOList(request);
    }

}
