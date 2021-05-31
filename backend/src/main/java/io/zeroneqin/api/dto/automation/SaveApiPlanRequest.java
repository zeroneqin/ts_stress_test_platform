package io.zeroneqin.api.dto.automation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveApiPlanRequest {
    private List<String> planIds;
    private List<String> apiIds;
    private List<String> scenarioIds;


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

    private String projectId;

    public List<String> getPlanIds() {
        return planIds;
    }

    public void setPlanIds(List<String> planIds) {
        this.planIds = planIds;
    }

    public List<String> getApiIds() {
        return apiIds;
    }

    public void setApiIds(List<String> apiIds) {
        this.apiIds = apiIds;
    }

    public List<String> getScenarioIds() {
        return scenarioIds;
    }

    public void setScenarioIds(List<String> scenarioIds) {
        this.scenarioIds = scenarioIds;
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

    public String getName() {
        return name;
    }

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
