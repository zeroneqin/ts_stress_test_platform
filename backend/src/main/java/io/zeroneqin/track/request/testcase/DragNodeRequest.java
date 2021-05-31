package io.zeroneqin.track.request.testcase;

import io.zeroneqin.base.domain.TestCaseNode;
import io.zeroneqin.track.dto.TestCaseNodeDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DragNodeRequest extends TestCaseNode {

    List<String> nodeIds;
    TestCaseNodeDTO nodeTree;
}
