package io.zeroneqin.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeleteAPITestRequest {

    private String id;
    /**
     * 是否强制删除（删除项目时不检查关联关系，强制删除资源）
     */
    private boolean forceDelete = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isForceDelete() {
        return forceDelete;
    }

    public void setForceDelete(boolean forceDelete) {
        this.forceDelete = forceDelete;
    }
}
