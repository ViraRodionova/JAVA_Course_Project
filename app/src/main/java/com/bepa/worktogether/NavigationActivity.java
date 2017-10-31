package com.bepa.worktogether;

import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.bepa.worktogether.adapter.TaskAdapter;
import com.bepa.worktogether.model.MockedData;
import com.bepa.worktogether.model.Task;

import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TaskAdapter adapter;
    ArrayAdapter<String> groupAdapter;
    ListView lvMain;
    final static MockedData data = new MockedData();
    int selectedGroupIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lvMain = (ListView) findViewById(R.id.lvMain);

        final ArrayList<String> names = new ArrayList<String>();

        names.add("My Tasks");
        for (int i = 0; i < data.user.getGroups().size(); i++) {
            names.add(data.user.getGroups().get(i).getName());
        }

        selectedGroupIndex = 0;

        setListItems();


        groupAdapter = new ArrayAdapter<String>(NavigationActivity.this,
                android.R.layout.simple_list_item_1, names);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setListItems() {
        final ArrayList<Task> tasks;
        if (selectedGroupIndex == 0) {
            tasks = data.user.getTasks();
        } else {
            tasks = data.user.getGroups().get(selectedGroupIndex - 1).getTasks();
        }

        adapter = new TaskAdapter(NavigationActivity.this,
                android.R.layout.activity_list_item,
                tasks);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task object;

                if (selectedGroupIndex == 0) {
                    object = data.user.getTasks().get(position);
                } else {
                    object = data.user.getGroups().get(selectedGroupIndex - 1).getTasks().get(position);
                }

                if (object.getStatus() < 2) object.setStatus(object.getStatus() + 1);

                final ArrayList<Task> tasks1;

                if (selectedGroupIndex == 0) {
                    tasks1 = data.user.getTasks();
                } else {
                    tasks1 = data.user.getGroups().get(selectedGroupIndex - 1).getTasks();
                }

                adapter = new TaskAdapter(NavigationActivity.this,
                        android.R.layout.activity_list_item,
                        tasks1);

                lvMain.setAdapter(adapter);
            }
        });
    }
}
