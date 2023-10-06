package com.doannganh.warningmap.Repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.doannganh.warningmap.Activity.MainActivity;
import com.doannganh.warningmap.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapRepository {
    private GoogleMap googleMap;
    private Polyline curentPolyline;

    public MapRepository(GoogleMap googleMap, Polyline curentPolyline) {
        this.googleMap = googleMap;
        this.curentPolyline = curentPolyline;
    }
    public List<LatLng> decodePoly(String encoded){
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len - 1) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b > 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
    public void direction(Context context, LatLng origin, LatLng destination){
        String sdestination = destination.latitude + ", " + destination.longitude;
        String sorigin = origin.latitude+ ", " + origin.longitude;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = Uri.parse("https://maps.googleapis.com/maps/api/directions/json")
                .buildUpon()
                .appendQueryParameter("destination", sdestination)
                .appendQueryParameter("origin", sorigin)
                .appendQueryParameter("mode", "driving")
                .appendQueryParameter("key", "AIzaSyD8Hy1UM4itV7K9hjz1M8CQLmchn7LMEP4")
                .toString();
        Log.d("NOTE", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (status.equals("OK")) {
                        Log.d("NOTE", response.toString());
                        JSONArray routes = response.getJSONArray("routes");

                        ArrayList<LatLng> points = null;
                        PolylineOptions polylineOptions = null;

                        for (int i=0;i<routes.length();i++){
                            points = new ArrayList<>();
                            polylineOptions = new PolylineOptions();

                            JSONArray legs = routes.getJSONObject(i).getJSONArray("legs");
                            Log.d("NOTE", legs.toString());
                            for (int j=0;j<legs.length();j++){
                                JSONArray steps = legs.getJSONObject(j).getJSONArray("steps");
                                Log.d("NOTE", steps.toString());
                                for (int k=0;k<steps.length();k++){
                                    String polyline = steps.getJSONObject(k).getJSONObject("polyline").getString("points");
                                    List<LatLng> list = decodePoly(polyline);
                                    Log.d("NOTE", polyline.toString());
                                    for (int l=0;l<list.size();l++){
                                        LatLng position = new LatLng((list.get(l)).latitude, (list.get(l)).longitude);
                                        points.add(position);
                                    }
                                }
                            }
                            polylineOptions.addAll(points);
                            polylineOptions.width(16);
                            polylineOptions.color(ContextCompat.getColor(context, R.color.blue));
                            polylineOptions.geodesic(true);
                        }
                        Log.d("NOTE", "Number of points: " + points.size());
                        if (curentPolyline != null) {curentPolyline.remove(); curentPolyline=null;}
                        curentPolyline = googleMap.addPolyline(polylineOptions);
//                        googleMap.addPolyline(polylineOptions);
//                        LatLngBounds bounds = new LatLngBounds.Builder()
//                                .include(origin)
//                                .include(destination).build();
//                        Point point = new Point();
//                        getWindowManager().getDefaultDisplay().getSize(point);
//                        gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, point.x, point.y, 30));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin,16));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RetryPolicy retryPolicy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(jsonObjectRequest);
    }
}
