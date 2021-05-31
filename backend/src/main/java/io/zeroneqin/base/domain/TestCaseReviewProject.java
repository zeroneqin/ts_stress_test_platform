package io.zeroneqin.base.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestCaseReviewProject implements Serializable {
    private String reviewId;

    private String projectId;

    private static final long serialVersionUID = 1L;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
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