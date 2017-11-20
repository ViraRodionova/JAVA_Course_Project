package com.bepa.worktogether.pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bepa.worktogether.R;
import com.bepa.worktogether.model.Task;
import com.bepa.worktogether.model.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vera on 11/3/17.
 */

public class TaskDialogFragment extends DialogFragment {
    RadioGroup radioGroup;
    RadioGroup usersGroup;
    TaskDialogListener listener;
    HashMap<String, String> users;
    Task task;
    RadioButton[] buttonsUsers;
    String selectedUser;

    public interface TaskDialogListener {
        void onTaskStatusChanged(int status);
        void onSetAssignee(String selectedUser);
        void onRemoveAssignee();
        void onRemoveTask();
    }

    public void sendBackResult(int status) {
        if (listener != null) listener.onTaskStatusChanged(status);
        dismiss();
    }

    public void sendBackResult(String selectedUser) {
        if (listener != null) listener.onSetAssignee(selectedUser);
        dismiss();
    }

    public void sendBackResult() {
        if (listener != null) listener.onRemoveAssignee();
        dismiss();
    }

    public void removeTask() {
        if (listener != null) listener.onRemoveTask();
        dismiss();
    }

    public TaskDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static TaskDialogFragment newInstance(
            TaskDialogListener listener, HashMap<String, String> users, Task task) {

        TaskDialogFragment frag = new TaskDialogFragment();
        frag.setListener(listener);
        frag.setUsers(users);
        frag.setTask(task);
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_task_status, container);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroupTask);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (buttonsUsers != null) {
                    if (i == R.id.radioButtonAssignee) {
                        for (RadioButton buttonsUser : buttonsUsers) {
                            buttonsUser.setEnabled(true);
                        }
                    } else {
                        for (RadioButton buttonsUser : buttonsUsers) {
                            buttonsUser.setEnabled(false);
                        }
                    }
                }
            }
        });

        usersGroup = (RadioGroup) view.findViewById(R.id.radioGroupUsers);
        RadioButton radioButton;


        if (task.hasAssignee()) {
            radioButton = (RadioButton) view.findViewById(R.id.radioButtonAssignee);
            radioButton.setText(String.format("Remove Assignee (%s)", task.getAssignee()));
        } else if (users != null) {
            usersGroup.setVisibility(View.VISIBLE);

            buttonsUsers = new RadioButton[users.size()];

            int i = 0;
            for (String user : users.values()) {
                radioButton = new RadioButton(getContext());
                radioButton.setText(user);
                radioButton.setEnabled(false);
                usersGroup.addView(radioButton);
                buttonsUsers[i] = radioButton;
                i++;
            }

            usersGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    selectedUser = ((RadioButton) view.findViewById(i)).getText().toString();
                }
            });
        }

        Button button = (Button) view.findViewById(R.id.taskCancel);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        button = (Button) view.findViewById(R.id.taskOk);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int radioButtonID = radioGroup.getCheckedRadioButtonId();

                if (radioButtonID == R.id.radioButtonOpened) sendBackResult(0);
                else if (radioButtonID == R.id.radioButtonInProgress) sendBackResult(1);
                else if (radioButtonID == R.id.radioButtonDone) sendBackResult(2);
                else if (radioButtonID == R.id.radioButtonRemove) removeTask();
                else if (radioButtonID == R.id.radioButtonAssignee) {
                    if (task.hasAssignee()) sendBackResult();
                    else sendBackResult(selectedUser);
                }
            }
        });
    }

    public void setListener(TaskDialogListener listener) {
        this.listener = listener;
    }

    public void setUsers(HashMap<String, String> users) {
        this.users = users;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
