package io.zeroneqin.api.dto;

import io.zeroneqin.api.dto.scenario.Scenario;
import io.zeroneqin.base.domain.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SaveAPITestRequest {

    private String id;

    private String projectId;

    private String name;

    private List<Scenario> scenarioDefinition;

    private String userId;

    private String gitlab;

    private String gitbranch;

    private Schedule schedule;

    private String triggerMode;

    private List<String> bodyUploadIds;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Scenario> getScenarioDefinition() {
        return scenarioDefinition;
    }

    public void setScenarioDefinition(List<Scenario> scenarioDefinition) {
        this.scenarioDefinition = scenarioDefinition;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGitlab() {
        return gitlab;
    }

    public void setGitlab(String gitlab) {
        this.gitlab = gitlab;
    }

    public String getGitbranch() {
        return gitbranch;
    }

    public void setGitbranch(String gitbranch) {
        this.gitbranch = gitbranch;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getTriggerMode() {
        return triggerMode;
    }

    public void setTriggerMode(String triggerMode) {
        this.triggerMode = triggerMode;
    }

    public List<String> getBodyUploadIds() {
        return bodyUploadIds;
    }

    public void setBodyUploadIds(List<String> bodyUploadIds) {
        this.bodyUploadIds = bodyUploadIds;
    }
}
