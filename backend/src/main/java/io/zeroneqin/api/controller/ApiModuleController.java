package io.zeroneqin.api.controller;

import io.zeroneqin.api.dto.definition.ApiModuleDTO;
import io.zeroneqin.api.dto.definition.DragModuleRequest;
import io.zeroneqin.api.service.ApiModuleService;
import io.zeroneqin.base.domain.ApiModule;
import io.zeroneqin.commons.constants.RoleConstants;
import io.zeroneqin.service.CheckPermissionService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/api/module")
@RestController
@RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
public class ApiModuleController {

    @Resource
    ApiModuleService apiModuleService;
    @Resource
    private CheckPermissionService checkPermissionService;

    @GetMapping("/list/{projectId}/{protocol}")
    public List<ApiModuleDTO> getNodeByProjectId(@PathVariable String projectId,@PathVariable String protocol) {
        checkPermissionService.checkProjectOwner(projectId);
        return apiModuleService.getNodeTreeByProjectId(projectId,protocol);
    }

    @GetMapping("/list/plan/{planId}/{protocol}")
    public List<ApiModuleDTO> getNodeByPlanId(@PathVariable String planId, @PathVariable String protocol) {
        checkPermissionService.checkTestPlanOwner(planId);
        return apiModuleService.getNodeByPlanId(planId, protocol);
    }

    @PostMapping("/add")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public String addNode(@RequestBody ApiModule node) {
        return apiModuleService.addNode(node);
    }

    @PostMapping("/edit")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public int editNode(@RequestBody DragModuleRequest node) {
        return apiModuleService.editNode(node);
    }

    @PostMapping("/delete")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public int deleteNode(@RequestBody List<String> nodeIds) {
        //nodeIds 包含删除节点ID及其所有子节点ID
        return apiModuleService.deleteNode(nodeIds);
    }

    @PostMapping("/drag")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void dragNode(@RequestBody DragModuleRequest node) {
        apiModuleService.dragNode(node);
    }

    @PostMapping("/pos")
    public void treeSort(@RequestBody List<String> ids) {
        apiModuleService.sort(ids);
    }

}
