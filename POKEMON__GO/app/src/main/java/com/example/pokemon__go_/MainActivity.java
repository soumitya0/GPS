package com.example.pokemon__go_;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback  {


    private GoogleMap mMap;
    Marker marker;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


            CheckUserPermsions();

            LoadPockemon();
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
        }

        void CheckUserPermsions(){
            if ( Build.VERSION.SDK_INT >= 23){
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED  ){
                    requestPermissions(new String[]{
                                    android.Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_ASK_PERMISSIONS);
                    return ;
                }
            }

            LocationListner();// init the contact list

        }
        //get acces to location permsion
        final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            switch (requestCode) {
                case REQUEST_CODE_ASK_PERMISSIONS:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        LocationListner();// init the contact list
                    } else {
                        // Permission Denied
                        Toast.makeText( this,"your message" , Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }


        void  LocationListner(){
            LocationListener locationListener = new MyLocationListener(this);
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            try{
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 3, locationListener);
            }catch (Exception ex)
            {
                Toast.makeText(this,"GRAND PERMISSION",Toast.LENGTH_LONG).show();
            }

            myThread m_thread= new myThread();
            m_thread.start();
        }

        Location oldLocation;
        class myThread extends Thread{
            myThread(){
                oldLocation= new Location("Start");
                oldLocation.setLatitude(0);
                oldLocation.setLongitude(0);
            }
            public void run(){
                while (true) try {
                    //Thread.sleep(1000);
                    if (oldLocation.distanceTo(MyLocationListener.location) == 0) {
                        continue;
                    }
                    oldLocation = MyLocationListener.location;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            mMap.clear();

                            // Add a marker in Sydney and move the camera
                            LatLng sydney = new LatLng(MyLocationListener.location.getLatitude(), MyLocationListener.location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(sydney).title("Me").icon(
                                    BitmapDescriptorFactory.fromResource(R.drawable.ash)
                            ));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18));

                            for (int i = 0; i < ListPockemons.size(); i++) {
                                Pockemon pockemon = ListPockemons.get(i);

                                if (pockemon.isCatch == false) {
                                    LatLng pockemonlocation =
                                            new LatLng(pockemon.location.getLatitude(), pockemon.location.getLongitude());
                                    marker=  mMap.addMarker(new MarkerOptions().position(pockemonlocation)
                                            .title(pockemon.name)
                                            .snippet(pockemon.des + ",power:" + pockemon.power)
                                            .icon(BitmapDescriptorFactory.fromResource(pockemon.Image)
                                            ));

                                    // catch the pockemn
                                    if (MyLocationListener.location.distanceTo(pockemon.location) < 2) {
                                        MyPower = MyPower + pockemon.power;
                                        marker.remove();

                                        Toast.makeText(MainActivity.this, "Catch Pockemon, new power is" + MyPower,
                                                Toast.LENGTH_SHORT).show();
                                        pockemon.isCatch = true;

                                    }
                                }


                            }

                        }
                    });


                } catch (Exception ex) {
                }
            }
        }

        double MyPower=0;
        //Add list f pockemon
        ArrayList<Pockemon> ListPockemons= new ArrayList<>();
        void LoadPockemon(){

            ListPockemons.add(new Pockemon(  R.drawable.ab, "Pikachu", "Type\tELECTRIC\n" + "Species\tMouse Pokémon\n" + "Height\t0.4 m\n" + "Weight\t6.0 kg \n", 65, 28.442965, 77.315068));
            ListPockemons.add(new Pockemon(  R.drawable.bb ,"Pachirisu",  "Type\tELECTRIC\n" + "Species\tEleSquirrel Pokémon\n" + "Height\t0.4 m\n" + "Weight\t3.9 kg\n", 50, 28.442966, 77.316369));
            ListPockemons.add(new Pockemon(  R.drawable.c, "Grookey ",  "Type\tGRASS\n" + "Species\tChimp Pokémon\n" + "Height\t0.3 m (1′00″)\n" + "Weight\t5.0 kg (11.0 lbs)\n", 55, 28.445360, 77.315641  ));

            ListPockemons.add(new Pockemon(  R.drawable.d, "Bulbasaur", "Type\tGRASS POISON\n" + "Species\tSeed Pokémon\n" + "Height\t0.7 m (2′04″)\n" + "Weight\t6.9 kg (15.2 lbs)\n", 55, 28.441214, 77.316377));
            ListPockemons.add(new Pockemon(  R.drawable.e ,"marill ",  "Type\tWATER FAIRY\n" + "Species\tAqua Mouse Pokémon\n" + "Height\t0.4 m (1′04″)\n" + "Weight\t8.5 kg (18.7 lbs)", 90.5, 28.441071, 77.316944));
            ListPockemons.add(new Pockemon(  R.drawable.f, "Piplup",  "Type\tWATER\n" + "Species\tPenguin Pokémon\n" + "Height\t0.4 m (1′04″)\n" + "Weight\t5.2 kg (11.5 lbs)\n", 33.5, 28.440401, 77.317772  ));

            ListPockemons.add(new Pockemon(  R.drawable.g, "Chikorita", "Type\tGRASS\n" + "Species\tLeaf Pokémon\n" + "Height\t0.9 m\n" + "Weight\t6.4 kg\n", 55, 28.441889, 77.315584));
            ListPockemons.add(new Pockemon(  R.drawable.h ,"Turtwig",  "Type\tGRASS\n" + "Species\tTiny Leaf Pokémon\n" + "Height\t0.4 m\n" + "Weight\t10.2 kg\n", 90.5, 28.442765, 77.313196));
            ListPockemons.add(new Pockemon(  R.drawable.i, "zygarde cell and core",  "Type\tDRAGON GROUND\n" + "Species\tOrder Pokémon\n" + "Height\t5.0 m\n" + "Weight\t305.0 kg", 33.5, 28.441925, 77.313444));

            ListPockemons.add(new Pockemon(  R.drawable.j, "Dugtrio", "Type\tGROUND\n" + "Species\tMole Pokémon\n" + "Height\t0.7 m\n" + "Weight\t33.3 kg", 55, 28.441140, 77.314158));
            ListPockemons.add(new Pockemon(  R.drawable.k ,"Mew",  "Type\tPSYCHIC\n" + "Species\tNew Species Pokémon\n" + "Height\t0.4 m\n" + "Weight\t4.0 kg", 90.5, 28.442219, 77.316536));
            ListPockemons.add(new Pockemon(  R.drawable.l, "Exeggcute",  "Type\tGRASS PSYCHIC\n" + "Species\tEgg Pokémon\n" + "Height\t0.4 m\n" + "Weight\t2.5 kg", 33.5, 28.441642, 77.314890));

            ListPockemons.add(new Pockemon(  R.drawable.m, "Tauros", "Type\tNORMAL\n" + "Species\tWild Bull Pokémon\n" + "Height\t1.4 m\n" + "Weight\t88.4 kg", 55, 37.7789994893035, -122.401846647263));

            ListPockemons.add(new Pockemon(  R.drawable.n,"Gloom",  "Type\tGRASS POISON\n" + "Species\tWeed Pokémon\n" + "Height\t0.8 m \n" + "Weight\t8.6 kg\n", 90.5, 37.7949568502667,  -122.410494089127));


        }
    }

