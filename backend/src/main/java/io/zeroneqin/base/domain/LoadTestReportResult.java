package io.zeroneqin.base.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoadTestReportResult implements Serializable {
    private String id;

    private String reportId;

    private String reportKey;

    private String reportValue;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportKey() {
        return reportKey;
    }

    public void setReportKey(String reportKey) {
        this.reportKey = reportKey;
    }

    public String getReportValue() {
        return reportValue;
    }

    public void setReportValue(String reportValue) {
        this.reportValue = reportValue;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}