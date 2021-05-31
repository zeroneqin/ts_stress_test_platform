package io.zeroneqin.track.request.testplancase;

import io.zeroneqin.base.domain.TestCaseReviewTestCase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestReviewCaseBatchRequest extends TestCaseReviewTestCase {
    private String reviewId;
    private List<String> ids;
}
