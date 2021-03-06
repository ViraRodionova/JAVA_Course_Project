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
import android.widget.TextView;

import com.bepa.worktogether.R;

/**
 * Created by vera on 11/3/17.
 */

public class CreateDialogFragment extends DialogFragment {
    EditText groupName;
    CreateDialogListener listener;
    String title;

    public interface CreateDialogListener {
        void onFinishCreateDialog(String inputText);
    }

    public void sendBackResult() {
        if (listener != null) listener.onFinishCreateDialog(groupName.getText().toString());
        dismiss();
    }

    public CreateDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static CreateDialogFragment newInstance(CreateDialogListener listener, String title) {
        CreateDialogFragment frag = new CreateDialogFragment();
        frag.setListener(listener);
        frag.setTitle(title);
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_create, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = (TextView) view.findViewById(R.id.create_group_title);
        textView.setText(title);

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

    public void setListener(CreateDialogListener listener) {
        this.listener = listener;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
