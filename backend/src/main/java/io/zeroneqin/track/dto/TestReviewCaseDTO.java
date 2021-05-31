package io.zeroneqin.track.dto;

import io.zeroneqin.base.domain.TestCaseWithBLOBs;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestReviewCaseDTO extends TestCaseWithBLOBs {
    private String reviewer;
    private String reviewerName;
    private String reviewStatus;
    private String results;
    private String reviewId;
    private String caseId;
    private String issues;
    private String model;
    private String projectName;
}
