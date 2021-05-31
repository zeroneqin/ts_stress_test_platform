package io.zeroneqin.base.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestCaseIssues implements Serializable {
    private String id;

    private String testCaseId;

    private String issuesId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getIssuesId() {
        return issuesId;
    }

    public void setIssuesId(String issuesId) {
        this.issuesId = issuesId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}