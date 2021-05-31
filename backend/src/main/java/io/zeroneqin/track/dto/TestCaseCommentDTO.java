package io.zeroneqin.track.dto;

import io.zeroneqin.base.domain.TestCaseComment;
import lombok.Data;

@Data
public class TestCaseCommentDTO extends TestCaseComment {
    private String authorName;
}
