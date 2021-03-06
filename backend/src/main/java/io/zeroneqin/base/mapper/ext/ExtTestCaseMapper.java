package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.base.domain.TestCase;
import io.zeroneqin.track.dto.TestCaseDTO;
import io.zeroneqin.track.request.testcase.QueryTestCaseRequest;
import io.zeroneqin.track.request.testcase.TestCaseBatchRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ExtTestCaseMapper {

    List<TestCase> getTestCaseNames(@Param("request") QueryTestCaseRequest request);

    List<TestCaseDTO> list(@Param("request") QueryTestCaseRequest request);

    List<TestCaseDTO> listByMethod(@Param("request") QueryTestCaseRequest request);

    List<TestCaseDTO> listByTestCaseIds(@Param("request") TestCaseBatchRequest request);

    TestCase getMaxNumByProjectId(@Param("projectId") String projectId);

    /**
     * 获取不在测试计划中的用例
     *
     * @param request
     * @return
     */
    List<TestCase> getTestCaseByNotInPlan(@Param("request") QueryTestCaseRequest request);

    /**
     * 获取不在评审范围中的用例
     *
     * @param request
     * @return
     */
    List<TestCase> getTestCaseByNotInReview(@Param("request") QueryTestCaseRequest request);

    /**
     * 检查某工作空间下是否有某用例
     *
     * @param caseId
     * @param workspaceIds
     * @return TestCase ID
     */
    int checkIsHave(@Param("caseId") String caseId, @Param("workspaceIds") Set<String> workspaceIds);

}
