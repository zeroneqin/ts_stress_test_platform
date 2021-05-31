package io.zeroneqin.base.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class ApiTestReportDetail implements Serializable {
    private String reportId;

    private String testId;

    private byte[] content;

    private static final long serialVersionUID = 1L;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}