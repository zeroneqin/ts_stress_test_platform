package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.api.dto.automation.ApiScenarioDTO;
import io.zeroneqin.api.dto.automation.TestPlanScenarioRequest;
import io.zeroneqin.base.domain.TestPlanApiScenario;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtTestPlanScenarioCaseMapper {
    void insertIfNotExists(@Param("request") TestPlanApiScenario request);

    List<ApiScenarioDTO> list(@Param("request") TestPlanScenarioRequest request);

    List<String> getExecResultByPlanId(String planId);

    List<String> getIdsByPlanId(String planId);

    List<String> getNotRelevanceCaseIds(String planId, List<String> relevanceProjectIds);
}