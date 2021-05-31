package io.zeroneqin.api.dto.automation;

import io.zeroneqin.base.domain.ApiScenario;
import io.zeroneqin.track.dto.TestPlanDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReferenceDTO {
    List<ApiScenario> scenarioList;

    List<TestPlanDTO> testPlanList;

    public List<ApiScenario> getScenarioList() {
        return scenarioList;
    }

    public void setScenarioList(List<ApiScenario> scenarioList) {
        this.scenarioList = scenarioList;
    }

    public List<TestPlanDTO> getTestPlanList() {
        return testPlanList;
    }

    public void setTestPlanList(List<TestPlanDTO> testPlanList) {
        this.testPlanList = testPlanList;
    }
}
