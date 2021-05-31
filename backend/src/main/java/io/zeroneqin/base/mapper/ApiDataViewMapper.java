package io.zeroneqin.base.mapper;

import io.zeroneqin.api.dto.ApiMonitorSearch;
import io.zeroneqin.api.dto.ApiResponseCodeMonitor;
import io.zeroneqin.api.dto.ApiResponseTimeMonitor;
import io.zeroneqin.base.domain.ApiDataView;

import java.util.List;

public interface ApiDataViewMapper {

    List<ApiMonitorSearch> selectAll();

    List<ApiResponseTimeMonitor> selectResponseTimeByUrl(String url,String startTime,String endTime);

    List<ApiResponseCodeMonitor> selectResponseCodeByUrl(String url,String startTime,String endTime);

    Integer insertListApiData(List<ApiDataView> list);

    Integer deleteByReportId(String reportId);

    String selectReportIdByUrlAndStartTime(String apiUrl,String startTime);
}