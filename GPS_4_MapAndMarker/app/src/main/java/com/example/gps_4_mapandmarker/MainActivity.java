package com.example.gps_4_mapandmarker;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback
{

    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {

        map=googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng NewDelhi= new LatLng(28.629119, 77.205691);

        LatLng Durgapur= new LatLng(23.696751, 87.311614);

        LatLng Kochi= new LatLng(10.291577, 76.192286);

        //ADDING DEFAULT MARKER TO MAP
        map.addMarker(new MarkerOptions().position(NewDelhi).title("NewDelhi").snippet("here i live"));
        map.addMarker(new MarkerOptions().position(Durgapur).title("Durgapur").snippet("here i live"));
        map.addMarker(new MarkerOptions().position(Kochi).title("Kochi").snippet("here i live"));

        // THIS IS YOUR ADDING YOUR OWN MAP MARKER TO YOUR MAP
        /* map.addMarker(new MarkerOptions().position(NewDelhi).
                title("NewDelhi").
                snippet("here i live").
                icon(BitmapDescriptorFactory.fromResource(R.drawable.orangehumidity2))
                );*/


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(NewDelhi,4));

        map.addCircle(new CircleOptions()
                .center(NewDelhi)
                .radius(20000)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));

        //Polyline
        map.addPolyline(new PolylineOptions()
        .add(NewDelhi,Durgapur)
        .width(25)
        .color(Color.BLUE)
        .geodesic(true));


        // this will create a line a with this 3
        map.addPolygon(new PolygonOptions()
        .add(NewDelhi,Durgapur,Kochi));


        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.addMarker(new MarkerOptions().position(latLng).title("add by click"));

            }
        });

    }
}
