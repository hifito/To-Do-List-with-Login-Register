package com.example.todolistwithloginregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistwithloginregister.utils.DatabaseHandler;

public class UpdateTaskAct extends AppCompatActivity {
    TextView titlepage, addtitle, adddesc, adddate;
    EditText titledoes, descdoes, datedoes;
    Button btnDelete, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
        databaseHandler.openDatabase();

        Bundle data = getIntent().getExtras();
        if(data != null) {
            String title = data.getString("title");
            String desc = data.getString("desc");
            String date = data.getString("date");
            titledoes = findViewById(R.id.titledoes);
            descdoes = findViewById(R.id.descdoes);
            datedoes = findViewById(R.id.datedoes);

            titledoes.setText(title);
            descdoes.setText(desc);
            datedoes.setText(date);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.updateTask(data.getInt("id"), titledoes.getText().toString(), datedoes.getText().toString(), descdoes.getText().toString());
                Intent intent = new Intent(UpdateTaskAct.this, com.example.todolistwithloginregister.MainActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.deleteTask(data.getInt("id"));
                Intent intent = new Intent(UpdateTaskAct.this, com.example.todolistwithloginregister.MainActivity.class);
                startActivity(intent);
            }
        });
    }
}