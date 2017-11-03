package com.bepa.worktogether.pages;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;

import com.bepa.worktogether.R;
import com.bepa.worktogether.adapter.TaskAdapter;
import com.bepa.worktogether.model.Group;
import com.bepa.worktogether.model.MockedData;
import com.bepa.worktogether.model.Task;
import com.bepa.worktogether.model.User;

import java.util.ArrayList;

/**
 * Created by vera on 11/2/17.
 */

public class MainFragment extends Fragment
        implements CreateDialogFragment.CreateDialogListener, TaskDialogFragment.TaskDialogListener {

    TaskAdapter adapter;
    ArrayAdapter<String> groupAdapter;
    ListView lvMain;
    int selectedGroupIndex;
    Task selectedTask;
    Group selectedGroup;
    Toolbar toolbar;
    Spinner spinner;
    FragmentActivity myContext;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        spinner = new Spinner(container.getContext());

        toolbar.addView(spinner);

        return inflater.inflate(R.layout.content_main_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lvMain = (ListView) getActivity().findViewById(R.id.lvMain);

        final ArrayList<String> names = new ArrayList<String>();

        names.add("My Tasks");
        for (int i = 0; i < MockedData.user.getGroups().size(); i++) {
            names.add(MockedData.user.getGroups().get(i).getName());
        }

        selectedGroupIndex = 0;

        setListItems();


        groupAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.custom_spinner_item, names);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(groupAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGroupIndex = i;

                setListItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fabAddTask);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showCreateTaskDialog();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        fragmentManager = myContext.getSupportFragmentManager();
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        toolbar.removeView(spinner);
    }

    private void setListItems() {
        final ArrayList<Task> tasks;
        if (selectedGroupIndex == 0) {
            tasks = MockedData.user.getTasks();
        } else {
            tasks = MockedData.user.getGroups().get(selectedGroupIndex - 1).getTasks();
        }

        adapter = new TaskAdapter(getActivity(),
                android.R.layout.activity_list_item,
                tasks);

        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedGroupIndex == 0) {
                    selectedGroup = null;
                    selectedTask = MockedData.user.getTasks().get(position);
                } else {
                    selectedGroup = MockedData.user.getGroups().get(selectedGroupIndex - 1);
                    selectedTask = selectedGroup.getTasks().get(position);
                }

                showSetTaskStatusDialog();
            }
        });
    }

    private void showCreateTaskDialog() {
        CreateDialogFragment createGroupFragment = CreateDialogFragment.newInstance(this, "Enter Task Description:");
        createGroupFragment.show(fragmentManager, "dialog_create_task");
    }

    @Override
    public void onFinishCreateDialog(String taskDesc) {
        if (selectedGroupIndex == 0) {
            MockedData.createTask(taskDesc);
        } else {
            Group group = MockedData.user.getGroups().get(selectedGroupIndex - 1);
            MockedData.createTask(taskDesc, group);
        }
        setListItems();
    }

    private void showSetTaskStatusDialog() {
        TaskDialogFragment taskDialogFragment;
        if (selectedGroup != null) {
            taskDialogFragment = TaskDialogFragment.newInstance(this, selectedGroup.getPeople());
        } else {
            taskDialogFragment = TaskDialogFragment.newInstance(this, null);
        }

        taskDialogFragment.show(fragmentManager, "dialog_set_task_status");
    }

    @Override
    public void onTaskStatusChanged(int status) {
        selectedTask.setStatus(status);
        selectedTask = null;
        setListItems();
    }

    @Override
    public void onSetAssignee(String email) {
        User user = MockedData.getUserByEmail(email);

        if (user != null) {
            user.addTask(selectedTask);
            selectedTask.setAssignee(user);
        }


        selectedTask = null;
        setListItems();
    }
}
