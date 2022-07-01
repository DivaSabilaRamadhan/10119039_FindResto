package com.example.a10119039_findresto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
// Nama   : Diva Sabila Ramadhan
// NIM    : 10119039
// Kelas  : IF-1

public class MapsFragment extends Fragment {
    private FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng DailyMilkPermataBiru = new LatLng(-6.942242046499123, 107.73022837523908);
            googleMap.addMarker(new MarkerOptions().position(DailyMilkPermataBiru).title("Daily Milk Permata Biru"));

            LatLng BasoMangIwa = new LatLng(-6.9252852310451445, 107.71063445433688);
            googleMap.addMarker(new MarkerOptions().position(BasoMangIwa).title("Baso Mang Iwa"));

            LatLng MieBasoLondoSolo = new LatLng(-6.942888694735565, 107.72579864664159);
            googleMap.addMarker(new MarkerOptions().position(MieBasoLondoSolo).title("Mie Baso Londo Solo"));

            LatLng PerspectiveCafe = new LatLng(-6.938579038038714, 107.72593296782772);
            googleMap.addMarker(new MarkerOptions().position(PerspectiveCafe).title("PERSPECTIVE Cafe"));

            LatLng MartabakPermata = new LatLng(-6.9416382417630595, 107.72585575433706);
            googleMap.addMarker(new MarkerOptions().position(MartabakPermata).title("Martabak Permata"));

            LatLng MartabakMasAmiPopuler = new LatLng(-6.933140539260515, 107.72322961200989);
            googleMap.addMarker(new MarkerOptions().position(MartabakMasAmiPopuler).title("Martabak Mas Ami Pupuler"));

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            client = LocationServices.getFusedLocationProviderClient(getActivity());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(DailyMilkPermataBiru, 17.0F));
            mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
            getCurrentLocation();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void getCurrentLocation() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            Log.e("Last Location: ", location.toString());
                            LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(currentLoc).title("Lokasi Anda saat ini"));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));
                        }
                    });

                }
            }
        });
}
}