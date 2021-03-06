package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.track.dto.TestPlanDTO;
import io.zeroneqin.track.dto.TestPlanDTOWithMetric;
import io.zeroneqin.track.request.testcase.QueryTestPlanRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ExtTestPlanMapper {
    List<TestPlanDTOWithMetric> list(@Param("request") QueryTestPlanRequest params);

    List<TestPlanDTOWithMetric> listRelate(@Param("request") QueryTestPlanRequest params);

    List<TestPlanDTO> planList(@Param("request") QueryTestPlanRequest params);

    List<TestPlanDTO> selectByIds(@Param("list") List<String> ids);

    /**
     * 通过关联表(test_plan_api_case/test_plan_api_scenario)查询testPlan
     *
     * @param params
     * @return
     */
    List<TestPlanDTO> selectTestPlanByRelevancy(@Param("request") QueryTestPlanRequest params);

    int checkIsHave(@Param("planId") String planId, @Param("workspaceIds") Set<String> workspaceIds);
}
