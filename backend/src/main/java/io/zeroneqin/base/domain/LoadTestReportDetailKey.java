package io.zeroneqin.base.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoadTestReportDetailKey implements Serializable {
    private String reportId;

    private Long part;

    private static final long serialVersionUID = 1L;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Long getPart() {
        return part;
    }

    public void setPart(Long part) {
        this.part = part;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}