package com.bepa.worktogether.pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.bepa.worktogether.R;

/**
 * Created by vera on 11/3/17.
 */

public class CreateGroupFragment extends DialogFragment {
    public interface CreateGroupListener {
        void onFinishCreateDialog(String inputText);
    }

    public void sendBackResult() {
        if (listener != null) listener.onFinishCreateDialog(groupName.getText().toString());
        dismiss();
    }

    EditText groupName;
    CreateGroupListener listener;

    public CreateGroupFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static CreateGroupFragment newInstance(CreateGroupListener listener) {
        CreateGroupFragment frag = new CreateGroupFragment();
        frag.setListener(listener);
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_create_group, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        groupName = (EditText) view.findViewById(R.id.create_group_name);

        groupName.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button button = (Button) view.findViewById(R.id.create_group_cancel);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        button = (Button) view.findViewById(R.id.create_group_ok);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendBackResult();
            }
        });
    }

    public void setListener(CreateGroupListener listener) {
        this.listener = listener;
    }
}
