package io.zeroneqin.track.request.testcase;

import io.zeroneqin.base.domain.TestPlan;
import io.zeroneqin.controller.request.OrderRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class QueryTestPlanRequest extends TestPlan {

    private boolean recent = false;

    private List<String> planIds;

    private String scenarioId;

    private String apiId;

    private List<OrderRequest> orders;

    private Map<String, List<String>> filters;

    private Map<String, Object> combine;

    private String projectId;

    private String projectName;
}
