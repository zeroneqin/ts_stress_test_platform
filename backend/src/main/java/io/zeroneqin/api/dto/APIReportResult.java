package io.zeroneqin.api.dto;

import io.zeroneqin.base.domain.ApiTestReport;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class APIReportResult extends ApiTestReport {

    private String testName;

    private String projectName;

    private String userName;

    private List<String> scenarioIds;

    private String content;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getScenarioIds() {
        return scenarioIds;
    }

    public void setScenarioIds(List<String> scenarioIds) {
        this.scenarioIds = scenarioIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
