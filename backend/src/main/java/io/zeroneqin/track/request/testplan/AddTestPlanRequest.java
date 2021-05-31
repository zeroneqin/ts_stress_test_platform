package io.zeroneqin.track.request.testplan;

import io.zeroneqin.base.domain.TestPlan;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddTestPlanRequest extends TestPlan {
    private List<String> projectIds;
}
