package com.example.donasimasjid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.donasimasjid.database.DatabaseHelper;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class form_masjid extends AppCompatActivity {
    AppCompatButton btn_save,btn_picker;
    AppCompatEditText txt_lat,txt_long,txt_nama,txt_nohp,txt_donasi,txt_alamat;
    double lat,longg;
    int PLACE_PICKET = 1;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_masjid);
        db = new DatabaseHelper(this);
        btn_picker = findViewById(R.id.btn_picker);
        btn_save = findViewById(R.id.btn_save);
        txt_donasi = findViewById(R.id.txt_donasi);
        txt_nama = findViewById(R.id.txt_nama_masjid);
        txt_lat = findViewById(R.id.txt_lat);
        txt_long = findViewById(R.id.txt_long);
        txt_nohp = findViewById(R.id.txt_nohp);
        txt_alamat = findViewById(R.id.txt_alamat);


        if(getIntent() != null){
            txt_nama.setText(getIntent().getStringExtra("nama"));
            txt_donasi.setText(getIntent().getStringExtra("donasi"));
            txt_lat.setText(getIntent().getStringExtra("lat"));
            txt_long.setText(getIntent().getStringExtra("longg"));
            txt_nohp.setText(getIntent().getStringExtra("hp"));
            txt_alamat.setText(getIntent().getStringExtra("alamat"));
        }

        btn_picker.setOnClickListener(v -> {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(form_masjid.this),PLACE_PICKET);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        });

        btn_save.setOnClickListener(v -> {
            if(getIntent().getStringExtra("status").equals("edit")){
                SQLiteDatabase db1 = db.getWritableDatabase();
                db1.execSQL("update masjid set nama_masjid='"+
                        txt_nama.getText().toString() +"', alamat='" +
                        txt_alamat.getText().toString()+"', donasi='"+
                        txt_donasi.getText().toString() +"', no_hp='" +
                        txt_nohp.getText().toString() +"', lattitude='" +
                        txt_lat.getText().toString() +"', longitude='" +
                        txt_long.getText().toString() + "' where id='" +
                        getIntent().getStringExtra("id")+"'");
                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
            }else{
                if(txt_donasi.getText() == null || txt_nama.getText() == null || txt_lat.getText() == null || txt_long.getText() == null || txt_nohp.getText() == null || txt_alamat.getText() == null){
                    Toast.makeText(getApplicationContext(), "Tidak Boleh Kosong",
                            Toast.LENGTH_SHORT).show();
                }else{
                    long res = db.tambahMasjid(txt_nama.getText().toString(),txt_alamat.getText().toString(),txt_donasi.getText().toString(),txt_nohp.getText().toString(),lat,longg);
                    if(res >= 1){
                        Toast.makeText(getApplicationContext(), "Berhasil Menyimpan data",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Gagal Menyimpan data",
                                Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKET){
            if(resultCode == RESULT_OK){
                Place place= PlacePicker.getPlace(data,this);
                longg = place.getLatLng().longitude;
                lat = place.getLatLng().latitude;
                txt_lat.setText(String.valueOf(lat));
                txt_long.setText(String.valueOf(longg));
            }
        }
    }
}