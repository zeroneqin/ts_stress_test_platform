package io.zeroneqin.api.dto.definition;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RunCaseRequest {

    private String caseId;

    private String reportId;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}
