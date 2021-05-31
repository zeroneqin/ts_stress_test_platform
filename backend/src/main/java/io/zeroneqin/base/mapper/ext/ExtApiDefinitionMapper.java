package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.api.dto.datacount.ApiDataCountResult;
import io.zeroneqin.api.dto.definition.ApiComputeResult;
import io.zeroneqin.api.dto.definition.ApiDefinitionRequest;
import io.zeroneqin.api.dto.definition.ApiDefinitionResult;
import io.zeroneqin.base.domain.ApiDefinition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtApiDefinitionMapper {

    List<ApiDefinitionResult> list(@Param("request") ApiDefinitionRequest request);

    List<ApiComputeResult> selectByIds(@Param("ids") List<String> ids);

    int removeToGc(@Param("ids") List<String> ids);

    int reduction(@Param("ids") List<String> ids);

    List<ApiDataCountResult> countProtocolByProjectID(String projectId);

    Long countByProjectIDAndCreateInThisWeek(@Param("projectId") String projectId, @Param("firstDayTimestamp") long firstDayTimestamp, @Param("lastDayTimestamp") long lastDayTimestamp);

    List<ApiDataCountResult> countStateByProjectID(String projectId);

    List<ApiDataCountResult> countApiCoverageByProjectID(String projectId);

    ApiDefinition getNextNum(@Param("projectId") String projectId);

    List<ApiDefinitionResult> listRelevance(@Param("request")ApiDefinitionRequest request);
}