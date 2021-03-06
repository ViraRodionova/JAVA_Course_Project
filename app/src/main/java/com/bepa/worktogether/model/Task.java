package com.bepa.worktogether.model;

import com.bepa.worktogether.pages.CreateDialogFragment;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by vera on 10/29/17.
 */

public class Task {
    private String id;
    private String name;
    private String userId;
    private String userEmail;
    private String groupId;
    private int status;

    TaskValueChangedListener listener;

    public interface TaskValueChangedListener {
        void onStatusChanged(String taskId, int status);
        void onAssigneeChanged(String taskId, String userId);
    }

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
        return userEmail;
    }

    public void setAssignee(String userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public void changeAssignee(String userId, String userEmail) {
        if(listener != null) listener.onAssigneeChanged(id, userId);

        this.userId = userId;
        this.userEmail = userEmail;
    }

    public void setListener(TaskValueChangedListener listener) {
        this.listener = listener;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;

        if (listener != null) listener.onStatusChanged(id, status);
    }

    public void removeAssignee() {
        this.userId = null;
        this.userEmail = null;

        if (listener != null) listener.onAssigneeChanged(id, null);
    }

    public boolean hasAssignee() {
        return userId != null;
    }

    public boolean hasAssignee(String userId) {
        return this.userId != null && this.userId.equals(userId);
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}