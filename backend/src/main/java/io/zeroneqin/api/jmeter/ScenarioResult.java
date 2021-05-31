package io.zeroneqin.api.jmeter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScenarioResult {

    private Integer id;

    private String name;

    private long responseTime;

    private int error = 0;

    private int success = 0;

    private int totalAssertions = 0;

    private int passAssertions = 0;

    private final List<RequestResult> requestResults = new ArrayList<>();

    public void addResponseTime(long time) {
        this.responseTime += time;
    }

    public void addError(int count) {
        this.error += count;
    }

    public void addSuccess() {
        this.success++;
    }

    public void addTotalAssertions(int count) {
        this.totalAssertions += count;
    }

    public void addPassAssertions(int count) {
        this.passAssertions += count;
    }

    public int getTotal() {
        return error + success;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getTotalAssertions() {
        return totalAssertions;
    }

    public void setTotalAssertions(int totalAssertions) {
        this.totalAssertions = totalAssertions;
    }

    public int getPassAssertions() {
        return passAssertions;
    }

    public void setPassAssertions(int passAssertions) {
        this.passAssertions = passAssertions;
    }

    public List<RequestResult> getRequestResults() {
        return requestResults;
    }
}
