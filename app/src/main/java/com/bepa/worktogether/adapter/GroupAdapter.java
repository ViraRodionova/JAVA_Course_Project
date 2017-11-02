package com.bepa.worktogether.adapter;

import android.content.Context;
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

    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item_group_search, null);
        }

    /*
     * Recall that the variable position is sent in as an argument to this method.
     * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
     * iterates through the list we sent it)
     *
     * Therefore, i refers to the current Item object.
     */
        Group i = objects.get(position);

        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView gn = (TextView) v.findViewById(R.id.groupName);
            TextView gt = (TextView) v.findViewById(R.id.numOfTasks);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (gn != null){
                gn.setText(i.getName());
            }
            if (gt != null){
                gt.setText(Integer.toString(i.getTasks().size()));
            }
        }

        // the view must be returned to our activity
        return v;

    }
}
