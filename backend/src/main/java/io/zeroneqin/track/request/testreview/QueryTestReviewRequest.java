package io.zeroneqin.track.request.testreview;

import io.zeroneqin.base.domain.TestCaseReview;
import io.zeroneqin.controller.request.OrderRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class QueryTestReviewRequest extends TestCaseReview {
    private boolean recent = false;

    private List<String> reviewIds;

    private List<OrderRequest> orders;

    private Map<String, List<String>> filters;

    private Map<String, Object> combine;

    private String projectId;

    private String workspaceId;

    private String reviewerId;
}
