package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.dto.DashboardTestDTO;
import io.zeroneqin.dto.ReportDTO;
import io.zeroneqin.performance.controller.request.ReportRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtLoadTestReportMapper {

    List<ReportDTO> getReportList(@Param("reportRequest") ReportRequest request);

    ReportDTO getReportTestAndProInfo(@Param("id") String id);

    List<DashboardTestDTO> selectDashboardTests(@Param("organizationId") String organizationId, @Param("startTimestamp") long startTimestamp);

    List<String> selectResourceId(@Param("reportId") String reportId);
}
