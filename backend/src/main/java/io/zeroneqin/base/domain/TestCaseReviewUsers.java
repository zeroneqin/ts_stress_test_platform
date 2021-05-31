package io.zeroneqin.base.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestCaseReviewUsers implements Serializable {
    private String reviewId;

    private String userId;

    private static final long serialVersionUID = 1L;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}