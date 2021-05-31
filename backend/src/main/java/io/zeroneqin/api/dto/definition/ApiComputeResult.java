package io.zeroneqin.api.dto.definition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiComputeResult {
    private String apiDefinitionId;
    private String caseTotal;
    private String status;
    private String passRate;

    public String getApiDefinitionId() {
        return apiDefinitionId;
    }

    public void setApiDefinitionId(String apiDefinitionId) {
        this.apiDefinitionId = apiDefinitionId;
    }

    public String getCaseTotal() {
        return caseTotal;
    }

    public void setCaseTotal(String caseTotal) {
        this.caseTotal = caseTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassRate() {
        return passRate;
    }

    public void setPassRate(String passRate) {
        this.passRate = passRate;
    }
}
