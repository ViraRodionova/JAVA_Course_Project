package com.bepa.worktogether.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
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
    private ArrayList<String> emails;

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

    public Group(String id, String name, String adminId, String adminEmail) {
        this.id = id;
        this.name = name;
        this.adminId = adminId;
        this.people = new ArrayList<User>();
        this.tasks = new ArrayList<Task>();
        this.users = new HashMap<String, String>();
        this.emails = new ArrayList<String>();
        this.emails.add(adminEmail);
        this.users.put(adminId, adminEmail);
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

    public void removeUser(String userId) {
        for (Task task : tasks) {
            if (task.hasAssignee(userId)) {
                task.setAssignee(null, null);
            }
        }
        this.users.remove(userId);

        mDatabase.child("users").child(userId).setValue(null);
    }

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

    public void createTask(String taskDesc) {
        String taskId = mDatabase.child("tasks").push().getKey();
        DatabaseReference taskRef = mDatabase.child("tasks").child(taskId).getRef();

        Task task = new Task(taskId, taskDesc, 0);
        task.setListener(this);

        taskRef.child("description").setValue(taskDesc);
        taskRef.child("status").setValue(0);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
        mDatabase.child("tasks").child(task.getId()).setValue(null);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<String> getUsers() {
        return emails;
    }

    public void setUsers(HashMap<String, String> users) {
        this.users = users;
        this.emails = new ArrayList<String>(users.values());
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

    public String getUserIdByEmail(String userEmail) {
        // TODO: rewrite

        for (String key : users.keySet()) {
            if (users.get(key).equals(userEmail)) return key;
        }

        return null;
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

    @Override
    public void onAssigneeChanged(String taskId, String userId) {
        mDatabase.child("tasks").child(taskId).child("user").setValue(userId);
    }
}
