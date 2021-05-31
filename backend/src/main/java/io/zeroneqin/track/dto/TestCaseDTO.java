package io.zeroneqin.track.dto;

import io.zeroneqin.base.domain.TestCaseWithBLOBs;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestCaseDTO extends TestCaseWithBLOBs {

    private String maintainerName;
    private String apiName;
    private String performName;

}
