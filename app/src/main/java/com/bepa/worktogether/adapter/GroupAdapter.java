package com.bepa.worktogether.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bepa.worktogether.R;
import com.bepa.worktogether.model.Group;
import com.bepa.worktogether.model.MockedData;

import java.util.ArrayList;

/**
 * Created by vera on 11/2/17.
 */

public class GroupAdapter extends ArrayAdapter<Group> {
    private ArrayList<Group> objects;

    public GroupAdapter(Context context, int textViewResourceId, ArrayList<Group> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item_group_search, null);
        }

        Group i = objects.get(position);

        if (i != null) {

            TextView gn = (TextView) v.findViewById(R.id.groupName);
            TextView gt = (TextView) v.findViewById(R.id.numOfTasks);
            Button btn = (Button) v.findViewById(R.id.btnJoinLeave);

            if (gn != null){
                gn.setText(i.getName());

                if (btn != null) {
                    if (MockedData.user.hasGroup(i)) {
                        btn.setText("Leave");
                        btn.setBackgroundColor(parent.getResources().getColor(R.color.colorAccent));
                    } else {
                        btn.setText("Join");
                        btn.setBackgroundColor(parent.getResources().getColor(R.color.colorPrimary));
                    }
                }
            }
            if (gt != null){
                gt.setText(Integer.toString(i.getTasks().size()));
            }
        }

         return v;

    }
}
