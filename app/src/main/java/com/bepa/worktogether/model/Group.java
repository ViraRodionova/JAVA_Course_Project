package com.bepa.worktogether.model;

import java.util.ArrayList;

/**
 * Created by vera on 10/30/17.
 */

public class Group {
    private String id;
    private String name;
    private ArrayList<User> people;
    private ArrayList<Task> tasks;

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
        this.people = new ArrayList<User>();
        this.tasks = new ArrayList<Task>();
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

    public void addUser(User user) {
        this.people.add(user);
    }

    public void removeUser(User user) { this.people.remove(user); }

    public ArrayList<User> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<User> people) {
        this.people = people;
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

    public boolean hasTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i) == task) return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return id.equals(((Group) obj).id);
    }
}
