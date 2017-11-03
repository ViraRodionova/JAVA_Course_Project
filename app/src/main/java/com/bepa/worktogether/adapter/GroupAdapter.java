package com.bepa.worktogether.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bepa.worktogether.R;
import com.bepa.worktogether.model.Group;

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

    public View getView(int position, View convertView, @NonNull final ViewGroup parent){
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item_group_search, null);
        }

        final Group i = objects.get(position);

        if (i != null) {

            TextView gn = (TextView) v.findViewById(R.id.groupName);
            TextView gt = (TextView) v.findViewById(R.id.numOfTasks);
            TextView gp = (TextView) v.findViewById(R.id.numOfPeople);

            if (gn != null){
                gn.setText(i.getName());
            }
            if (gt != null){
                gt.setText(Integer.toString(i.getTasks().size()));
            }
            if (gp != null){
                gp.setText(Integer.toString(i.getPeople().size()));
            }
        }

         return v;
    }
}
