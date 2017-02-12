package com.example.priyankatummala.sjsumap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

public class StreetViewActivity extends AppCompatActivity
        implements OnStreetViewPanoramaReadyCallback {
    String building_name;
    String latlong[]=new String[]{"37.3358734,-121.8858809", "37.3373555,-121.8828865", "37.3337986,-121.8845346", "37.3363913,-121.8807342", "37.3369032,-121.8782262", "37.3326232,-121.880525"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_street_view);
        building_name= getIntent().getExtras().getString("BuildingName");
        StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
        double lat=0,lng=0;
        if(building_name.equals("KING")){
           lat= Double.valueOf(latlong[0].split(",")[0]);
            lng= Double.valueOf(latlong[0].split(",")[1]);
        }
        else if(building_name.equals("ENG")){
            lat= Double.valueOf(latlong[1].split(",")[0]);
            lng= Double.valueOf(latlong[1].split(",")[1]);
        }
        else if(building_name.equals("YUH")){
            lat= Double.valueOf(latlong[2].split(",")[0]);
            lng= Double.valueOf(latlong[2].split(",")[1]);
        }
        else if(building_name.equals("SU")){
            lat= Double.valueOf(latlong[3].split(",")[0]);
            lng= Double.valueOf(latlong[3].split(",")[1]);
        }
        else if(building_name.equals("BBC")){
            lat= Double.valueOf(latlong[4].split(",")[0]);
            lng= Double.valueOf(latlong[4].split(",")[1]);
        }
        else if(building_name.equals("SPG")){
            lat= Double.valueOf(latlong[5].split(",")[0]);
            lng= Double.valueOf(latlong[5].split(",")[1]);
        }

        panorama.setPosition(new LatLng(lat,lng));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
