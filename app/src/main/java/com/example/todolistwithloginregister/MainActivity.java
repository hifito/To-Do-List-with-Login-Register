package com.example.todolistwithloginregister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistwithloginregister.adapter.ListTaskAdapter;
import com.example.todolistwithloginregister.model.Task;
import com.example.todolistwithloginregister.utils.DatabaseHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView titlepage, subtitlepage, endpage;
    Button btnAddNew, btnLogout;
    private SharedPreferences sharedPreferences;
    String username;
    private Session session;
    private RecyclerView rvTask;
    private ArrayList<Task> list = new ArrayList<>();
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);
        endpage = findViewById(R.id.endpage);

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        btnAddNew = findViewById(R.id.btnAddNew);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, com.example.todolistwithloginregister.NewTaskAct.class);
                onDestroy();
                startActivity(a);
            }
        });

        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }

        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        rvTask = findViewById(R.id.rc_task);
        rvTask.setHasFixedSize(true);
        databaseHandler = new DatabaseHandler(getApplicationContext());
        databaseHandler.openDatabase();
        list.addAll(databaseHandler.getAllTasks());
        showRecyclerList();

        if (getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("My To Do");
        }
    }

    private void logout(){
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

    private void showRecyclerList() {
        rvTask.setLayoutManager(new LinearLayoutManager(this));
        ListTaskAdapter listTaskAdapter = new ListTaskAdapter(list, databaseHandler);
        rvTask.setAdapter(listTaskAdapter);
    }
}