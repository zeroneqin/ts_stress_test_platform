package io.zeroneqin.base.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoadTestFile implements Serializable {
    private String testId;

    private String fileId;

    private static final long serialVersionUID = 1L;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
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