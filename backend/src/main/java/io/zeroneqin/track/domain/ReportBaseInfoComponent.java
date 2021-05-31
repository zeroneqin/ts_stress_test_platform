package io.zeroneqin.track.domain;

import io.zeroneqin.track.dto.TestCaseReportMetricDTO;
import io.zeroneqin.track.dto.TestPlanCaseDTO;
import io.zeroneqin.track.dto.TestPlanDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ReportBaseInfoComponent extends ReportComponent {
    private Set<String> executorsSet = new HashSet<>();

    public ReportBaseInfoComponent(TestPlanDTO testPlan) {
        super(testPlan);
        componentId = "1";
    }

    @Override
    public void readRecord(TestPlanCaseDTO testCase) {
        executorsSet.add(testCase.getExecutor());
    }

    @Override
    public void afterBuild(TestCaseReportMetricDTO testCaseReportMetric) {
        testCaseReportMetric.setProjectName(testPlan.getProjectName());
        testCaseReportMetric.setPrincipal(testPlan.getPrincipal());
        testCaseReportMetric.setExecutors(new ArrayList<>(this.executorsSet));
    }
}
