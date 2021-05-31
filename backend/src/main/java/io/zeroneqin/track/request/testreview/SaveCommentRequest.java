package io.zeroneqin.track.request.testreview;

import io.zeroneqin.base.domain.TestCaseComment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveCommentRequest extends TestCaseComment {
     private String reviewId;

}
