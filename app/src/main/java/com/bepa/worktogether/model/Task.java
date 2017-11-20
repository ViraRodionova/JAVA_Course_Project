package com.bepa.worktogether.model;

/**
 * Created by vera on 10/29/17.
 */

public class Task {
    String id;
    String name;
    String userId;
    int status;

    public Task(String id) {
        this.id = id;
    }

    public Task(String id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignee() {
        return userId;
    }

    public void setAssignee(String assignee) {
        this.userId = assignee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void removeAssignee() {
        this.userId = null;
    }

    public boolean hasAssignee() {
        if (userId == null) return false;

        return true;
    }
}