package io.zeroneqin.base.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TestPlanTestCaseWithBLOBs extends TestPlanTestCase implements Serializable {
    private String results;

    private String issues;

    private static final long serialVersionUID = 1L;

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}