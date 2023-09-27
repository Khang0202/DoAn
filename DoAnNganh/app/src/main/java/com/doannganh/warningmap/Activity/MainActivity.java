package com.doannganh.warningmap.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.R;
import com.doannganh.warningmap.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int REQUEST_CODE = 101;
    private GoogleMap gMap;
    private SupportMapFragment mapFragment;
    private ActivityMainBinding binding;
    private ImageView imvCurrentLoc, imvReport;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navBar.setItemSelected(R.id.nav_map, true);
        setUpTabBar();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        khoitao();
        imvCurrentLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });









        //không được thực thi trước khác xử lý khác
        khoiTaoMap();
    }

    private void khoitao() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        imvCurrentLoc = findViewById(R.id.imvCurrentLoc);
        imvReport = findViewById(R.id.imvReport);


    }
    private void khoiTaoMap(){
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(MainActivity.this);
    }

    private void setCurrentLocation() {
        Log.d("NOTE", "getCurrentLocationNow() called");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Log.d("NOTE", "Location service is enable");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("NOTE", "Permission not granted");
                return;
            }
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
            fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                        gMap.addMarker(new MarkerOptions().position(current).title("Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_loc_png)));
                        gMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 16));
                        Log.d("NOTE", String.valueOf(location.getLatitude()));
                        Log.d("NOTE", String.valueOf(location.getLongitude()));
                    } else {
                        Log.d("NOTE", "Failed get location");
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location = locationResult.getLastLocation();
                                LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                                gMap.addMarker(new MarkerOptions().position(current).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_loc_png)));
                                gMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 16));
                                Log.d("NOTE", String.valueOf(location.getLatitude()));
                                Log.d("NOTE", String.valueOf(location.getLongitude()));
                            }
                        };
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });
        } else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d("NOTE", "permission granted");
            setCurrentLocation();
            Log.d("NOTE", "setMapViewCalled");
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, REQUEST_CODE);
            Log.d("NOTE", "request permission");
        }
    }

    private void setUpTabBar() {
        binding.navBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if (i == R.id.nav_map) {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        }, REQUEST_CODE);
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                } else if (i == R.id.nav_setting) {
                    if (StaticClass.currentUser == null) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        //bắt đầu setting activity
                        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent);
                    }
                } else if (i == R.id.nav_notice) {
                    if (StaticClass.currentUser == null) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        //bắt đầu notice activity
                        Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults.length > 0 && (grantResults[0] + grantResults[1]) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d("NOTE", "call OnMapReady");
        gMap = googleMap;

        //default loc map map
        LatLng current = new LatLng(10.762622,106.660172);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 6));
        List<Marker> markers = new ArrayList<>();
        //tạo marker
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                for (Marker marker : markers) {
                    marker.remove();
                }
                markers.clear();
                Marker marker = gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Điểm chọn")
                        .icon(BitmapDescriptorFactory.defaultMarker()));
                markers.add(marker);
            }
        });
        //xử lý khi chọn marker
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String title = marker.getTitle();
                LatLng position = marker.getPosition();
                Toast.makeText(getApplicationContext(), "Chọn địa điểm: " + title, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }
}
