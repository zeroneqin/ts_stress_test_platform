package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.api.dto.definition.ApiTestCaseRequest;
import io.zeroneqin.api.dto.definition.TestPlanApiCaseDTO;
import io.zeroneqin.base.domain.TestPlanApiCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtTestPlanApiCaseMapper {
    void insertIfNotExists(@Param("request") TestPlanApiCase request);

    List<TestPlanApiCaseDTO> list(@Param("request") ApiTestCaseRequest request);

    List<String> getExecResultByPlanId(String planId);

    List<String> getIdsByPlanId(String planId);

    List<String> getNotRelevanceCaseIds(@Param("planId")String planId, @Param("relevanceProjectIds")List<String> relevanceProjectIds);
}