package io.zeroneqin.api.dto.definition;

import io.zeroneqin.base.domain.ApiDefinitionWithBLOBs;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiDefinitionResult extends ApiDefinitionWithBLOBs {

    private String projectName;

    private String userName;

    private String caseTotal;

    private String caseStatus;

    private String casePassingRate;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCaseTotal() {
        return caseTotal;
    }

    public void setCaseTotal(String caseTotal) {
        this.caseTotal = caseTotal;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getCasePassingRate() {
        return casePassingRate;
    }

    public void setCasePassingRate(String casePassingRate) {
        this.casePassingRate = casePassingRate;
    }
}
