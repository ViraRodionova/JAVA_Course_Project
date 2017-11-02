package com.bepa.worktogether.pages;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;

import com.bepa.worktogether.NavigationActivity;
import com.bepa.worktogether.R;
import com.bepa.worktogether.adapter.TaskAdapter;
import com.bepa.worktogether.model.MockedData;
import com.bepa.worktogether.model.Task;

import java.util.ArrayList;

/**
 * Created by vera on 11/2/17.
 */

public class MainFragment extends Fragment {
    TaskAdapter adapter;
    ArrayAdapter<String> groupAdapter;
    ListView lvMain;
    int selectedGroupIndex;
    Toolbar toolbar;
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        spinner = new Spinner(container.getContext());

        toolbar.addView(spinner);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_main_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lvMain = (ListView) getActivity().findViewById(R.id.lvMain);

        final ArrayList<String> names = new ArrayList<String>();

        names.add("My Tasks");
        for (int i = 0; i < MockedData.user.getGroups().size(); i++) {
            names.add(MockedData.user.getGroups().get(i).getName());
        }

        selectedGroupIndex = 0;

        setListItems();


        groupAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.custom_spinner_item, names);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(groupAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGroupIndex = i;

                setListItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        toolbar.removeView(spinner);
    }

    private void setListItems() {
        final ArrayList<Task> tasks;
        if (selectedGroupIndex == 0) {
            tasks = MockedData.user.getTasks();
        } else {
            tasks = MockedData.user.getGroups().get(selectedGroupIndex - 1).getTasks();
        }

        adapter = new TaskAdapter(getActivity(),
                android.R.layout.activity_list_item,
                tasks);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task object;

                if (selectedGroupIndex == 0) {
                    object = MockedData.user.getTasks().get(position);
                } else {
                    object = MockedData.user.getGroups().get(selectedGroupIndex - 1).getTasks().get(position);
                }

                if (object.getStatus() < 2) object.setStatus(object.getStatus() + 1);

                final ArrayList<Task> tasks1;

                if (selectedGroupIndex == 0) {
                    tasks1 = MockedData.user.getTasks();
                } else {
                    tasks1 = MockedData.user.getGroups().get(selectedGroupIndex - 1).getTasks();
                }

                adapter = new TaskAdapter(getActivity(),
                        android.R.layout.activity_list_item,
                        tasks1);

                lvMain.setAdapter(adapter);
            }
        });
    }
}
