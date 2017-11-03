package com.bepa.worktogether.pages;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bepa.worktogether.GroupPageActivity;
import com.bepa.worktogether.R;
import com.bepa.worktogether.adapter.GroupAdapter;
import com.bepa.worktogether.model.Group;
import com.bepa.worktogether.model.MockedData;

import java.util.ArrayList;

/**
 * Created by vera on 11/2/17.
 */

public class SearchGroupFragment extends Fragment implements CreateGroupDialogFragment.CreateGroupListener {
    GroupAdapter groupAdapter;
    ListView listView;
    private FragmentActivity myContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_search_group_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getActivity().findViewById(R.id.lvGroupSearch);
        setGroups(MockedData.groups);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fabAddGroup);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showCreateGroupDialog();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setGroups(final ArrayList<Group> groups) {
        groupAdapter = new GroupAdapter(getActivity(),
                android.R.layout.activity_list_item,
                groups);

        listView.setAdapter(groupAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), GroupPageActivity.class);

                System.out.println(groups.get(i).getName());

                intent.putExtra("GROUP_ID", groups.get(i).getId());

                startActivity(intent);
            }
        });

    }

    private void showCreateGroupDialog() {
        FragmentManager fm = myContext.getSupportFragmentManager();
        CreateGroupDialogFragment createGroupFragment = CreateGroupDialogFragment.newInstance(this, "Enter Group Name:");
        createGroupFragment.show(fm, "dialog_create_group");
    }

    @Override
    public void onFinishCreateDialog(String groupName) {
        MockedData.addGroup(groupName);
        setGroups(MockedData.groups);
    }
}
