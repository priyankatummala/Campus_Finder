package com.example.priyankatummala.sjsumap;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class BuildingInformation extends AppCompatActivity {

    BuildingInfo[] bArr = new BuildingInfo[6];
    //    GoogleApiClient mGoogleApiClient;
//    LocationRequest mLocationRequest;
    double currentLatitude;
    double currentLongitude;
    LocationManager locationManager;
    String provider;
    getInfo matrix;
    static TextView distance, time;
    static  ImageView travelIcon, clock;
    String building_name;
    static String mode;
    public static final String buildingname = "com.example.priyankatummala.sjsumap.buildingName";
    public static final String BLat = "com.example.priyankatummala.sjsumap.lattitude";
    public static final String BLng = "com.example.priyankatummala.sjsumap.longitude";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_building_information);
        InatializeBuilding();
        building_name = getIntent().getExtras().getString(buildingname);
        currentLatitude = getIntent().getExtras().getDouble(BLat);
        currentLongitude = getIntent().getExtras().getDouble(BLng);
        mode = getIntent().getExtras().getString("mode");
        String s = getIntent().getExtras().getString(mode);
        ImageView img = (ImageView) findViewById(R.id.building_image);
         travelIcon = (ImageView) findViewById(R.id.travelIcon);
         clock = (ImageView) findViewById(R.id.clock);

        //TextView name=(TextView) findViewById(R.id.building_name);
        TextView address = (TextView) findViewById(R.id.building_address);
        distance = (TextView) findViewById(R.id.distance);
        time = (TextView) findViewById(R.id.time);
        BuildingInfo building = new BuildingInfo();

        for (int i = 0; i < bArr.length; i++) {

            if (building_name.equals(bArr[i].code)) {
                building = bArr[i];
                setTitle(building.name);
            }
        }

        int id = getResources().getIdentifier(building.imageSrc, null, null);
        img.setImageResource(id);


        //name.setText(building.name);

        address.setText(building.address);

        if(currentLatitude == 0.0 &&  currentLongitude == 0.0)
        {
            distance.setText("Unable to fetch location! Check you settings");

        }
        else {
            String getDistance = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + currentLatitude + "," + currentLongitude + "&destinations=" + building.lattitude + "," + building.longitude + "&mode=" + mode;
            Log.d("url", getDistance);
            matrix = new getInfo();
            matrix.execute(getDistance);

        }
    }

    public void InatializeBuilding() {
        bArr[0] = new BuildingInfo("King Library", "KING", "Dr. Martin Luther King, Jr. Library\n 150 East San Fernando" +
                "Street,\n San Jose, CA 95112", "com.example.priyankatummala.sjsumap:drawable/king_library", 37.335380, -121.885052);
        bArr[1] = new BuildingInfo("Engineering Building", "ENG", "San José State University Charles W. Davidson" +
                "College of Engineering\n 1 Washington Square,\n San Jose, CA 95112", "com.example.priyankatummala.sjsumap:drawable/engineering_building", 37.337275, -121.882158);
        bArr[2] = new BuildingInfo("Yoshihiro Uchida Hall", "YUH", "Yoshihiro Uchida Hall,\n San Jose, CA 95112", "com.example.priyankatummala.sjsumap:drawable/yoshohiro_uchida_hall", 37.333679, -121.883735);
        bArr[3] = new BuildingInfo("Student Union", "SU", "Student Union Building,\n San Jose, CA 95112", "com.example.priyankatummala.sjsumap:drawable/student_union", 37.336336, -121.881346);
        bArr[4] = new BuildingInfo("BBC", "BBC", "Boccardo Business Complex,\n San Jose, CA 95112", "com.example.priyankatummala.sjsumap:drawable/bbc", 37.336644, -121.878521);
        bArr[5] = new BuildingInfo("South Parking Garage", "SPG", "San Jose State University South Garage\n 330" +
                "South 7th Street, San Jose, CA 95112", "com.example.priyankatummala.sjsumap:drawable/south_parking_garage", 37.333249, -121.880492);
    }


    public static android.os.Handler receiver = new android.os.Handler() {
        public void handleMessage(Message message) {

            JSONObject distance_time = (JSONObject) message.obj;
            String total_distance = null, travel_time = null;

            try {
                if (String.valueOf(message.what) == "2") {
                    distance.setText("Cannot naivgate from your location");
                } else {
                    JSONObject distance = distance_time.getJSONObject("distance");
                    JSONObject time = distance_time.getJSONObject("duration");
                    total_distance = distance.getString("text");
                    travel_time = time.getString("text");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (String.valueOf(message.what) != "2") {
                if(mode.equals("Walking")) {
                    travelIcon.setImageResource(R.drawable.walk);
                }
                else
                {
                    travelIcon.setImageResource(R.drawable.car);
                }
                clock.setImageResource(R.drawable.clock);
                distance.setText(mode + " Distance : " + total_distance);
                time.setText(mode + " Time : " + travel_time);
            }

        }
    };


    public void showStreetview(View v) {

        Intent intent = new Intent(getApplicationContext(), StreetViewActivity.class);
        intent.putExtra("BuildingName", building_name);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}



