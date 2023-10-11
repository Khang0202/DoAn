package com.doannganh.warningmap.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.doannganh.warningmap.Object.API;
import com.doannganh.warningmap.Object.District;
import com.doannganh.warningmap.Object.Province;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.Object.Warning;
import com.doannganh.warningmap.R;
import com.doannganh.warningmap.Repository.AddressRepository;
import com.doannganh.warningmap.Repository.MapRepository;
import com.doannganh.warningmap.Repository.WarningRepository;
import com.doannganh.warningmap.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int REQUEST_CODE = 101;
    protected GoogleMap gMap;
    private SupportMapFragment mapFragment;
    private ActivityMainBinding binding;
    private ImageView imvCurrentLoc, imvReport;
    private FusedLocationProviderClient fusedLocationClient;
    private SearchView mapSearch;
    private Marker searchMarker, currentMarker, mapMarker;
    private TextView txtMarkerDialogPlaces, txtMarkerDialogLatitude, txtMarkerDialogLongitude;
    private Button btnMarkerDialogDirection, btnMarkerDialogClear, btnMarkerDialogSave, btnMarkerDialogAddImage;
    private Location currentLocation;
    private Polyline curentPolyline;
    com.doannganh.warningmap.Object.Address placeInfoToAddReport = new com.doannganh.warningmap.Object.Address();
    MapRepository mapRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navBar.setItemSelected(R.id.nav_map, true);
        setUpTabBar();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initView();
        new AddressRepository().getAllProvince(MainActivity.this);
        new AddressRepository().getAllDistrict(MainActivity.this);
        imvCurrentLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });
        mapSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String searchText = mapSearch.getQuery().toString();
                if (searchText == null || searchText.isEmpty())
                {
                    Log.d("TAG", searchText);
                    Toast.makeText(MainActivity.this, "Location Not Found", Toast.LENGTH_SHORT).show();}
                else {
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocationName(searchText, 1);
                        if (addressList.size() > 0){
                            LatLng latLngSearch = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
                            if (searchMarker != null) searchMarker.remove();
                            MarkerOptions markerOptions = new MarkerOptions().position(latLngSearch).title(searchText);
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngSearch, 5);
                            gMap.animateCamera(cameraUpdate);
                            searchMarker = gMap.addMarker(markerOptions);
                        }
                    } catch (IOException e) {
                        Log.d("TAG", e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        imvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPlaceInfo(MainActivity.this, currentMarker.getPosition());
            }
        });







        //không được thực thi trước khác xử lý khác
        initViewMap();
    }

    private void initView() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        imvCurrentLoc = findViewById(R.id.imvCurrentLoc);
        imvReport = findViewById(R.id.imvReport);
        mapSearch = findViewById(R.id.mapSearch);

        btnMarkerDialogClear = findViewById(R.id.btnMarkerDialogClear);
        btnMarkerDialogDirection = findViewById(R.id.btnMarkerDialogDirection);
        txtMarkerDialogPlaces = findViewById(R.id.txtMarkerDialogPlaces);
        txtMarkerDialogLatitude = findViewById(R.id.txtMarkerDialogLatitude);
        txtMarkerDialogLongitude = findViewById(R.id.txtMarkerDialogLongitude);

    }
    private void initViewMap(){
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
                    currentLocation = location;
                    if (location != null) {
                        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                        currentMarker = gMap.addMarker(new MarkerOptions().position(current).title("Current location").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_loc_png)));
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
                                currentMarker = gMap.addMarker(new MarkerOptions().position(current).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current_loc_png)));
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
                    if (StaticClass.userToken == null) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        //bắt đầu setting activity
                        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent);
                    }
                } else if (i == R.id.nav_notice) {
                    if (StaticClass.userToken == null) {
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
    private void openReportDialog(Marker marker){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View markerDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_add_report, null);
        builder.setView(markerDialog);
        btnMarkerDialogSave = markerDialog.findViewById(R.id.btnMarkerDialogSave);
        btnMarkerDialogAddImage = markerDialog.findViewById(R.id.btnMarkerDialogAddImage);
        txtMarkerDialogPlaces = markerDialog.findViewById(R.id.txtMarkerDialogPlaces);
        txtMarkerDialogLatitude =  markerDialog.findViewById(R.id.txtMarkerDialogLatitude);
        txtMarkerDialogLongitude =  markerDialog.findViewById(R.id.txtMarkerDialogLongitude);


        txtMarkerDialogPlaces.setText(marker.getTitle());
        txtMarkerDialogLatitude.setText(String.valueOf(marker.getPosition().latitude));
        txtMarkerDialogLongitude.setText(String.valueOf(marker.getPosition().longitude));
        AlertDialog alertDialog = builder.create();

        btnMarkerDialogSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticClass.userToken == null) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    new WarningRepository().addWarning(MainActivity.this, placeInfoToAddReport, currentMarker.getPosition());
                }
            }
        });
        btnMarkerDialogAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, CaptureActivity.class);
                startActivity(intent);
            }
        });



        alertDialog.show();
    }
    private void openMarkerDialog(Marker marker){
        Log.d("NOTE", "call openMarkerDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View markerDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_map_marker, null);
        builder.setView(markerDialog);
        btnMarkerDialogClear = markerDialog.findViewById(R.id.btnMarkerDialogClear);
        btnMarkerDialogDirection = markerDialog.findViewById(R.id.btnMarkerDialogDirection);
        txtMarkerDialogPlaces = markerDialog.findViewById(R.id.txtMarkerDialogPlaces);
        txtMarkerDialogLatitude =  markerDialog.findViewById(R.id.txtMarkerDialogLatitude);
        txtMarkerDialogLongitude =  markerDialog.findViewById(R.id.txtMarkerDialogLongitude);

        txtMarkerDialogPlaces.setText(marker.getTitle().toString());
        txtMarkerDialogLatitude.setText(String.valueOf(marker.getPosition().latitude));
        txtMarkerDialogLongitude.setText(String.valueOf(marker.getPosition().longitude));
        AlertDialog alertDialog = builder.create();
        btnMarkerDialogDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (currentLocation != null){
                    LatLng origin = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    mapRepository.direction(MainActivity.this, origin, marker.getPosition());
//                    direction(origin, marker.getPosition());
                }else
                    Toast.makeText(getApplicationContext(), "chưa làm",Toast.LENGTH_SHORT).show();
            }
        });
        btnMarkerDialogClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marker.remove();
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
    }
    public void getPlaceInfo(Context context, LatLng latLng){
        String latlng = latLng.latitude+","+ latLng.longitude;
        String url = Uri.parse("https://maps.googleapis.com/maps/api/geocode/json")
                .buildUpon()
                .appendQueryParameter("latlng", latlng)
                .appendQueryParameter("key", "AIzaSyD8Hy1UM4itV7K9hjz1M8CQLmchn7LMEP4")
                .toString();
        Log.d("NOTE", url);
        com.doannganh.warningmap.Object.Address address = new com.doannganh.warningmap.Object.Address();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (status.equals("OK")){
                        JSONArray results = response.getJSONArray("results");
                        for (int i=0;i<results.length();i++){
                            JSONArray address_components = results.getJSONObject(i).getJSONArray("address_components");
                            for (int j=0;j<address_components.length();j++){
                                JSONArray types = address_components.getJSONObject(j).getJSONArray("types");
                                String longName = address_components.getJSONObject(j).getString("long_name");
                                for (int k = 0; k < types.length(); k++) {
                                    String type = types.get(k).toString();
                                    if (type.equals("street_number")) {
                                        address.setStreetNumber(longName);
                                    }else if (type.equals("route")){
                                        address.setRoute(longName);
                                    }else if (type.equals("administrative_area_level_3")){
                                        address.setTown(longName);
                                    }else if (type.equals("administrative_area_level_2")){
                                        address.setDistrict(new District(longName));
                                    }else if (type.equals("administrative_area_level_1")){
                                        address.setProvince(new Province(longName));
                                    }
                                }
                            }
                        }
                        placeInfoToAddReport = address;
                        currentMarker.setTitle(address.getStreetNumber()+", "+address.getRoute()+", "+address.getTown()+", "+address.getDistrict().getDistrict()+", "+address.getProvince().getProvince());
                        openReportDialog(currentMarker);
                        Log.d("NOTE",address.getStreetNumber()+address.getRoute()+address.getTown()+address.getDistrict().getDistrict()+address.getProvince().getProvince());
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("NOTE",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
    public void getMarkerTitle(Context context, LatLng latLng){
        String latlng = latLng.latitude+","+ latLng.longitude;
        String url = Uri.parse("https://maps.googleapis.com/maps/api/geocode/json")
                .buildUpon()
                .appendQueryParameter("latlng", latlng)
                .appendQueryParameter("key", "AIzaSyD8Hy1UM4itV7K9hjz1M8CQLmchn7LMEP4")
                .toString();
        Log.d("NOTE", url);
        com.doannganh.warningmap.Object.Address address = new com.doannganh.warningmap.Object.Address();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (status.equals("OK")){
                        JSONArray results = response.getJSONArray("results");
                        for (int i=0;i<results.length();i++){
                            JSONArray address_components = results.getJSONObject(i).getJSONArray("address_components");
                            for (int j=0;j<address_components.length();j++){
                                JSONArray types = address_components.getJSONObject(j).getJSONArray("types");
                                String longName = address_components.getJSONObject(j).getString("long_name");
                                for (int k = 0; k < types.length(); k++) {
                                    String type = types.get(k).toString();
                                    if (type.equals("street_number")) {
                                        address.setStreetNumber(longName);
                                    }else if (type.equals("route")){
                                        address.setRoute(longName);
                                    }else if (type.equals("administrative_area_level_3")){
                                        address.setTown(longName);
                                    }else if (type.equals("administrative_area_level_2")){
                                        address.setDistrict(new District(longName));
                                    }else if (type.equals("administrative_area_level_1")){
                                        address.setProvince(new Province(longName));
                                    }
                                }
                            }
                        }
                        mapMarker.setTitle(address.getStreetNumber()+", "+address.getRoute()+", "+address.getTown()+", "+address.getDistrict().getDistrict()+", "+address.getProvince().getProvince());
                        Log.d("NOTE",address.getStreetNumber()+address.getRoute()+address.getTown()+address.getDistrict().getDistrict()+address.getProvince().getProvince());
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("NOTE",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
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
        mapRepository = new MapRepository(gMap, curentPolyline);

        //default loc map map
        LatLng current = new LatLng(16.664374304057134,106.4648463204503);
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
                mapMarker = gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker()));
                getMarkerTitle(MainActivity.this, mapMarker.getPosition());
                markers.add(mapMarker);
            }
        });
        //xử lý khi chọn marker
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                openMarkerDialog(marker);
                String title = marker.getTitle();
                Toast.makeText(getApplicationContext(), "Chọn địa điểm: " + title, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
