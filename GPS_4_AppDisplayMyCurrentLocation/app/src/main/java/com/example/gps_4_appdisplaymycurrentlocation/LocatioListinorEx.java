package com.example.gps_4_appdisplaymycurrentlocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class LocatioListinorEx implements LocationListener {

    Context context;
    public LocatioListinorEx(Context c) {
        context=c;
    }


    public Location getLocation()   //Location is dataType
    {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(context,"Permission not Granted",Toast.LENGTH_SHORT).show();

            return null;
        }


        LocationManager locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


        if(isGPSEnable)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2,10,this);

            Location l=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        }else
        {
            Toast.makeText(context,"Please enable GPS",Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    @Override
    public void onLocationChanged(Location location)
    {

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
}
