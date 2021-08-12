package com.example.donasimasjid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donasimasjid.database.DatabaseHelper;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    ArrayList<ModelMasjid> dataMasjid = null;
    Context context;

    public Adapter(Context ct, ArrayList<ModelMasjid> data) {
        context = ct;
        dataMasjid = data;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama,nohp,donasi;
        AppCompatButton btndonasi,btn_delete,btn_edit;
        ProgressBar progress;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama_masjid);
            nohp = itemView.findViewById(R.id.nohp);
            progress = itemView.findViewById(R.id.progress);
            donasi = itemView.findViewById(R.id.per);
            btndonasi = itemView.findViewById(R.id.donasi);
            btn_delete = itemView.findViewById(R.id.delete);
            btn_edit = itemView.findViewById(R.id.edit);
        }
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(context);
       View view =  inflater.inflate(R.layout.card_masjid,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String role = preferences.getString("Role", "");
        holder.nama.setText(dataMasjid.get(position).getNama());
        holder.nohp.setText("No HP : "+dataMasjid.get(position).getNohp());
        int persen = dataMasjid.get(position).getTotalDonasi() * 100 / Integer.valueOf(dataMasjid.get(position).getDonasi());
        holder.progress.setProgress(persen);
        holder.donasi.setText(dataMasjid.get(position).getTotalDonasi() + "/"+dataMasjid.get(position).getDonasi());
        int ind = position;
        holder.btndonasi.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Donasi");
            final EditText input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_TEXT );
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseHelper db;
                    db = new DatabaseHelper(context);
                    long res = db.donasi(dataMasjid.get(ind).getId(),"test",input.getText().toString(),"donasi");
                    if(res == 1){
                        Toast.makeText(context, "Berhasil Menyimpan data",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Gagal Menyimpan data",
                                Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        });

        if (!role.equals("admin")){
            holder.btn_delete.setVisibility(View.GONE);
            holder.btn_edit.setVisibility(View.GONE);
        }



        holder.btn_delete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Data");


            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        DatabaseHelper db;
                        db = new DatabaseHelper(context);
                        SQLiteDatabase db1 = db.getWritableDatabase();
                        db1.execSQL("delete from masjid where id = '"+dataMasjid.get(ind).getId()+"'");

                        db.close();
                        Toast.makeText(context, "Berhasil Menghapus data",
                                Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(context, "Opps ada yang salah",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        });

        holder.btn_edit.setOnClickListener( v -> {
            Intent intent = new Intent(context, form_masjid.class);
            intent.putExtra("nama", dataMasjid.get(position).getNama());
            intent.putExtra("hp", dataMasjid.get(position).getNohp());
            intent.putExtra("id", dataMasjid.get(position).getId());
            intent.putExtra("longg", dataMasjid.get(position).getLongg());
            intent.putExtra("lat", dataMasjid.get(position).getLatt());
            intent.putExtra("donasi", dataMasjid.get(position).getDonasi());
            intent.putExtra("alamat", dataMasjid.get(position).getAlamat());
            intent.putExtra("status", "edit");
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return dataMasjid == null ? 0 : dataMasjid.size();
    }
}
