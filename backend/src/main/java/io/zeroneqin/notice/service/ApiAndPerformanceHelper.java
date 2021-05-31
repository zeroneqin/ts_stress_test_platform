package io.zeroneqin.notice.service;

import io.zeroneqin.api.dto.APIReportResult;
import io.zeroneqin.base.domain.ApiTestReportDetail;
import io.zeroneqin.base.domain.LoadTestReportWithBLOBs;
import io.zeroneqin.base.domain.Schedule;
import io.zeroneqin.base.mapper.ApiTestReportDetailMapper;
import io.zeroneqin.base.mapper.LoadTestReportMapper;
import io.zeroneqin.base.mapper.ext.ExtApiTestReportMapper;
import io.zeroneqin.base.mapper.ext.ExtLoadTestMapper;
import io.zeroneqin.commons.constants.ScheduleGroup;
import io.zeroneqin.dto.LoadTestDTO;
import io.zeroneqin.service.ScheduleService;
import io.zeroneqin.track.request.testplan.QueryTestPlanRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
public class ApiAndPerformanceHelper {
    @Resource
    private ExtLoadTestMapper extLoadTestMapper;
    @Resource
    private ExtApiTestReportMapper extApiTestReportMapper;
    @Resource
    private ApiTestReportDetailMapper apiTestReportDetailMapper;
    @Resource
    private ScheduleService scheduleService;
    @Resource
    private LoadTestReportMapper loadTestReportMapper;

    public APIReportResult getApi(String reportId) {
        APIReportResult result = extApiTestReportMapper.get(reportId);
        ApiTestReportDetail detail = apiTestReportDetailMapper.selectByPrimaryKey(reportId);
        if (detail != null) {
            result.setContent(new String(detail.getContent(), StandardCharsets.UTF_8));
        }
        return result;
    }

    public LoadTestDTO getPerformance(String testId) {
        QueryTestPlanRequest request = new QueryTestPlanRequest();
        request.setId(testId);
        List<LoadTestDTO> testDTOS = extLoadTestMapper.list(request);
        if (!CollectionUtils.isEmpty(testDTOS)) {
            LoadTestDTO loadTestDTO = testDTOS.get(0);
            Schedule schedule = scheduleService.getScheduleByResource(loadTestDTO.getId(), ScheduleGroup.PERFORMANCE_TEST.name());
            loadTestDTO.setSchedule(schedule);
            return loadTestDTO;
        }
        return null;
    }

    public LoadTestReportWithBLOBs getLoadTestReport(String id) {
        return loadTestReportMapper.selectByPrimaryKey(id);
    }
}

