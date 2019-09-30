package com.example.gps_4_appdisplaymycurrentlocation;

import android.Manifest;
import android.location.LocationListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {


    GoogleMap map;

    LocatioListinorEx locatioListinorEx= new LocatioListinorEx(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},12);


        myThred thred= new myThred();
        thred.start();


    }





class myThred extends Thread
{

    public  void run()
    {


        while (true)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    map.clear();
                    if(locatioListinorEx.getLocation() != null) {

                        LatLng NewDelhi = new LatLng(locatioListinorEx.getLocation().getLatitude(), locatioListinorEx.getLocation().getLongitude());
                        map.addMarker(new MarkerOptions().position(NewDelhi).title("NewDelhi").snippet("here i live"));
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(NewDelhi, 4));

                    }
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map=googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }
}
