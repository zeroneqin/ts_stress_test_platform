package io.zeroneqin.base.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestCaseFile implements Serializable {
    private String caseId;

    private String fileId;

    private static final long serialVersionUID = 1L;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}