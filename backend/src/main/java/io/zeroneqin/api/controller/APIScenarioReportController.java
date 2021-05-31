package io.zeroneqin.api.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.zeroneqin.api.dto.DeleteAPIReportRequest;
import io.zeroneqin.api.dto.QueryAPIReportRequest;
import io.zeroneqin.api.dto.automation.APIScenarioReportResult;
import io.zeroneqin.api.dto.automation.ExecuteType;
import io.zeroneqin.api.service.ApiScenarioReportService;
import io.zeroneqin.commons.constants.RoleConstants;
import io.zeroneqin.commons.utils.PageUtils;
import io.zeroneqin.commons.utils.Pager;
import io.zeroneqin.commons.utils.SessionUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/scenario/report")
@RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
public class APIScenarioReportController {

    @Resource
    private ApiScenarioReportService apiReportService;

    @GetMapping("/get/{reportId}")
    public APIScenarioReportResult get(@PathVariable String reportId) {
        return apiReportService.get(reportId);
    }

    @PostMapping("/list/{goPage}/{pageSize}")
    public Pager<List<APIScenarioReportResult>> list(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody QueryAPIReportRequest request) {
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        request.setOrganizationId(SessionUtils.getCurrentOrganizationId());
        return PageUtils.setPageInfo(page, apiReportService.list(request));
    }

    @PostMapping("/update")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public String update(@RequestBody APIScenarioReportResult node) {
        node.setExecuteType(ExecuteType.Saved.name());
        return apiReportService.update(node);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody DeleteAPIReportRequest request) {
        apiReportService.delete(request);
    }

    @PostMapping("/batch/delete")
    public void deleteAPIReportBatch(@RequestBody DeleteAPIReportRequest reportRequest) {
        apiReportService.deleteAPIReportBatch(reportRequest);
    }

}
