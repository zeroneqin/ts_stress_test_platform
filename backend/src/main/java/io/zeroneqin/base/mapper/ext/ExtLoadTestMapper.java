package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.base.domain.LoadTest;
import io.zeroneqin.dto.LoadTestDTO;
import io.zeroneqin.track.request.testplan.QueryTestPlanRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ExtLoadTestMapper {
    List<LoadTestDTO> list(@Param("request") QueryTestPlanRequest params);

    List<LoadTest> getLoadTestByProjectId(String projectId);

    int checkLoadTestOwner(@Param("testId") String testId, @Param("workspaceIds") Set<String> workspaceIds);
}
