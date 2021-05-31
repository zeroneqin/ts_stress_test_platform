package io.zeroneqin.performance.base;

import lombok.Data;

@Data
public class Errors {

    private String errorType;
    private String errorNumber;
    private String percentOfErrors;
    private String percentOfAllSamples;

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
