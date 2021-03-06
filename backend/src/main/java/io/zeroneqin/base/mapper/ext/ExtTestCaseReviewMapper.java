package io.zeroneqin.base.mapper.ext;

import io.zeroneqin.track.dto.TestCaseReviewDTO;
import io.zeroneqin.track.dto.TestReviewDTOWithMetric;
import io.zeroneqin.track.request.testreview.QueryCaseReviewRequest;
import io.zeroneqin.track.request.testreview.QueryTestReviewRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ExtTestCaseReviewMapper {

    List<TestCaseReviewDTO> list(@Param("request") QueryCaseReviewRequest params);

    List<TestCaseReviewDTO> listByWorkspaceId(@Param("workspaceId") String workspaceId, @Param("userId") String userId, @Param("projectId") String projectId);

    List<TestReviewDTOWithMetric> listRelate(@Param("request") QueryTestReviewRequest request);

    /**
     * 检查某工作空间下是否有某测试评审
     *
     * @param reviewId
     * @param workspaceIds
     * @return Review ID
     */
    int checkIsHave(@Param("reviewId") String reviewId, @Param("workspaceIds") Set<String> workspaceIds);
}
