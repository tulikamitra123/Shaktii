package com.example.shaktii;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;


public class sosMyLocation extends FragmentActivity implements OnMapReadyCallback {


    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference myRef;
    String num1, num2, num3, num4, num5;
    private GoogleMap mMap;
    String uid;
    private Geocoder geocoder;


    public String mylat;
    public String mylon ;
    String message;
    int count=0;

    public double lati, longi;



    private LocationManager locationManager;
    private LocationListener locationListener;

    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;



    private LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_my_location);

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();

        uid = user.getUid();

        geocoder = new Geocoder(sosMyLocation.this, Locale.getDefault());



        myRef = FirebaseDatabase.getInstance().getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                num1 = snapshot.child(uid).child("contact1").getValue(String.class);
                num2 = snapshot.child(uid).child("contact2").getValue(String.class);
                num3 = snapshot.child(uid).child("contact3").getValue(String.class);
                num4 = snapshot.child(uid).child("contact4").getValue(String.class);
                num5 = snapshot.child(uid).child("contact5").getValue(String.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        binding = ActivityMapsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        // Read from the database
        

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        locationListener = new LocationListener() {
            @Override
                public void onLocationChanged(@NonNull Location location) {



                    try {


                        latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title("My location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


                        mylat = String.valueOf(location.getLatitude());
                        mylon = String.valueOf(location.getLongitude());

                        //getLocationDetails();

                        lati = Double.parseDouble(mylat);
                        longi = Double.parseDouble(mylon);

                        String address = null;
                        String city = null;
                        String state = null;
                        String country = null;
                        String postalcode = null;


                            List<Address> addresses = geocoder.getFromLocation(lati,longi, 1 );
                            address = addresses.get(0).getAddressLine(0);
                            city = addresses.get(0).getLocality();
                            state = addresses.get(0).getAdminArea();
                            country = addresses.get(0).getCountryName();
                            postalcode = addresses.get(0).getPostalCode();




                        message = "this is my location: " + address +" " + country +" "+ "\nlatitude: " + mylat + " longitude: "+ mylon;




                        while(count<1){
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(String.valueOf(num1),null,message,null,null);
                            smsManager.sendTextMessage(String.valueOf(num2),null,message,null,null);
                            smsManager.sendTextMessage(String.valueOf(num3),null,message,null,null);
                            smsManager.sendTextMessage(String.valueOf(num4),null,message,null,null);
                            smsManager.sendTextMessage(String.valueOf(num5),null,message,null,null);
                            count++;

                        }





                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(sosMyLocation.this,"error",Toast.LENGTH_SHORT).show();
                    }
                }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };



        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        }
        catch (SecurityException e){
            e.printStackTrace();
        }


    }


}