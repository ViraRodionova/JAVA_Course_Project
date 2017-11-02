package com.bepa.worktogether.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bepa.worktogether.R;
import com.bepa.worktogether.model.User;

import java.util.ArrayList;

/**
 * Created by vera on 11/2/17.
 */

public class UserAdapter extends ArrayAdapter<User> {
    private ArrayList<User> objects;

    public UserAdapter(Context context, int textViewResourceId, ArrayList<User> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    public View getView(int position, View convertView, @NonNull final ViewGroup parent){
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item_user, null);
        }

        final User i = objects.get(position);

        if (i != null) {

            TextView ue = (TextView) v.findViewById(R.id.userEmail);

            if (ue != null){
                ue.setText(i.getEmail());
            }
        }

        return v;

    }
}
