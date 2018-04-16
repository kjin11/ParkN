package com.example.kejin.iot_demo;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kejin.iot_demo.data_class.DataRecord;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kejin on 24/02/2018.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    GoogleMap mMap;
    List<DataRecord>CurrentAvailableLot;
    private LocationManager mLocationManager;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.amap_main, container, false);
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//        mMap.getUiSettings().setCompassEnabled(true);
//        mMap.getUiSettings().setAllGesturesEnabled(true);
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Button button = (Button) getActivity().findViewById(R.id.fake_button_map);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DetailFragment detailFragment = new DetailFragment();
//                getFragmentManager().beginTransaction().replace(R.id.content_container, detailFragment).commit();
//            }
//        });
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        if (mapFragment == null) {
//            FragmentManager fm = getFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            mapFragment = SupportMapFragment.newInstance();
//            ft.replace(R.id.content_container, mapFragment).commit();
//
//        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng cmuParking = new LatLng(40.443601, -79.941972);
        LatLng cmuHuntLibrary = new LatLng(40.441329, -79.943720);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {

                Log.d("mGoogleMap1", "Activity_Calling");

            }
        });
        mMap.addMarker(new MarkerOptions().position(cmuParking).title("This is CMU parking lot.").snippet("Indoor parking lot."));
        mMap.addMarker(new MarkerOptions().position(cmuHuntLibrary).title("This is CMU hunt library parking space.").snippet("Outdoor parking lot"));

        CameraPosition cmuParkingCamera = CameraPosition.builder().target(cmuParking).zoom(16).bearing(0).tilt(45).build();
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(cmuParking));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cmuParkingCamera));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
//                Toast.makeText(getActivity(), "hahahhaha", Toast.LENGTH_SHORT).show();// display toast
                Intent intent = new Intent (getActivity(), DetailActivity.class);
                startActivity(intent);

                return true;
            }
        });

    }
//
//    private void getCurrentLocation() {
//        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//        Location location = null;
//        if (!(isGPSEnabled || isNetworkEnabled))
//            Snackbar.make(mMapView, R.string.error_location_provider, Snackbar.LENGTH_INDEFINITE).show();
//        else {
//            if (isNetworkEnabled) {
//                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
//                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
//                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            }
//
//            if (isGPSEnabled) {
//                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
//                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            }
//        }
//        if (location != null) {
//            Logger.d(String.format("getCurrentLocation(%f, %f)", location.getLatitude(),
//                    location.getLongitude()));
//            drawMarker(location);
//        }
//    }
//
//    private void drawMarker(Location location) {
//        if (mMap != null) {
//            mMap.clear();
//            LatLng gps = new LatLng(location.getLatitude(), location.getLongitude());
//            mMap.addMarker(new MarkerOptions()
//                    .position(gps)
//                    .title("Current Position"));
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12));
//        }
//
//    }
    private List<DataRecord> getAvailableLot(){
        final List<DataRecord> currentAvailableLot = new ArrayList<DataRecord>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.e("firebase","1");
                String value = dataSnapshot.getValue(String.class);
                Log.e("firebase","2");
                DataSnapshot allAvailableLotSnapshot = dataSnapshot.child("Available_Lot");
                Log.e("firebase","3");
                Iterable<DataSnapshot> availableLotSnapshots = allAvailableLotSnapshot.getChildren();
                Log.e("firebase","4");
                for (DataSnapshot availableLotSnapshot : availableLotSnapshots) {
                    Log.e("firebase","5");
                    DataRecord c = availableLotSnapshot.getValue(DataRecord.class);
                    currentAvailableLot.add(c);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
        return currentAvailableLot;
    }

}

