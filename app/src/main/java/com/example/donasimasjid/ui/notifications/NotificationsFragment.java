package com.example.donasimasjid.ui.notifications;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.donasimasjid.R;
import com.example.donasimasjid.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView txt_nama = binding.nama;
        final TextView txt_email = binding.email;
        final TextView txt_nohp = binding.hp;
        final TextView txt_role = binding.role;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(root.getContext());
        String name = preferences.getString("Name", "");
        String email = preferences.getString("email", "");
        String hp = preferences.getString("No Hp", "");
        String role = preferences.getString("Role", "");
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                txt_nama.setText("Nama   :"+name);
                txt_email.setText("Email :"+email);
                txt_nohp.setText("No HP  :"+hp);
                txt_role.setText("Role   :"+role);
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