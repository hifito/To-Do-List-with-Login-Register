package com.example.todolistwithloginregister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistwithloginregister.model.Task;
import com.example.todolistwithloginregister.utils.DatabaseHandler;

public class NewTaskAct extends AppCompatActivity {
    TextView titlepage, addtitle, adddesc, adddate;
    EditText titledoes, descdoes, datedoes;
    Button btnCancel, btnAdd;
    SharedPreferences sharedPreferences;
    private String username;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Task task = new Task();

        btnAdd = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);
        titledoes = findViewById(R.id.titledoes);
        datedoes = findViewById(R.id.datedoes);
        descdoes = findViewById(R.id.descdoes);

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
        databaseHandler.openDatabase();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(NewTaskAct.this, MainActivity.class);
                onDestroy();
                startActivity(a);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setTitledoes(titledoes.getText().toString());
                task.setDatedoes(datedoes.getText().toString());
                task.setDescdoes(descdoes.getText().toString());
                databaseHandler.insertTask(task);
                Intent intent = new Intent(NewTaskAct.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}