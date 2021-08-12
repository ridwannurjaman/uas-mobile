package com.example.donasimasjid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.donasimasjid.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    AppCompatButton btn_login,btn_register;
    AppCompatEditText txt_email,txt_password;
    DatabaseHelper db;
    protected Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        db = new DatabaseHelper(this);
        db.tambahUser("admin@test.com","asdqwe123","123456789","admin","Admin1");
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);


        btn_login.setOnClickListener(
                v -> {
                    String email = txt_email.getText().toString();
                    String password = txt_password.getText().toString();
                    Boolean res = db.periksaUser(email,password);
                    if(res){
                        SQLiteDatabase db1 = db.getReadableDatabase();
                        cursor = db1.rawQuery("SELECT * FROM user WHERE email = '" +
                                txt_email.getText().toString()+ "' AND password='"+ txt_password.getText().toString() +"'",null);
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                        SharedPreferences.Editor editor = preferences.edit();
                        while (cursor.moveToNext()){
                            editor.putString("email",cursor.getString(1));
                            editor.putString("Name",cursor.getString(5));
                            editor.putString("No Hp",cursor.getString(3));
                            editor.putString("Role",cursor.getString(4));
                            editor.apply();
                        }


                        Toast.makeText(getApplicationContext(), "Selamat Datang",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),MenuUtama.class);
                        startActivity(i);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Email atau Password Anda salah!")
                                .setNegativeButton("Retry", null).create().show();
                    }
                }
        );

        btn_register.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),register.class);
            startActivity(i);
        });


    }
}