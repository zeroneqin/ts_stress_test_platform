package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.api.dto.datacount.response.TaskInfoResult;
import io.zeroneqin.controller.request.QueryScheduleRequest;
import io.zeroneqin.dto.ScheduleDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtScheduleMapper {
    List<ScheduleDao> list(@Param("request") QueryScheduleRequest request);

    long countTaskByProjectId(String workspaceId);

    long countTaskByProjectIdAndCreateTimeRange(@Param("projectId")String projectId, @Param("startTime") long startTime, @Param("endTime") long endTime);

    List<TaskInfoResult> findRunningTaskInfoByProjectID(String workspaceID);
}