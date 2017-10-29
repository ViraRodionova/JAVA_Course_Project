package com.bepa.worktogether.model;

/**
 * Created by vera on 10/29/17.
 */

public class Task {
    private String id;
    String name;
    String group_id;
    int status;

    public Task(String id, String name, String group_id, int status) {
        this.id = id;
        this.name = name;
        this.group_id = group_id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}