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
import com.bepa.worktogether.model.User;

import java.util.ArrayList;

/**
 * Created by vera on 11/3/17.
 */

public class TaskDialogFragment extends DialogFragment {
    RadioGroup radioGroup;
    RadioGroup usersGroup;
    TaskDialogListener listener;
    ArrayList<User> users;
    RadioButton[] buttonsUsers;
    String selectedUser;

    public interface TaskDialogListener {
        void onTaskStatusChanged(int status);
        void onSetAssignee(String selectedUser);
    }

    public void sendBackResult(int status) {
        if (listener != null) listener.onTaskStatusChanged(status);
        dismiss();
    }

    public void sendBackResult() {
        if (listener != null) listener.onSetAssignee(selectedUser);
        dismiss();
    }

    public TaskDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static TaskDialogFragment newInstance(TaskDialogListener listener, ArrayList<User> users) {
        TaskDialogFragment frag = new TaskDialogFragment();
        frag.setListener(listener);
        frag.setUsers(users);
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

        if (users != null) {
            radioButton = (RadioButton) view.findViewById(R.id.radioButtonAssignee);
            radioButton.setVisibility(View.VISIBLE);
            usersGroup.setVisibility(View.VISIBLE);

            buttonsUsers = new RadioButton[users.size()];

            for (int i = 0; i < users.size(); i++) {
                radioButton = new RadioButton(getContext());
                radioButton.setText(users.get(i).getEmail());
                radioButton.setEnabled(false);
                usersGroup.addView(radioButton);
                buttonsUsers[i] = radioButton;
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
                else if (radioButtonID == R.id.radioButtonAssignee) sendBackResult();
            }
        });
    }

    public void setListener(TaskDialogListener listener) {
        this.listener = listener;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
