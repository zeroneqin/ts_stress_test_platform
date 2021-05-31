package io.zeroneqin.track.dto;

import io.zeroneqin.base.domain.TestPlan;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestPlanDTO extends TestPlan {
    private String projectName;
    private String userName;
    private List<String> projectIds;
}
