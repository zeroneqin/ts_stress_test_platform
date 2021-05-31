package io.zeroneqin.api.dto.parse;

import io.zeroneqin.api.dto.scenario.Scenario;
import lombok.Data;

import java.util.List;

@Data
public class ApiImport {
    private List<Scenario> scenarios;

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }
}
