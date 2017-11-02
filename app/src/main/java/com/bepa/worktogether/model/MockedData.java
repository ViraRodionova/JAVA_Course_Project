package com.bepa.worktogether.model;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by vera on 10/30/17.
 */

public class MockedData {
    public static User user;
    public static ArrayList<Group> groups;

    static {
        user = new User("user", "bepa.rdnv@gmail.com");

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
    }

    @Nullable
    public static Group getGroupById(String id) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getId().equals(id))
                return groups.get(i);
        }

        return null;
    }
}
