package io.zeroneqin.api.dto.datacount.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author song.tianyang
 * @Date 2020/12/17 5:04 下午
 * @Description
 */
@Getter
@Setter
public class ScheduleInfoRequest {
    private String taskID;
    private boolean enable;

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
