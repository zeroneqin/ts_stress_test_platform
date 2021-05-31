package io.zeroneqin.api.dto.automation;

import io.zeroneqin.api.dto.definition.request.MsTestElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SaveApiScenarioRequest {
    private String id;

    private String projectId;

    private String tags;

    private String userId;

    private String apiScenarioModuleId;

    private String modulePath;

    private String name;

    private String level;

    private String status;

    private String principal;

    private Integer stepTotal;

    private String followPeople;

    private String schedule;

    private String description;

    private MsTestElement scenarioDefinition;

    List<String> bodyUploadIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApiScenarioModuleId() {
        return apiScenarioModuleId;
    }

    public void setApiScenarioModuleId(String apiScenarioModuleId) {
        this.apiScenarioModuleId = apiScenarioModuleId;
    }

    public String getModulePath() {
        return modulePath;
    }

    public void setModulePath(String modulePath) {
        this.modulePath = modulePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Integer getStepTotal() {
        return stepTotal;
    }

    public void setStepTotal(Integer stepTotal) {
        this.stepTotal = stepTotal;
    }

    public String getFollowPeople() {
        return followPeople;
    }

    public void setFollowPeople(String followPeople) {
        this.followPeople = followPeople;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MsTestElement getScenarioDefinition() {
        return scenarioDefinition;
    }

    public void setScenarioDefinition(MsTestElement scenarioDefinition) {
        this.scenarioDefinition = scenarioDefinition;
    }

    public List<String> getBodyUploadIds() {
        return bodyUploadIds;
    }

    public void setBodyUploadIds(List<String> bodyUploadIds) {
        this.bodyUploadIds = bodyUploadIds;
    }
}
