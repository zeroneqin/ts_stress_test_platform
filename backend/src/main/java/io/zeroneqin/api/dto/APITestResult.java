package io.zeroneqin.api.dto;

import io.zeroneqin.base.domain.ApiTest;
import io.zeroneqin.base.domain.Schedule;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class APITestResult extends ApiTest {

    private String projectName;

    private String userName;

    private String gitlab;

    private String gitbranch;

    private Schedule schedule;

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

    @Override
    public String getGitlab() {
        return gitlab;
    }

    @Override
    public void setGitlab(String gitlab) {
        this.gitlab = gitlab;
    }

    @Override
    public String getGitbranch() {
        return gitbranch;
    }

    @Override
    public void setGitbranch(String gitbranch) {
        this.gitbranch = gitbranch;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
