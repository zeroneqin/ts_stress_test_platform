package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.api.dto.QueryAPIReportRequest;
import io.zeroneqin.api.dto.automation.APIScenarioReportResult;
import io.zeroneqin.api.dto.datacount.ApiDataCountResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtApiScenarioReportMapper {
    List<APIScenarioReportResult> list(@Param("request") QueryAPIReportRequest request);

    APIScenarioReportResult get(@Param("reportId") String reportId);

    long countByProjectID(String projectId);

    long countByProjectIdAndCreateInThisWeek(@Param("projectId") String projectId, @Param("firstDayTimestamp") long firstDayTimestamp, @Param("lastDayTimestamp") long lastDayTimestamp);

    long countByProjectIdAndCreateAndByScheduleInThisWeek(@Param("projectId") String projectId, @Param("firstDayTimestamp") long firstDayTimestamp, @Param("lastDayTimestamp") long lastDayTimestamp);

    List<ApiDataCountResult> countByProjectIdGroupByExecuteResult(String projectId);
}