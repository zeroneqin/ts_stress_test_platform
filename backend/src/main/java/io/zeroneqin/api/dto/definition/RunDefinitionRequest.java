package io.zeroneqin.api.dto.definition;

import io.zeroneqin.api.dto.definition.request.MsTestElement;
import io.zeroneqin.api.dto.definition.response.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RunDefinitionRequest {

    private String id;

    private String reportId;

    private String name;

    private String type;

    private String projectId;

    private String scenarioId;

    private String scenarioName;

    private String environmentId;

    private MsTestElement testElement;

    private String executeType;

    private Response response;

    private List<String> bodyUploadIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public MsTestElement getTestElement() {
        return testElement;
    }

    public void setTestElement(MsTestElement testElement) {
        this.testElement = testElement;
    }

    public String getExecuteType() {
        return executeType;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<String> getBodyUploadIds() {
        return bodyUploadIds;
    }

    public void setBodyUploadIds(List<String> bodyUploadIds) {
        this.bodyUploadIds = bodyUploadIds;
    }
}
