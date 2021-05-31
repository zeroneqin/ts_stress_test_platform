package io.zeroneqin.track.request.testplan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RunTestPlanRequest extends TestPlanRequest {
    private String userId;
    private String triggerMode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTriggerMode() {
        return triggerMode;
    }

    public void setTriggerMode(String triggerMode) {
        this.triggerMode = triggerMode;
    }
}
