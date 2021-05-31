package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.api.dto.datacount.ApiDataCountResult;
import io.zeroneqin.api.dto.definition.ApiTestCaseDTO;
import io.zeroneqin.api.dto.definition.ApiTestCaseRequest;
import io.zeroneqin.api.dto.definition.ApiTestCaseResult;
import io.zeroneqin.base.domain.ApiTestCase;
import io.zeroneqin.base.domain.ApiTestCaseWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtApiTestCaseMapper {

    List<ApiTestCaseResult> list(@Param("request") ApiTestCaseRequest request);

    List<ApiTestCaseDTO> listSimple(@Param("request") ApiTestCaseRequest request);

    List<String> selectIdsNotExistsInPlan(@Param("projectId") String projectId, @Param("planId") String planId);

    List<ApiDataCountResult> countProtocolByProjectID(String projectId);

    long countByProjectIDAndCreateInThisWeek(@Param("projectId") String projectId, @Param("firstDayTimestamp") long firstDayTimestamp, @Param("lastDayTimestamp") long lastDayTimestamp);

    List<ApiTestCaseWithBLOBs> getRequest(@Param("request") ApiTestCaseRequest request);

    ApiTestCase getNextNum(@Param("definitionId") String definitionId);
}