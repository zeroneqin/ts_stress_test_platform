package io.zeroneqin.api.jmeter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestResult {

    private String testId;

    private int success = 0;

    private int error = 0;

    private int total = 0;

    private int totalAssertions = 0;

    private int passAssertions = 0;

    private List<ScenarioResult> scenarios = new ArrayList<>();

    public void addError(int count) {
        this.error += count;
    }

    public void addSuccess() {
        this.success++;
    }

    public void addTotalAssertions(int count) {
        this.totalAssertions += count;
    }

    public void addPassAssertions(int count) {
        this.passAssertions += count;
    }


    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalAssertions() {
        return totalAssertions;
    }

    public void setTotalAssertions(int totalAssertions) {
        this.totalAssertions = totalAssertions;
    }

    public int getPassAssertions() {
        return passAssertions;
    }

    public void setPassAssertions(int passAssertions) {
        this.passAssertions = passAssertions;
    }

    public List<ScenarioResult> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<ScenarioResult> scenarios) {
        this.scenarios = scenarios;
    }
}
