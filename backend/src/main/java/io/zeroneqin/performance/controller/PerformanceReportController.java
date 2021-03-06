package io.zeroneqin.performance.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.zeroneqin.base.domain.LoadTestReportLog;
import io.zeroneqin.base.domain.LoadTestReportWithBLOBs;
import io.zeroneqin.commons.utils.PageUtils;
import io.zeroneqin.commons.utils.Pager;
import io.zeroneqin.commons.utils.SessionUtils;
import io.zeroneqin.dto.LogDetailDTO;
import io.zeroneqin.dto.ReportDTO;
import io.zeroneqin.performance.base.*;
import io.zeroneqin.performance.controller.request.DeleteReportRequest;
import io.zeroneqin.performance.controller.request.ReportRequest;
import io.zeroneqin.performance.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "performance/report")
public class PerformanceReportController {

    @Resource
    private ReportService reportService;

    @GetMapping("/recent/{count}")

    public List<ReportDTO> recentProjects(@PathVariable int count) {
        String currentOrganizationId = SessionUtils.getCurrentOrganizationId();
        ReportRequest request = new ReportRequest();
        request.setOrganizationId(currentOrganizationId);
        request.setUserId(SessionUtils.getUserId());
        // 最近 `count` 个项目
        PageHelper.startPage(1, count);
        return reportService.getRecentReportList(request);
    }

    @PostMapping("/list/all/{goPage}/{pageSize}")
    public Pager<List<ReportDTO>> getReportList(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody ReportRequest request) {
        String currentOrganizationId = SessionUtils.getCurrentOrganizationId();
        request.setOrganizationId(currentOrganizationId);
        Page<Object> page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, reportService.getReportList(request));
    }

    @PostMapping("/delete/{reportId}")
    public void deleteReport(@PathVariable String reportId) {
        reportService.deleteReport(reportId);
    }


    @GetMapping("/test/pro/info/{reportId}")
    public ReportDTO getReportTestAndProInfo(@PathVariable String reportId) {
        return reportService.getReportTestAndProInfo(reportId);
    }

    @GetMapping("/content/{reportId}")
    public List<Statistics> getReportContent(@PathVariable String reportId) {
        return reportService.getReportStatistics(reportId);
    }

    @GetMapping("/content/errors/{reportId}")
    public List<Errors> getReportErrors(@PathVariable String reportId) {
        return reportService.getReportErrors(reportId);
    }

    @GetMapping("/content/errors_top5/{reportId}")
    public List<ErrorsTop5> getReportErrorsTop5(@PathVariable String reportId) {
        return reportService.getReportErrorsTOP5(reportId);
    }

    @GetMapping("/content/testoverview/{reportId}")
    public TestOverview getTestOverview(@PathVariable String reportId) {
        return reportService.getTestOverview(reportId);
    }

    @GetMapping("/content/report_time/{reportId}")
    public ReportTimeInfo getReportTimeInfo(@PathVariable String reportId) {
        return reportService.getReportTimeInfo(reportId);
    }

    @GetMapping("/content/load_chart/{reportId}")
    public List<ChartsData> getLoadChartData(@PathVariable String reportId) {
        return reportService.getLoadChartData(reportId);
    }

    @GetMapping("/content/res_chart/{reportId}")
    public List<ChartsData> getResponseTimeChartData(@PathVariable String reportId) {
        return reportService.getResponseTimeChartData(reportId);
    }

    @GetMapping("/content/error_chart/{reportId}")
    public List<ChartsData> getErrorChartData(@PathVariable String reportId) {
        return reportService.getErrorChartData(reportId);
    }

    @GetMapping("/content/response_code_chart/{reportId}")
    public List<ChartsData> getResponseCodeChartData(@PathVariable String reportId) {
        return reportService.getResponseCodeChartData(reportId);
    }

    @GetMapping("/{reportId}")
    public LoadTestReportWithBLOBs getLoadTestReport(@PathVariable String reportId) {
        return reportService.getLoadTestReport(reportId);
    }

    @GetMapping("log/resource/{reportId}")
    public List<LogDetailDTO> getResourceIds(@PathVariable String reportId) {
        return reportService.getReportLogResource(reportId);
    }

    @GetMapping("log/{reportId}/{resourceId}/{goPage}")
    public Pager<List<LoadTestReportLog>> logs(@PathVariable String reportId, @PathVariable String resourceId, @PathVariable int goPage) {
        Page<Object> page = PageHelper.startPage(goPage, 1, true);
        return PageUtils.setPageInfo(page, reportService.getReportLogs(reportId, resourceId));
    }

    @GetMapping("log/download/{reportId}/{resourceId}")
    public void downloadLog(@PathVariable String reportId, @PathVariable String resourceId, HttpServletResponse response) throws Exception {
        reportService.downloadLog(response, reportId, resourceId);
    }

    @PostMapping("/batch/delete")

    public void deleteReportBatch(@RequestBody DeleteReportRequest reportRequest) {
        reportService.deleteReportBatch(reportRequest);
    }

    @GetMapping("/jtl/download/{reportId}")
    public ResponseEntity<byte[]> downloadJtl(@PathVariable String reportId) {
        byte[] bytes = reportService.downloadJtl(reportId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + reportId + ".jtl\"")
                .body(bytes);
    }
}
