package com.bepa.worktogether.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vera on 10/30/17.
 */

public class Group implements Task.TaskValueChangedListener {
    private String id;
    private String name;
    private String adminId;
    private ArrayList<User> people;
    private HashMap<String, String> users;
    private ArrayList<Task> tasks;

    private DatabaseReference mDatabase;

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
        this.people = new ArrayList<User>();
        this.tasks = new ArrayList<Task>();
        setDatabaseReference();
    }

    public Group(String id, String name, String adminId) {
        this.id = id;
        this.name = name;
        this.adminId = adminId;
        this.people = new ArrayList<User>();
        this.tasks = new ArrayList<Task>();
        setDatabaseReference();
    }

    private void setDatabaseReference() {
        mDatabase = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("groups")
                .child(id)
                .getRef();
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

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
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
        task.setListener(this);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public HashMap<String, String> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, String> users) {
        this.users = users;
    }

    public boolean hasTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i) == task) return true;
        }

        return false;
    }

    public Task getTaskById(String taskId) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(taskId)) return tasks.get(i);
        }

        return null;
    }

    public String getUserEmailById(String userId) {
        return this.users.get(userId);
    }

    public boolean isAdmin(String userId) {
        return this.adminId.equals(userId);
    }

    @Override
    public boolean equals(Object obj) {
        return id.equals(((Group) obj).id);
    }

    @Override
    public void onStatusChanged(String taskId, int status) {
        mDatabase.child("tasks").child(taskId).child("status").setValue(status);
    }
}
