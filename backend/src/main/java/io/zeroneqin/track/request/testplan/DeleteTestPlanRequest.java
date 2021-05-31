package io.zeroneqin.track.request.testplan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteTestPlanRequest extends TestPlanRequest {
    /**
     * 是否强制删除（删除项目时不检查关联关系，强制删除资源）
     */

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private boolean forceDelete = false;

    public boolean isForceDelete() {
        return forceDelete;
    }

    public void setForceDelete(boolean forceDelete) {
        this.forceDelete = forceDelete;
    }
}
