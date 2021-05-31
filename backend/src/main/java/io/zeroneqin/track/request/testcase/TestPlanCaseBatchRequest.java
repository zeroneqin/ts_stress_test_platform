package io.zeroneqin.track.request.testcase;

import io.zeroneqin.base.domain.TestPlanTestCase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestPlanCaseBatchRequest extends TestPlanTestCase {
    private List<String> ids;
}
