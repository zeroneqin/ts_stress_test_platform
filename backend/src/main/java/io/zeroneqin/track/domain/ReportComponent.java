package io.zeroneqin.track.domain;

import io.zeroneqin.api.dto.automation.ApiScenarioDTO;
import io.zeroneqin.api.dto.definition.TestPlanApiCaseDTO;
import io.zeroneqin.track.dto.TestCaseReportMetricDTO;
import io.zeroneqin.track.dto.TestPlanCaseDTO;
import io.zeroneqin.track.dto.TestPlanDTO;

public abstract class ReportComponent {
    protected String componentId;
    protected TestPlanDTO testPlan;

    public ReportComponent(TestPlanDTO testPlan) {
        this.testPlan = testPlan;
    }

    public abstract void readRecord(TestPlanCaseDTO testCase);

    public abstract void afterBuild(TestCaseReportMetricDTO testCaseReportMetric);

    public void readRecord(TestPlanApiCaseDTO testCase) {
    }

    public void readRecord(ApiScenarioDTO testCase) {
    }

}
