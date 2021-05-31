package io.zeroneqin.track.dto;

import io.zeroneqin.api.dto.automation.ApiScenarioDTO;
import io.zeroneqin.api.dto.definition.TestPlanApiCaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FailureTestCasesAdvanceDTO {
    private List<TestPlanCaseDTO> functionalTestCases;
    private List<TestPlanApiCaseDTO> apiTestCases;
    private List<ApiScenarioDTO> scenarioTestCases;
}
