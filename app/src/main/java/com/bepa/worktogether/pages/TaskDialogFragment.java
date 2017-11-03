package com.bepa.worktogether.pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;

import com.bepa.worktogether.R;

/**
 * Created by vera on 11/3/17.
 */

public class TaskDialogFragment extends DialogFragment {
    RadioGroup radioGroup;
    TaskDialogListener listener;

    public interface TaskDialogListener {
        void onTaskStatusChanged(int status);
        void onSetAssignee();
    }

    public void sendBackResult(int status) {
        if (listener != null) listener.onTaskStatusChanged(status);
        dismiss();
    }

    public TaskDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static TaskDialogFragment newInstance(TaskDialogListener listener) {
        TaskDialogFragment frag = new TaskDialogFragment();
        frag.setListener(listener);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroupTask);
        
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
                int status = 0;

                if (radioButtonID == R.id.radioButtonOpened) status = 0;
                else if (radioButtonID == R.id.radioButtonInProgress) status = 1;
                else if (radioButtonID == R.id.radioButtonDone) status = 2;

                sendBackResult(status);
            }
        });
    }

    public void setListener(TaskDialogListener listener) {
        this.listener = listener;
    }
}
