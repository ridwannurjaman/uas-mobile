package com.example.donasimasjid.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.donasimasjid.R;
import com.example.donasimasjid.database.DatabaseHelper;
import com.example.donasimasjid.databinding.FragmentHomeBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    MapView mapView;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    View root;
    GoogleMap googleMap;
    DatabaseHelper dbHelper;
    protected Cursor cursor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        mapView = binding.mapview;
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                if (ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                MarkerOptions mp = new MarkerOptions();
                googleMap.setMyLocationEnabled(true);
                googleMap.setOnMyLocationChangeListener(location -> {
                    CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
                    googleMap.clear();

                    mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
                    mp.title("my position");
                    googleMap.addMarker(mp);

                    googleMap.moveCamera(center);
                    googleMap.animateCamera(zoom);

                    dbHelper = new DatabaseHelper(root.getContext());
                    SQLiteDatabase db1 = dbHelper.getReadableDatabase();
                    cursor = db1.rawQuery("SELECT * FROM masjid ",null);
                    while (cursor.moveToNext()) {
                        mp.position(new LatLng(Double.valueOf(cursor.getString(5)), Double.valueOf(cursor.getString(6))));
                        mp.title("Lokasi Masjid");
                        googleMap.addMarker(mp);
                    }
                });





            }
        });

        mapView.onResume();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
//        if (ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        googleMap.setMyLocationEnabled(true);
//        googleMap.setOnMyLocationChangeListener(location -> {
//            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
//            CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
//            googleMap.clear();
//            MarkerOptions mp = new MarkerOptions();
//            mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
//            mp.title("my position");
//            googleMap.addMarker(mp);
//            googleMap.moveCamera(center);
//            googleMap.animateCamera(zoom);
//        });
    }
}