package io.zeroneqin.api.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.zeroneqin.api.dto.APIReportResult;
import io.zeroneqin.api.dto.ApiTestImportRequest;
import io.zeroneqin.api.dto.automation.ApiScenarioRequest;
import io.zeroneqin.api.dto.automation.ReferenceDTO;
import io.zeroneqin.api.dto.definition.*;
import io.zeroneqin.api.dto.definition.parse.ApiDefinitionImport;
import io.zeroneqin.api.service.ApiDefinitionService;
import io.zeroneqin.base.domain.ApiDefinition;
import io.zeroneqin.commons.constants.RoleConstants;
import io.zeroneqin.commons.utils.PageUtils;
import io.zeroneqin.commons.utils.Pager;
import io.zeroneqin.commons.utils.SessionUtils;
import io.zeroneqin.service.CheckPermissionService;
import io.zeroneqin.track.request.testcase.ApiCaseRelevanceRequest;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping(value = "/api/definition")
@RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
public class ApiDefinitionController {
    @Resource
    private ApiDefinitionService apiDefinitionService;
    @Resource
    private CheckPermissionService checkPermissionService;

    @PostMapping("/list/{goPage}/{pageSize}")
    public Pager<List<ApiDefinitionResult>> list(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody ApiDefinitionRequest request) {
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        request.setWorkspaceId(SessionUtils.getCurrentWorkspaceId());
        return PageUtils.setPageInfo(page, apiDefinitionService.list(request));
    }

    @PostMapping("/list/relevance/{goPage}/{pageSize}")
    public Pager<List<ApiDefinitionResult>> listRelevance(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody ApiDefinitionRequest request) {
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        request.setWorkspaceId(SessionUtils.getCurrentWorkspaceId());
        return PageUtils.setPageInfo(page, apiDefinitionService.listRelevance(request));
    }

    @PostMapping("/list/all")
    public List<ApiDefinitionResult> list(@RequestBody ApiDefinitionRequest request) {
        return apiDefinitionService.list(request);
    }

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void create(@RequestPart("request") SaveApiDefinitionRequest request, @RequestPart(value = "files") List<MultipartFile> bodyFiles) {
        checkPermissionService.checkProjectOwner(request.getProjectId());
        apiDefinitionService.create(request, bodyFiles);
    }

    @PostMapping(value = "/update", consumes = {"multipart/form-data"})
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void update(@RequestPart("request") SaveApiDefinitionRequest request, @RequestPart(value = "files") List<MultipartFile> bodyFiles) {
        checkPermissionService.checkProjectOwner(request.getProjectId());
        apiDefinitionService.update(request, bodyFiles);
    }

    @GetMapping("/delete/{id}")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void delete(@PathVariable String id) {
        apiDefinitionService.delete(id);
    }

    @PostMapping("/deleteBatch")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void deleteBatch(@RequestBody List<String> ids) {
        apiDefinitionService.deleteBatch(ids);
    }

    @PostMapping("/deleteBatchByParams")
    public void deleteBatchByParams(@RequestBody ApiDefinitionBatchProcessingRequest request) {
        apiDefinitionService.deleteByParams(request);
    }

    @PostMapping("/removeToGc")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void removeToGc(@RequestBody List<String> ids) {
        apiDefinitionService.removeToGc(ids);
    }

    @PostMapping("/removeToGcByParams")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void removeToGcByParams(@RequestBody ApiDefinitionBatchProcessingRequest request) {
        apiDefinitionService.removeToGcByParams(request);
    }

    @PostMapping("/reduction")
    public void reduction(@RequestBody List<SaveApiDefinitionRequest> requests) {
        apiDefinitionService.reduction(requests);
    }

    @GetMapping("/get/{id}")
    public ApiDefinition get(@PathVariable String id) {
        return apiDefinitionService.get(id);
    }

    @PostMapping(value = "/run/debug", consumes = {"multipart/form-data"})
    public String runDebug(@RequestPart("request") RunDefinitionRequest request, @RequestPart(value = "files") List<MultipartFile> bodyFiles) {
        return apiDefinitionService.run(request, bodyFiles);
    }

    @PostMapping(value = "/run", consumes = {"multipart/form-data"})
    public String run(@RequestPart("request") RunDefinitionRequest request, @RequestPart(value = "files") List<MultipartFile> bodyFiles) {
        return apiDefinitionService.run(request, bodyFiles);
    }

    @GetMapping("/report/get/{testId}/{test}")
    public APIReportResult get(@PathVariable String testId, @PathVariable String test) {
        return apiDefinitionService.getResult(testId, test);
    }

    @GetMapping("/report/getReport/{testId}")
    public APIReportResult getReport(@PathVariable String testId) {
        return apiDefinitionService.getDbResult(testId);
    }

    @GetMapping("/report/getReport/{testId}/{type}")
    public APIReportResult getReport(@PathVariable String testId, @PathVariable String type) {
        return apiDefinitionService.getDbResult(testId, type);
    }

    @PostMapping(value = "/import", consumes = {"multipart/form-data"})
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public ApiDefinitionImport testCaseImport(@RequestPart(value = "file", required = false) MultipartFile file, @RequestPart("request") ApiTestImportRequest request) {
        return apiDefinitionService.apiTestImport(file, request);
    }

    @PostMapping("/getReference")
    public ReferenceDTO getReference(@RequestBody ApiScenarioRequest request) {
        return apiDefinitionService.getReference(request);
    }

    @PostMapping("/batch/edit")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void editApiBath(@RequestBody ApiBatchRequest request) {
        apiDefinitionService.editApiBath(request);
    }

    @PostMapping("/batch/editByParams")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void editByParams(@RequestBody ApiBatchRequest request) {
        apiDefinitionService.editApiByParam(request);
    }

    @PostMapping("/relevance")
    public void testPlanRelevance(@RequestBody ApiCaseRelevanceRequest request) {
        apiDefinitionService.testPlanRelevance(request);
    }
}
