package com.bepa.worktogether.pages;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.bepa.worktogether.GroupPageActivity;
import com.bepa.worktogether.R;
import com.bepa.worktogether.adapter.GroupAdapter;
import com.bepa.worktogether.model.MockedData;

/**
 * Created by vera on 11/2/17.
 */

public class SearchGroupFragment extends Fragment {
    Toolbar toolbar;
    GroupAdapter groupAdapter;
    SearchView searchView;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_search_group_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchView = (SearchView) getActivity().findViewById(R.id.svGroups);

        listView = (ListView) getActivity().findViewById(R.id.lvGroupSearch);

        groupAdapter = new GroupAdapter(getActivity(),
                android.R.layout.activity_list_item,
                MockedData.groups);

        listView.setAdapter(groupAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), GroupPageActivity.class);

                System.out.println(MockedData.groups.get(i).getName());

                intent.putExtra("GROUP_ID", MockedData.groups.get(i).getId());

                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println("================== SUBMIT ==================");
                System.out.println(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println("================== SEARCH ==================");
                System.out.println(s);
                return false;
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
