package io.zeroneqin.track.controller;

import io.zeroneqin.base.domain.TestCaseNode;
import io.zeroneqin.commons.constants.RoleConstants;
import io.zeroneqin.service.CheckPermissionService;
import io.zeroneqin.track.dto.TestCaseNodeDTO;
import io.zeroneqin.track.request.testcase.DragNodeRequest;
import io.zeroneqin.track.request.testcase.QueryNodeRequest;
import io.zeroneqin.track.service.TestCaseNodeService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/case/node")
@RestController
@RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
public class TestCaseNodeController {

    @Resource
    TestCaseNodeService testCaseNodeService;
    @Resource
    private CheckPermissionService checkPermissionService;

    @GetMapping("/list/{projectId}")
    public List<TestCaseNodeDTO> getNodeByProjectId(@PathVariable String projectId) {
        checkPermissionService.checkProjectOwner(projectId);
        return testCaseNodeService.getNodeTreeByProjectId(projectId);
    }

    /*模块列表列表*/
    @PostMapping("/list/all/plan")
    public List<TestCaseNodeDTO> getAllNodeByPlanId(@RequestBody QueryNodeRequest request) {
        return testCaseNodeService.getAllNodeByPlanId(request);
    }

    @PostMapping("/list/all/review")
    public List<TestCaseNodeDTO> getAllNodeByReviewId(@RequestBody QueryNodeRequest request) {
        return testCaseNodeService.getAllNodeByReviewId(request);
    }

    @GetMapping("/list/plan/{planId}")
    public List<TestCaseNodeDTO> getNodeByPlanId(@PathVariable String planId) {
        checkPermissionService.checkTestPlanOwner(planId);
        return testCaseNodeService.getNodeByPlanId(planId);
    }

    @GetMapping("/list/review/{reviewId}")
    public List<TestCaseNodeDTO> getNodeByReviewId(@PathVariable String reviewId) {
        checkPermissionService.checkTestReviewOwner(reviewId);
        return testCaseNodeService.getNodeByReviewId(reviewId);
    }

    @PostMapping("/add")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public String addNode(@RequestBody TestCaseNode node) {
        return testCaseNodeService.addNode(node);
    }

    @PostMapping("/edit")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public int editNode(@RequestBody DragNodeRequest node) {
        return testCaseNodeService.editNode(node);
    }

    @PostMapping("/delete")

    public int deleteNode(@RequestBody List<String> nodeIds) {
        //nodeIds 包含删除节点ID及其所有子节点ID
        return testCaseNodeService.deleteNode(nodeIds);
    }

    @PostMapping("/drag")

    public void dragNode(@RequestBody DragNodeRequest node) {
        testCaseNodeService.dragNode(node);
    }

    @PostMapping("/pos")
    public void treeSort(@RequestBody List<String> ids) {
        testCaseNodeService.sort(ids);
    }
}
