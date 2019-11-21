package com.example.mixedlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


   FusedLocationProviderClient fusedLocationProviderClient;
   TextView showdata;
   PlacesClient placesClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showdata=findViewById(R.id.showdata);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        if (!Places.isInitialized())
        {
            Places.initialize(getApplicationContext(),"AIzaSyCYdY6IAHRD1QkxisenY2ik_dN1i0EAops");
        }
        placesClient=Places.createClient(this);
    }

    public void dothis(View view) {
        checkPermissions();
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Permission Granted",Toast.LENGTH_LONG).show();
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location!=null)
                    {
                        double lat=location.getLatitude();
                        double longi=location.getLongitude();
                        Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                        try {
                            List<Address>addresses=geocoder.getFromLocation(lat,longi,1);

                            Address address=addresses.get(0);
                            showdata.setText(""+address);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1)
        {
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                checkPermissions();
            }
            else
                Toast.makeText(getApplicationContext(),"Permission denied",Toast.LENGTH_LONG).show();
        }
    }

    public void MoveBro(View view) {

        startActivity(new Intent(MainActivity.this,MapActivity.class));
    }
}
