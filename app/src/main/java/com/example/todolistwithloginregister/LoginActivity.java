package com.example.todolistwithloginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistwithloginregister.utils.DatabaseHandler;
import com.example.todolistwithloginregister.utils.PreferenceHandler;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private TextView register;
    private EditText etEmail, etPass;
    private DatabaseHandler db;
    private PreferenceHandler preferenceHandler;
    private String username;
    private SharedPreferences sharedPreferences;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHandler(this);
        preferenceHandler = new PreferenceHandler(this);
        login = (Button)findViewById(R.id.btnLogin);
        register = (TextView)findViewById(R.id.btnReg);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPass = (EditText)findViewById(R.id.etPass);
        sharedPreferences = getSharedPreferences("myapp", Context.MODE_PRIVATE);
        sharedPreferences.contains("username");

        if(preferenceHandler.loggedin()){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }

    private void login(){
        username = etEmail.getText().toString();
        password = etPass.getText().toString();

//        db = new DatabaseHandler(LoginActivity.this);
//        SQLiteDatabase sqlDB = db.getWritableDatabase();
//
//        Cursor cursor = sqlDB.rawQuery("SELECT  * FROM users", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                if(cursor.getString(1).equals(username) && cursor.getString(2).equals(password)){
//                    Toast.makeText(this, "Login Successfull",
//                            Toast.LENGTH_SHORT).show();
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("username", username);
//                    editor.apply();
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
//                    return;
//                }
//            } while (cursor.moveToNext());
//        }
//        Toast.makeText(this, "Username / Password invalid", Toast.LENGTH_SHORT).show();

        if(db.getUser(username,password)){
            preferenceHandler.setLoggedin(true);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Wrong email/password",Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View view) {
        login();
    }

    public void register(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
}