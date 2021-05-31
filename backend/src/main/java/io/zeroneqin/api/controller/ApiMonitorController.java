package io.zeroneqin.api.controller;

import io.zeroneqin.api.dto.ApiMonitorRequest;
import io.zeroneqin.api.dto.ApiMonitorSearch;
import io.zeroneqin.api.dto.ApiResponseCodeMonitor;
import io.zeroneqin.api.dto.ApiResponseTimeMonitor;
import io.zeroneqin.api.service.APIMonitorService;
import io.zeroneqin.commons.constants.RoleConstants;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/monitor")
@RequiresRoles(value = {RoleConstants.ADMIN,RoleConstants.ORG_ADMIN,RoleConstants.ORG_MEMBER}, logical = Logical.OR)
public class ApiMonitorController {

    @Resource
    private APIMonitorService apiMonitorService;

    /**
     * 查询所有接口
     */
    @GetMapping("/list")
    public List<ApiMonitorSearch> apiList() {
        return apiMonitorService.list();
    }


    /**
     * 查询响应时间
     */
    @PostMapping("/getResponseTime")
    public List<ApiResponseTimeMonitor> responseTimeData(@RequestBody ApiMonitorRequest request) {
        return apiMonitorService.getApiResponseTimeData(request.getUrl(), request.getStartTime(), request.getEndTime());
    }

    /**
     * 查询状态码
     */
    @PostMapping("/getResponseCode")
    public List<ApiResponseCodeMonitor> responseCodeData(@RequestBody ApiMonitorRequest request) {
        return apiMonitorService.getApiResponseCodeData(request.getUrl(), request.getStartTime(), request.getEndTime());
    }

    /**
     * 查询reportId
     */
    @PostMapping("/getReportId")
    public String searchReportId(@RequestBody ApiMonitorRequest request) {
        return apiMonitorService.getReportId(request.getUrl(), request.getStartTime());
    }
}
