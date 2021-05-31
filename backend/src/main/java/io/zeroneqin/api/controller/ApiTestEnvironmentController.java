package io.zeroneqin.api.controller;

import io.zeroneqin.api.service.ApiTestEnvironmentService;
import io.zeroneqin.base.domain.ApiTestEnvironmentWithBLOBs;
import io.zeroneqin.commons.constants.RoleConstants;
import io.zeroneqin.service.CheckPermissionService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/environment")
@RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
public class ApiTestEnvironmentController {

    @Resource
    ApiTestEnvironmentService apiTestEnvironmentService;
    @Resource
    private CheckPermissionService checkPermissionService;

    @GetMapping("/list/{projectId}")
    public List<ApiTestEnvironmentWithBLOBs> list(@PathVariable String projectId) {
        checkPermissionService.checkProjectOwner(projectId);
        return apiTestEnvironmentService.list(projectId);
    }

    @GetMapping("/get/{id}")
    public ApiTestEnvironmentWithBLOBs get(@PathVariable String id) {
        return apiTestEnvironmentService.get(id);
    }

    @PostMapping("/add")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public String add(@RequestBody ApiTestEnvironmentWithBLOBs apiTestEnvironmentWithBLOBs) {
        return apiTestEnvironmentService.add(apiTestEnvironmentWithBLOBs);
    }

    @PostMapping(value = "/update")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void update(@RequestBody ApiTestEnvironmentWithBLOBs apiTestEnvironment) {
        apiTestEnvironmentService.update(apiTestEnvironment);
    }

    @GetMapping("/delete/{id}")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void delete(@PathVariable String id) {
        apiTestEnvironmentService.delete(id);
    }

}
