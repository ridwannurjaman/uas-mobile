package com.example.donasimasjid.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donasimasjid.Adapter;
import com.example.donasimasjid.ModelMasjid;
import com.example.donasimasjid.database.DatabaseHelper;
import com.example.donasimasjid.databinding.FragmentDashboardBinding;
import com.example.donasimasjid.form_masjid;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    FloatingActionButton fba;
    DatabaseHelper db;
    protected Cursor cursor,cursor2;
    ArrayList<ModelMasjid> model = new ArrayList<>();
    ArrayList<ModelMasjid> model2 = new ArrayList<>();
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fba = binding.fab;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(root.getContext());
        String role = preferences.getString("Role", "");

        if (!role.equals("admin"))
            fba.setVisibility(View.GONE);

        recyclerView = binding.rec;
        db = new DatabaseHelper(root.getContext());
        SQLiteDatabase db1 = db.getReadableDatabase();
        cursor = db1.rawQuery("SELECT * FROM masjid ",null);
        while (cursor.moveToNext()){
            int total_donasi = 0;
            cursor2 = db1.rawQuery("SELECT * FROM detail_masjid WHERE id_masjid = '" +
                    cursor.getString(0).toString()+ "'",null);
            while (cursor2.moveToNext()){
                total_donasi += Integer.valueOf(cursor2.getString(3));
            }
            model.add( new ModelMasjid(
                    cursor.getString(1).toString() != null  ? cursor.getString(1).toString() :"-",
                    cursor.getString(2).toString() != null  ? cursor.getString(2).toString() :"-",
                    cursor.getString(4).toString() != null  ? cursor.getString(4).toString() :"-",
                    cursor.getString(3).toString() != null  ? cursor.getString(3).toString() :"-",
                    cursor.getString(5) != null  ? cursor.getString(5) :"-",
                    cursor.getString(6) != null  ? cursor.getString(6) :"-",
                    cursor.getString(0).toString() != null  ? cursor.getString(0).toString() :"-",
                    total_donasi));
        }
        model2.addAll(model);

        Adapter adapter = new Adapter(root.getContext(),model2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        fba.setOnClickListener( v -> {
            Intent i = new Intent(root.getContext(), form_masjid.class);
            i.putExtra("status", "save");
            startActivity(i);
        });
//        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}