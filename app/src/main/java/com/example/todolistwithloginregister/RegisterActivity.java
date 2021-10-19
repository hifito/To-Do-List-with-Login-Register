package com.example.todolistwithloginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistwithloginregister.utils.DatabaseHandler;

public class RegisterActivity extends AppCompatActivity {
    private Button reg;
    private TextView tvLogin;
    private EditText etEmail, etPass;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHandler(this);
        reg = (Button) findViewById(R.id.btnReg);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
    }

    private void register() {
        String username = etEmail.getText().toString();
        String password = etPass.getText().toString();

        db = new DatabaseHandler(RegisterActivity.this);
        SQLiteDatabase sqlDB = db.getWritableDatabase();

        Cursor cursor = sqlDB.rawQuery("SELECT  * FROM users", null);

        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(1).equals(username)) {
                    Toast.makeText(this, "User Already Exist, Please Login",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            } while (cursor.moveToNext());
        }
        if (username.isEmpty() && password.isEmpty()) {
            displayToast("Username/password field empty");
        } else {
            db.addUser(username, password);
            displayToast("User registered");
            finish();
        }
    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void register(View view) {
        switch (view.getId()) {
            case R.id.btnReg:
                register();
                break;
            case R.id.tvLogin:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
            default:
        }
    }
}