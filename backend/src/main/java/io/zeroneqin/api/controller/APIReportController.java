package io.zeroneqin.api.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.zeroneqin.api.dto.APIReportResult;
import io.zeroneqin.api.dto.DeleteAPIReportRequest;
import io.zeroneqin.api.dto.QueryAPIReportRequest;
import io.zeroneqin.api.service.APIReportService;
import io.zeroneqin.commons.constants.RoleConstants;
import io.zeroneqin.commons.utils.PageUtils;
import io.zeroneqin.commons.utils.Pager;
import io.zeroneqin.commons.utils.SessionUtils;
import io.zeroneqin.dto.DashboardTestDTO;
import io.zeroneqin.service.CheckPermissionService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/report")
@RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
public class APIReportController {

    @Resource
    private APIReportService apiReportService;
    @Resource
    private CheckPermissionService checkPermissionService;

    @GetMapping("recent/{count}")
    public List<APIReportResult> recentTest(@PathVariable int count) {
        String currentOrganizationId = SessionUtils.getCurrentOrganizationId();
        QueryAPIReportRequest request = new QueryAPIReportRequest();
        request.setOrganizationId(currentOrganizationId);
        request.setUserId(SessionUtils.getUserId());
        PageHelper.startPage(1, count, true);
        return apiReportService.recentTest(request);
    }

    @GetMapping("/list/{testId}/{goPage}/{pageSize}")
    public Pager<List<APIReportResult>> listByTestId(@PathVariable String testId, @PathVariable int goPage, @PathVariable int pageSize) {
        checkPermissionService.checkApiTestOwner(testId);
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, apiReportService.listByTestId(testId));

    }

    @PostMapping("/list/{goPage}/{pageSize}")
    public Pager<List<APIReportResult>> list(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody QueryAPIReportRequest request) {
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        request.setOrganizationId(SessionUtils.getCurrentOrganizationId());
        return PageUtils.setPageInfo(page, apiReportService.list(request));
    }

    @GetMapping("/get/{reportId}")
    public APIReportResult get(@PathVariable String reportId) {
        return apiReportService.get(reportId);
    }

    @PostMapping("/delete")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void delete(@RequestBody DeleteAPIReportRequest request) {
        apiReportService.delete(request);
    }

    @GetMapping("dashboard/tests")
    public List<DashboardTestDTO> dashboardTests() {
        return apiReportService.dashboardTests(SessionUtils.getCurrentOrganizationId());
    }

    @PostMapping("/batch/delete")
    @RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
    public void deleteAPIReportBatch(@RequestBody DeleteAPIReportRequest reportRequest) {
        apiReportService.deleteAPIReportBatch(reportRequest);
    }


}
