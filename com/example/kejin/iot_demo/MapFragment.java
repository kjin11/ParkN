package com.example.kejin.iot_demo;


import com.example.kejin.iot_demo.models.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * Created by kejin on 24/02/2018.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks
        ,GoogleApiClient.OnConnectionFailedListener
        ,LocationListener {
    SupportMapFragment mapFragment;
    GoogleMap mMap;
    private LocationManager mLocationManager;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private ChildEventListener mChildEventListener;
    Marker marker;
    LatLng cur = new LatLng(40.443852, -79.942905);
    Set<DataRecord> mCurrentAvailableLot = new HashSet<>();
    private HashMap<Marker, DataRecord> detailMarkerMap = new HashMap<>();
    private HashMap<DataRecord, Marker> recordToMarker = new HashMap<>();

    List<Address> mList = new ArrayList<>();  //search result from text

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private static final int PERMISSIONS_REQUEST_REQUEST_CODE = 7001;
    private static final int PLAY_SERVICE_REQUEST = 7002;
    private static final int UPDATE_INTERVAL = 5000;
    private static final int FASTEST_INTERVAL = 3000;
    private static final int DISPLACEMENT = 10;

    private static final String TAG = "MapFragment";
    private static final float DEFAULT_ZOOM = 15f;
    private String mSearchLocationTitle = "";

    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));
    private PlaceInfo mPlace;


    //widgets
    private AutoCompleteTextView mSearchText;
    private Button btn_search;

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
        requestPermission();
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Button button = (Button) getActivity().findViewById(R.id.fake_button_map);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DetailFragment detailFragment = new DetailFragment();
                String searchString = mSearchText.getText().toString();
                if (searchString != null || searchString.length() != 0) {
                    geoLocate();
                    Log.e(TAG, "btn_search geoLocate() worked!");
                }
//
//                getFragmentManager().beginTransaction().replace(R.id.content_container, detailFragment).commit();
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        if (mapFragment == null) {
//            FragmentManager fm = getFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            mapFragment = SupportMapFragment.newInstance();
//            ft.replace(R.id.content_container, mapFragment).commit();
//
//        }

        mapFragment.getMapAsync(this);
//
//        ChildEventListener mChildEventListener;

        mSearchText = (AutoCompleteTextView)getActivity().findViewById(R.id.input_search);
        btn_search = (Button) getActivity().findViewById(R.id.btn_search);
        setUpLocation();

    }

    private void setUpLocation() {

        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_REQUEST_CODE);

        }
        if (checkPlayServices()) {
            buildGoogleApiClient();
            createLocationRequest();
            displayLocation();
        }

    }

    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        init();
        mCurrentAvailableLot = getAvailableLot();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation != null) {
            final double latitude = mLocation.getLatitude();
            final double longitude = mLocation.getLongitude();

            //show current locwtion marker
            mMap.addMarker(new MarkerOptions().position(cur)
                    .title("Current Location").snippet("My current location.")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
//            moveCamera(cur, DEFAULT_ZOOM,"Current Location");
//            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
//                    .title("Current Location").snippet("My current location.")
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cur, 14.0f));
        }
    }

    @SuppressLint("RestrictedApi")
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);

    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this.getActivity(), this)
                .build();
        mGoogleApiClient.connect();

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this.getActivity(), PLAY_SERVICE_REQUEST).show();
            } else {
                Toast.makeText(this.getActivity(), "This device is not supported GoogleMap", Toast.LENGTH_SHORT).show();

            }
            return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//
//        LatLng cmuParking = new LatLng(40.443601, -79.941972);
//        LatLng cmuHuntLibrary = new LatLng(40.441329, -79.943720);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        LatLng cmuParking = new LatLng(40.443601, -79.941972);
//        LatLng cmuParking = new LatLng(37.422162, -122.081890);
//        LatLng cmuHuntLibrary = new LatLng(37.426873, -122.078854);
        LatLng cmuHuntLibrary = new LatLng(40.441329, -79.943720);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {

                Log.d("mGoogleMap", "Activity_Calling");

            }
        });

//        mMap.addMarker(new MarkerOptions().position(cmuParking).title("This is CMU parking lot.").snippet("Indoor parking lot."));
//        mMap.addMarker(new MarkerOptions().position(cmuHuntLibrary).title("This is CMU hunt library parking space.").snippet("Outdoor parking lot"));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
//                Toast.makeText(getActivity(), "hahahhaha", Toast.LENGTH_SHORT).show();// display toast
                if(!marker.getTitle().equals("Current Location")
                        && !marker.getSnippet().equals("Target Place")){

                    DataRecord detail = detailMarkerMap.get(marker);
//                    Intent detail_intent = new Intent(getActivity(), DetailActivity.class);
//                    detail_intent.putExtra("location", detail.getLocation());
//                    detail_intent.putExtra("amenity", detail.getAmenity());
//                    detail_intent.putExtra("distance", detail.getDistance());
//                    detail_intent.putExtra("end_time", detail.getEnd_time());
//                    detail_intent.putExtra("owner", detail.getOwner());
//                    detail_intent.putExtra("duration", detail.getDuration());
//                    detail_intent.putExtra("price", detail.getPrice());
//                    detail_intent.putExtra("start_time", detail.getStart_time());
//                    detail_intent.putExtra("total_money", detail.getTotoal_money());
//                    startActivity(detail_intent);
                    Intent checkout_intent = new Intent(getActivity(), CheckoutActivity.class);
                    checkout_intent.putExtra("location", detail.getLocation());
                    checkout_intent.putExtra("amenity", detail.getAmenity());
                    checkout_intent.putExtra("distance", detail.getDistance());
                    checkout_intent.putExtra("end_time", detail.getEnd_time());
                    checkout_intent.putExtra("owner", detail.getOwner());
                    checkout_intent.putExtra("duration", detail.getDuration());
                    checkout_intent.putExtra("price", detail.getPrice());
                    checkout_intent.putExtra("start_time", detail.getStart_time());
                    checkout_intent.putExtra("total_money", detail.getTotoal_money());
                    startActivity(checkout_intent);
                    Log.e(TAG,"sent to Checkout!" + detail.getLocation());
                    return true;
                }
                return false;
            }
        });


        //read data from Firebase and show on GoogleMap

        mCurrentAvailableLot = getAvailableLot();


    }


    private Set<DataRecord> getAvailableLot() {
        final Set<DataRecord> currentAvailableLot = new HashSet<>();
        ChildEventListener mChildEventListener;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.push().setValue(marker);
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                Object value = dataSnapshot.getValue(Object.class);
//                Log.e("firebase2", "2");
//                DataSnapshot allAvailableLotSnapshot = dataSnapshot.child("Available_Lot");
//                Log.e("firebase2", "3");
//                Iterable<DataSnapshot> availableLotSnapshots = allAvailableLotSnapshot.getChildren();
//                Log.e("firebase2", "4");
//                for (DataSnapshot availableLotSnapshot : availableLotSnapshots) {
//                    Log.e("firebase2", "5");
//                    showData(availableLotSnapshot);
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d("firebase", "1");

                Object value = dataSnapshot.getValue(Object.class);
                Log.d("firebase", "2");
                DataSnapshot allAvailableLotSnapshot = dataSnapshot.child("Available_Lot");
                Log.d("firebase", "3");
                Iterable<DataSnapshot> availableLotSnapshots = allAvailableLotSnapshot.getChildren();
                Log.d("firebase", "4");
                for (DataSnapshot availableLotSnapshot : availableLotSnapshots) {
                    Log.d("firebase", "5");
                    DataRecord c = availableLotSnapshot.getValue(DataRecord.class);
                    currentAvailableLot.add(c);
                    mCurrentAvailableLot.add(c);
                }

//                for (DataRecord d : currentAvailableLot) {
//                    Log.e("ForSingleValueEvent:", d.getLocation());
//                }

                for (DataRecord d : currentAvailableLot) {
                    String searchString = d.getLocation().trim();
                    Log.e("searchString:", searchString);
                    String title = searchString;
                    Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.US);
                    List<Address> list = new ArrayList<>();
                    try{
                        list = geocoder.getFromLocationName(searchString, 1);
                        Log.e("list is empty?", list.isEmpty() + "");
                        for (Address a : list){
                            Log.e("in geoLocate2", a.getAddressLine(0).toString());
                        }

                    }catch (IOException e){
                        Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
                    }
//                    Log.e("onDataChange:", list.isEmpty()+"");
                    if(list.size() > 0){
                        Address address = list.get(0);
                        double longitude = address.getLongitude();
                        double latitude = address.getLatitude();
                        LatLng latLng = new LatLng(latitude, longitude);
                        String snippet =  address.getAddressLine(0);

                        MarkerOptions options = new MarkerOptions()
                                .position(latLng)
                                .title(title)
                                .snippet(snippet)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        Marker marker = mMap.addMarker(options);
                        detailMarkerMap.put(marker, d);
                        recordToMarker.put(d, marker);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
        return currentAvailableLot;
    }


    private void init(){
        Log.e(TAG, "init: initializing");
//        mCurrentAvailableLot = getAvailableLot();
        Log.e("in init:", mCurrentAvailableLot.size()+"");

       for(DataRecord d :mCurrentAvailableLot) {

           Log.e("in init:", d.getLocation());

       }
        mSearchText.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this.getContext(), mGoogleApiClient,
                LAT_LNG_BOUNDS, null);
        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                Log.e(TAG,"Im in sSearchText listener");
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });

        hideSoftKeyboard();
    }

    // search location from mSearchText
    private void geoLocate(){
        Log.e(TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();
        Log.e("searchString", searchString);
        Geocoder geocoder = new Geocoder(this.getContext());
//        Log.e("in geoLocate", geocoder.toString());
        mList = new ArrayList<>();
        try{
            if(searchString != null && searchString.length() >= 20){
                mList = geocoder.getFromLocationName(searchString.substring(20), 1);
            } else {
                mList = geocoder.getFromLocationName(searchString, 1);

            }
            Log.e("list is empty?", mList.isEmpty() + "");
            for (Address a : mList){
                Log.e("length", mList.size() + "");
                Log.e("in geoLocate2", a.toString());
            }

        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(mList.size() > 0){
            Address address = mList.get(0);

            Log.e(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
            mSearchLocationTitle = address.getAddressLine(0);

        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.e(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        addMarkers(latLng, title);
//        if(!title.equals("Current Location")){
//            MarkerOptions options = new MarkerOptions()
//                    .position(latLng)
//                    .title(title)
//                    .snippet("Target Place")
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//            mMap.addMarker(options);
//        }

        hideSoftKeyboard();
    }

    private void addMarkers(LatLng latLng,String title) {
        if(!title.equals("Current Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .snippet("Target Place")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.addMarker(options);
        }
    }

    private void addMarkersFromFirebase(LatLng latLng, String title, String snippet) {

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mMap.addMarker(options);

    }

    private void hideSoftKeyboard(){
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }



    private void requestPermission() {
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();


    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        displayLocation();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPlayServices()) {
                        buildGoogleApiClient();
                        createLocationRequest();
                        displayLocation();
                    }
                }
            }
        }
    }
        /*
        --------------------------- google places API autocomplete suggestions -----------------
     */

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboard();

            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(i);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if(!places.getStatus().isSuccess()){
                Log.e(TAG, "onResult: Place query did not complete successfully: " + places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);

            try{
                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                Log.d(TAG, "onResult: name: " + place.getName());
                mPlace.setAddress(place.getAddress().toString());
                Log.d(TAG, "onResult: address: " + place.getAddress());
//                mPlace.setAttributions(place.getAttributions().toString());
//                Log.d(TAG, "onResult: attributions: " + place.getAttributions());
                mPlace.setId(place.getId());
                Log.d(TAG, "onResult: id:" + place.getId());
                mPlace.setLatlng(place.getLatLng());
                Log.d(TAG, "onResult: latlng: " + place.getLatLng());
                mPlace.setRating(place.getRating());
                Log.d(TAG, "onResult: rating: " + place.getRating());
                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                Log.d(TAG, "onResult: phone number: " + place.getPhoneNumber());
                mPlace.setWebsiteUri(place.getWebsiteUri());
                Log.d(TAG, "onResult: website uri: " + place.getWebsiteUri());

                Log.d(TAG, "onResult: place: " + mPlace.toString());
            }catch (NullPointerException e){
                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage() );
            }
//
            if(mList == null || mList.size() == 0){
                moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                        place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mPlace.getName());
            }


            places.release();
        }
    };


}


