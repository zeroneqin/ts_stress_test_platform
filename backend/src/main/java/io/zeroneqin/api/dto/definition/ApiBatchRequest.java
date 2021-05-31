package io.zeroneqin.api.dto.definition;

import io.zeroneqin.base.domain.ApiDefinitionWithBLOBs;
import io.zeroneqin.controller.request.OrderRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiBatchRequest extends ApiDefinitionWithBLOBs {
    private List<String> ids;
    private List<OrderRequest> orders;
    private String projectId;

    /**
     * isSelectAllDate：选择的数据是否是全部数据（全部数据是不受分页影响的数据）
     * filters: 数据状态
     * name：如果是全部数据，那么表格如果历经查询，查询参数是什么
     * moduleIds： 哪些模块的数据
     * unSelectIds：是否在页面上有未勾选的数据，有的话他们的ID是哪些。
     * filters/name/moduleIds/unSeelctIds 只在isSelectAllDate为true时需要。为了让程序能明确批量的范围。
     */
    private boolean isSelectAllDate;

    private List<String> filters;

    private String name;

    private List<String> moduleIds;

    private List<String> unSelectIds;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<OrderRequest> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderRequest> orders) {
        this.orders = orders;
    }

    @Override
    public String getProjectId() {
        return projectId;
    }

    @Override
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public boolean isSelectAllDate() {
        return isSelectAllDate;
    }

    public void setSelectAllDate(boolean selectAllDate) {
        isSelectAllDate = selectAllDate;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public List<String> getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(List<String> moduleIds) {
        this.moduleIds = moduleIds;
    }

    public List<String> getUnSelectIds() {
        return unSelectIds;
    }

    public void setUnSelectIds(List<String> unSelectIds) {
        this.unSelectIds = unSelectIds;
    }
}
