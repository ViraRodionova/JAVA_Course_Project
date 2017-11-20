package com.bepa.worktogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.bepa.worktogether.adapter.UserAdapter;
import com.bepa.worktogether.model.Group;
import com.bepa.worktogether.model.MockedData;

import java.util.ArrayList;
import java.util.List;

public class GroupPageActivity extends AppCompatActivity {
    ArrayAdapter<String> userAdapter;
    ListView lvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        final Group group = MockedData.getGroupById(intent.getStringExtra("GROUP_ID"));

        if (group == null) finish();

        lvUsers = (ListView) findViewById(R.id.gpPeopleList);
        setUsers(group);

        ArrayList<String> gridData = new ArrayList<String>();

        gridData.add("Group Name:");
        gridData.add(group.getName());

        gridData.add("Tasks Count:");
        gridData.add(Integer.toString(group.getTasks().size()));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gridData);

        GridView gridView = (GridView) findViewById(R.id.gpInfoGrid);
        gridView.setAdapter(adapter);

        final Button btn = (Button) findViewById(R.id.gpJoinLeave);

        if (btn != null) {
            if (group.isAdmin(MockedData.user.getId())) {
                btn.setText("Remove");
                btn.setBackgroundColor(getResources().getColor(R.color.red));
            }
            else if (MockedData.user.hasGroup(group)) {
                btn.setText("Leave");
                btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            } else {
                btn.setText("Join");
                btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (group.isAdmin(MockedData.user.getId())) {
                        MockedData.removeGroup(group);
                        finish();
                    }
                    else if (MockedData.user.hasGroup(group)) {
                        MockedData.user.removeGroup(group);

                        setUsers(group);

                        btn.setText("Join");
                        btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        MockedData.user.addGroup(group);

                        setUsers(group);

                        btn.setText("Leave");
                        btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUsers(Group group) {
        userAdapter = new ArrayAdapter<String>(GroupPageActivity.this,
                android.R.layout.simple_list_item_1,
                group.getUsers());
        lvUsers.setAdapter(userAdapter);
    }
}
