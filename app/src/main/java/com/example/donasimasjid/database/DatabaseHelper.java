package com.example.donasimasjid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "donasiMasjid.db";
    public DatabaseHelper(Context context){
        super ( context,DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + "user" + "(" + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "email" + " TEXT, " + "password" + " TEXT,"+"no_hp TEXT,role TEXT,nama TEXT)";
        db.execSQL(query);

        String masjid = "CREATE TABLE " + "masjid" + "(" + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nama_masjid" + " TEXT, " + "alamat" + " TEXT,"+"donasi TEXT,no_hp TEXT,lattitude TEXT,longitude TEXT)";
        db.execSQL(masjid);

        String detail_masjid = "CREATE TABLE " + "detail_masjid" + "(" + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "id_masjid" + " TEXT, " + "nama_user" + " TEXT,"+"donasi TEXT,type_donasi TEXT)";
        db.execSQL(detail_masjid);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS user");
        db.execSQL(" DROP TABLE IF EXISTS masjid");
        db.execSQL(" DROP TABLE IF EXISTS detail_masjid");

        onCreate(db);
    }

    public boolean tambahUser(String user, String password,String no_hp,String role,String nama){
        SQLiteDatabase db = this.getReadableDatabase();
        boolean cek =periksaUser(user,password);
        boolean cekdata = false;
        if(cek){
            cekdata = false;
            System.out.println("Gagal tambah data");
        }else{
            ContentValues contentValues = new ContentValues();
            contentValues.put("email",user);
            contentValues.put("password",password);
            contentValues.put("no_hp",no_hp);
            contentValues.put("role",role);
            contentValues.put("nama",nama);
            long res = db.insert("user",null,contentValues);

            cekdata = true;
            System.out.println("Berhasil tambah data");
        }
        return cekdata;
    }

    public long tambahMasjid(String nama_masjid,String alamat,String donasi,String no_hp,double lattitude,double longitude){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama_masjid",nama_masjid);
        contentValues.put("alamat",alamat);
        contentValues.put("donasi",donasi);
        contentValues.put("no_hp",no_hp);
        contentValues.put("lattitude",lattitude);
        contentValues.put("longitude",longitude);
        long res = db.insert("masjid",null,contentValues);
        System.out.println(res);
        return res;
    }

    public long donasi(String id_masjid,String nama_user,String donasi,String type){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_masjid",id_masjid);
        contentValues.put("nama_user",nama_user);
        contentValues.put("donasi",donasi);
        contentValues.put("type_donasi",type);
        long res = db.insert("detail_masjid",null,contentValues);
        return res;
    }



    public boolean periksaUser(String username, String password){
        String[] columns = { "id" };
        SQLiteDatabase db = getReadableDatabase();
        String selection = "email" + "=?" + " AND " + "password" + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query("user",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
//        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }
}
