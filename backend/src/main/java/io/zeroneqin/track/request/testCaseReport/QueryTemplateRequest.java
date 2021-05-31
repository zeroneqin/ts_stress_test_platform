package io.zeroneqin.track.request.testCaseReport;

import io.zeroneqin.base.domain.TestCaseReportTemplate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryTemplateRequest extends TestCaseReportTemplate {
    Boolean queryDefault;
}
