package com.bepa.worktogether.model;

import android.support.annotation.Nullable;
import android.test.suitebuilder.annotation.Smoke;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by vera on 10/30/17.
 */

public class MockedData {
    public static User user;
    public static ArrayList<User> users;
    public static ArrayList<Group> groups;

    static {
        user = new User("user", "bepa.rdnv@gmail.com");
        User u2 = new User("u2", "email@example.com");

        Group g1 = new Group("group1", "Group 1");
        Group g2 = new Group("group2", "Group 2");

        Task t1 = new Task("1", "Task 1", 0);
        Task t2 = new Task("2", "Task 2", 0);
        Task t3 = new Task("3", "Task 3", 1);
        Task t4 = new Task("4", "Task 4", 0);
        Task t5 = new Task("5", "Task 5", 0);
        Task t6 = new Task("6", "Task 6", 2);
        Task t7 = new Task("7", "Task 7", 0);
        Task t8 = new Task("8", "Task 8", 0);

        g1.addTask(t1);
        g1.addTask(t2);
        g1.addTask(t3);
        g1.addTask(t4);
        g1.addTask(t5);

        g1.addUser(u2);

        g2.addTask(t6);
        g2.addTask(t7);
        g2.addTask(t8);

        user.addGroup(g1);
        user.addGroup(g2);

        user.tasks = new ArrayList<Task>(Arrays.asList(new Task[] {
                t1, t5, t8
        }));

        t1.setAssignee(user);
        t5.setAssignee(user);
        t8.setAssignee(user);

        groups = new ArrayList<>(Arrays.asList(new Group[] {
                g1, g2
        }));

        for(int i = 3; i < 21; i++) {
            groups.add(new Group("group" + i, "Group " + i));
        }

        users = new ArrayList<>(Arrays.asList(new User[] {
                user, u2
        }));
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

    public static void addGroup() {
        int id = groups.size() + 1;

        Group group = new Group("group" + id, "Group " + id);
        user.addGroup(group);

        groups.add(group);
    }

    public static void addGroup(String name) {
        int id = groups.size() + 1;

        Group group = new Group("group" + id, name);
        user.addGroup(group);

        groups.add(group);
    }

    public static void createTask(String taskDesc) {
        user.addTask(new Task("id", taskDesc, 0));
    }

    public static void createTask(String taskDesc, Group group) {
        Task task = new Task("id", taskDesc, 0);

        group.addTask(task);
    }
}
