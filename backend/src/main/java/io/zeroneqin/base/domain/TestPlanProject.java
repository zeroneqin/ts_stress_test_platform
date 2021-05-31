package io.zeroneqin.base.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestPlanProject implements Serializable {
    private String testPlanId;

    private String projectId;

    private static final long serialVersionUID = 1L;

    public String getTestPlanId() {
        return testPlanId;
    }

    public void setTestPlanId(String testPlanId) {
        this.testPlanId = testPlanId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}