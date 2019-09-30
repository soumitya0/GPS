package com.example.gps_2_locationlistiner;

import android.Manifest;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_getLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_getLoc=(Button)findViewById(R.id.btn_GetLoc);

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},12);

        btn_getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker gpsTracker= new GPSTracker(getApplication());
                Location l=gpsTracker.getLocation();

                if(l !=null)
                {
                    double lat=l.getLatitude();
                    double log=l.getLongitude();

                    Toast.makeText(getApplicationContext(),"Lat :"+lat+ "\n lon :"+ log,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
