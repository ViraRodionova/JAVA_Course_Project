package com.bepa.worktogether.pages;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.bepa.worktogether.R;
import com.bepa.worktogether.model.MockedData;

import java.util.ArrayList;

/**
 * Created by vera on 11/2/17.
 */

public class SearchGroupFragment extends Fragment {
    Toolbar toolbar;
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

        ArrayList<String> names = new ArrayList<String>();

        for (int i = 0; i < MockedData.groups.size(); i++) {
            names.add(MockedData.groups.get(i).getName());
        }

        ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, names);

        listView.setAdapter(groupAdapter);

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
