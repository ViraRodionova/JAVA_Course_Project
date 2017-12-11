package com.bepa.worktogether.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by vera on 10/30/17.
 */

public class User {
    private String id;
    String email;
    ArrayList<Group> groups;
    ArrayList<Task> tasks;
    private DatabaseReference mDatabase;

    public User(String id, String email) {
        this.id = id;
        this.email = email;
        this.groups = new ArrayList<Group>();
        this.tasks = new ArrayList<Task>();
        setDatabaseReference();
    }

    public User(String id, String email, ArrayList<Group> groups, ArrayList<Task> tasks) {
        this.id = id;
        this.email = email;
        this.groups = groups;
        this.tasks = tasks;
        setDatabaseReference();
    }

    private void setDatabaseReference() {
        mDatabase = FirebaseDatabase
                .getInstance()
                .getReference();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
        group.addUser(this);
    }

    public void removeGroup(Group group) {
        group.removeUser(this.id);
        this.groups.remove(group);
    }

    public void removeTask(Task task, boolean cascade) {
        if (cascade) {
            for (Group group : groups) {
                if (group.hasTask(task)) group.removeTask(task);
            }
        }

        this.tasks.remove(task);
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

    public Group getGroupById(String groupId) {
        for(int i = 0; i < groups.size(); i++) {
            if(groups.get(i).getId().equals(groupId)) return groups.get(i);
        }

        return null;
    }

    public void createGroup(String groupName) {
        String groupId = mDatabase.child("groups").push().getKey();
        DatabaseReference groupRef = mDatabase.child("groups").child(groupId).getRef();

        Group group = new Group(groupId, groupName, this.id, this.email);

        groupRef.child("name").setValue(groupName);
        groupRef.child("adminId").setValue(this.id);
        groupRef.child("users").child(this.id).setValue(this.email);

        groups.add(group);
    }
}
