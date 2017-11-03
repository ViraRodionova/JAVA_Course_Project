package com.bepa.worktogether.model;

/**
 * Created by vera on 10/29/17.
 */

public class Task {
    private String id;
    String name;
    User assignee;
    int status;

    public Task(String id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void removeAssignee() {
        this.assignee = null;
    }
}