package com.bepa.worktogether.model;

import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by vera on 10/30/17.
 */

public class MockedData {
    public static User user;
    public static ArrayList<Group> groups;
    static ArrayList<User> users;
    static private DatabaseReference mDatabase;

    static {
        groups = new ArrayList<Group>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Nullable
    public static Group getGroupById(String id) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getId().equals(id))
                return groups.get(i);
        }

        return null;
    }

    @Nullable
    public static User getUserByEmail(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email))
                return users.get(i);
        }

        return null;
    }

    public static void addGroup(String name) {
        int id = groups.size() + 1;

        Group group = new Group("group" + id, name);
        user.addGroup(group);

        groups.add(group);
    }

    private static void addGroup(HashMap<String, Object> group, String id) {
        HashMap<String, Object> hmUsers = (HashMap<String, Object>) group.get("users");

        if (hmUsers != null && hmUsers.keySet().contains(user.getId())) {
            setGroupListener(id);
        }
    }

    private static void setGroupListener(final String groupId) {
        mDatabase.child("groups").child(groupId).getRef().addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> group = (HashMap<String,Object>) dataSnapshot.getValue();

                        Group res = getGroupById(groupId);

                        if (res == null) {
                            res = new Group(
                                    groupId,
                                    group.get("name").toString(),
                                    group.get("adminId").toString());
                        }

                        HashMap<String, String> hmUsers = (HashMap<String, String>) group.get("users");
                        res.setUsers(hmUsers);

                        HashMap<String, Object> hmTasks = (HashMap<String, Object>) group.get("tasks");

                        if (hmTasks != null) {
                            Set<String> tasks = hmTasks.keySet();

                            for(String taskId : tasks) {
                                HashMap<String, Object> taskH = (HashMap<String, Object>) hmTasks.get(taskId);

                                if (taskH.get("description") != null && taskH.get("status") != null) {
                                    Task task = res.getTaskById(taskId);

                                    if (task == null) {
                                        task = new Task(taskId);
                                        task.setName(taskH.get("description").toString());
                                        task.setStatus(Integer.parseInt(taskH.get("status").toString()));
                                        task.setGroupId(res.getId());
                                        if (taskH.get("user") != null) {
                                            String userId = taskH.get("user").toString();
                                            task.setAssignee(userId, res.getUserEmailById(userId));
                                        }
                                        res.addTask(task);
                                    } else {
                                        task.setName(taskH.get("description").toString());
                                        task.setStatus(Integer.parseInt(taskH.get("status").toString()));
                                        task.setGroupId(res.getId());
                                        if (taskH.get("user") != null) {
                                            String userId = taskH.get("user").toString();
                                            task.setAssignee(userId, res.getUserEmailById(userId));
                                        } else task.setAssignee(null, null);
                                    }
                                }
                            }
                        }

                        if (getGroupById(groupId) == null) groups.add(res);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    public static void removeGroup(Group group) {
        for (User user : group.getPeople()) {
            user.removeGroup(group);
        }

        groups.remove(group);
    }

    public static void setUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            user = new User(firebaseUser.getUid(), firebaseUser.getEmail());
            initDatabase();
        } else user  = null;
    }

    public static void initDatabase() {
        mDatabase.child("groups").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();

                Set<String> hkGroups = td.keySet();

                for(String group : hkGroups) {
                   addGroup((HashMap<String, Object>) td.get(group), group);
                }

                user.setGroups(groups);

                mDatabase.child("groups").getRef().removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
            }
        });
    }
}
