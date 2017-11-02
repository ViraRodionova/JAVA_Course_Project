package com.bepa.worktogether.model;

import java.util.ArrayList;

/**
 * Created by vera on 10/30/17.
 */

public class User {
    private String id;
    String email;
    ArrayList<Group> groups;
    ArrayList<Task> tasks;

    public User(String id, String email) {
        this.id = id;
        this.email = email;
        this.groups = new ArrayList<Group>();
        this.tasks = new ArrayList<Task>();
    }

    public User(String id, String email, ArrayList<Group> groups, ArrayList<Task> tasks) {
        this.id = id;
        this.email = email;
        this.groups = groups;
        this.tasks = tasks;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean hasGroup(Group group) {
        boolean res = false;

        for(int i = 0; i < groups.size() && !res; i++) {
            if(group.equals(groups.get(i))) res = true;
        }

        return res;
    }
}
