package com.example.donasimasjid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.donasimasjid.database.DatabaseHelper;

public class register extends AppCompatActivity {
    AppCompatEditText txt_email,txt_password,txt_nama,txt_nohp;
    AppCompatButton btn_register;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseHelper(this);
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        txt_nama = findViewById(R.id.txt_nama);
        txt_nohp = findViewById(R.id.txt_nohp);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(v -> {
           boolean res = db.tambahUser(txt_email.getText().toString(),txt_password.getText().toString(),txt_nohp.getText().toString(),"user",txt_nama.getText().toString());
            if(res){
                Toast.makeText(getApplicationContext(), "Register Berhasil",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                builder.setMessage("Opps ada yang salah!")
                        .setNegativeButton("Retry", null).create().show();
            }
        });
    }
}