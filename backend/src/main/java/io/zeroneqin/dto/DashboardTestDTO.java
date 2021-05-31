package io.zeroneqin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardTestDTO {
    private Long date;
    private Integer count;

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
