package com.example.priyankatummala.sjsumap;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,LocationListener{

    public static final String buildingname="com.example.priyankatummala.sjsumap.buildingName";
    public static final String BLat="com.example.priyankatummala.sjsumap.lattitude";
    public static final String BLng="com.example.priyankatummala.sjsumap.longitude";
    ArrayList<String> listBuilding;
    ArrayAdapter adapter;
    ListView listview;
    String[] buildings;
    ImageView mapimage;
    FrameLayout fl_Main;
    DrawView king;
    DrawView eng;
    DrawView yoshiro;
    DrawView studentunion;
    DrawView bbc;
    DrawView southparking;
    DrawView userlocation;
    Menu searchMenu;
    Double currentLatitude=0.0;
    Double currentLongitude=0.0;
    String provider;
    double lat=0.0, lng=0.0;
    String mode;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    Location location;
    double latitude;
    double longitude;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    String mprovider;
    int x1,x2,y1,y2;

    /**Hard Code Values*/
    double  Westgarage_lat=37.331489;
    double  Westgarage_lng=-121.882864;


    double King_lat=37.335844;
    double  King_lng=-121.886130;

    double  North_lat=37.338935;
    double  North_lng=-121.879728;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listview);
        mapimage = (ImageView) findViewById(R.id.mapimage);

        fl_Main = (FrameLayout) findViewById(R.id.activity_map);
        boolean isGPSEnabled = false;
        removeLocationDrawViews();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Toast.makeText(MainActivity.this, "Check Internet and Location settings!", Toast.LENGTH_LONG).show();

            return;
        }
        locationManager.requestLocationUpdates(provider, 100, 1000, this);
        Location location = locationManager.getLastKnownLocation(provider);
        //onLocationChanged(location);

        if(location != null){

            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

            Log.i("Status", "Latitude is: "+ currentLatitude.toString());
            Log.i("Status", "Longitude is: "+ currentLongitude.toString());

            if(currentLatitude>=37.331489 && currentLatitude<=37.338939 && currentLongitude>=-121.886130 && currentLongitude<=-121.876430){
                mode="Walking";
            }
            else mode = "Driving";
//            Log.i("mode", mode);

        }


        mapimage.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(getApplicationContext(), BuildingInformation.class);
                int[] posXY = new int[2];
                v.getLocationInWindow(posXY);
                //int x = posXY[0];
                //int y = posXY[1];
                float x = event.getX();
                float y = event.getY();

//                Log.d("EventX",String.valueOf(x));
//                Log.d("EventY",String.valueOf(y));
//                Log.d("PosXY0",String.valueOf(posXY[0]));
//                Log.d("PosXY1",String.valueOf(posXY[1]));

                x1=mapimage.getLeft();
                y1=mapimage.getTop();
                x2 = mapimage.getRight();
                y2 = mapimage.getBottom();

//                Log.d("X2",String.valueOf(x2));
//                Log.d("Y2",String.valueOf(y2));

                if (x >= (x2*0.0875) && x<= (x2*0.205) && y>=(y2*0.335) && y<=(y2*0.435)){

                    intent.putExtra(buildingname,"KING");

                }
                else  if (x >= (x2*0.515) && x<= (x2*0.655) && y>=(y2*0.335) && y<=(y2*0.445)) {
                    //Intent intent = new Intent(getApplicationContext(), BuildingInformation.class);
                    intent.putExtra(buildingname, "ENG");
//                    startActivity(intent);
                }
                else  if (x >= (x2*0.0850) && x<= (x2*0.200) && y>=(y2*0.558) && y<=(y2*0.632)) {
                   // Intent intent = new Intent(getApplicationContext(), BuildingInformation.class);
                    intent.putExtra(buildingname, "YUH");
//                    startActivity(intent);
                }

                else  if (x >= (x2*0.800) && x<= (x2*0.900) && y>=(y2*0.510) && y<=(y2*0.565)) {
                 //   Intent intent = new Intent(getApplicationContext(), BuildingInformation.class);
                    intent.putExtra(buildingname, "BBC");
//                    startActivity(intent);
                }

                else  if (x >= (x2*0.300) && x<= (x2*0.480) && y>=(y2*0.725) && y<=(y2*0.810)) {
                   // Intent intent = new Intent(getApplicationContext(), BuildingInformation.class);
                    intent.putExtra(buildingname, "SPG");
//                    startActivity(intent);
                }

                else  if (x >= (x2*0.510) && x<= (x2*0.680) && y>=(y2*0.455) && y<=(y2*0.500)) {
                   // Intent intent = new Intent(getApplicationContext(), BuildingInformation.class);
                    intent.putExtra(buildingname, "SU");
//                    startActivity(intent);
                }
                else{
                    return true;
                }

                intent.putExtra(BLat,currentLatitude);
                intent.putExtra(BLng,currentLongitude);
                intent.putExtra("mode",mode);
                startActivity(intent);

//               Toast.makeText(MainActivity.this, x + ":" + y, Toast.LENGTH_SHORT)
//                        .show();

                //  if (x>=120 && )

                return true;
            }
        });





//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            isGPSEnabled = locationManager
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
//            if (isGPSEnabled) {
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//                Log.d("GPS Enabled", "GPS Enabled");
//                if (locationManager != null) {
//                    location = locationManager
//                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                    if (location != null) {
//                        latitude = location.getLatitude();
//                        longitude = location.getLongitude();
//                        Log.d("Lat",String.valueOf(latitude));
//                        Log.d("Long",String.valueOf(latitude));
//
//                    }
//                }
//            }
//        }
      //  initialise();



    }



    public void initialise() {
        x1=mapimage.getLeft();
        y1=mapimage.getTop();
        x2 = mapimage.getRight();
        y2 = mapimage.getBottom();
       // Log.d(String.valueOf(x1),String.valueOf(x2));
        //Log.d(String.valueOf(y1),String.valueOf(y2));

//        Log.d("MapLeft",String.valueOf(x1));
//        Log.d("MapTop",String.valueOf(y1));
//        Log.d("MapRight",String.valueOf(x2));
//        Log.d("MapBottom",String.valueOf(y2));

        king = new DrawView(MainActivity.this, (int)(x2*0.0875), (int)(y2*0.335), (int)(x2*0.205), (int)(y2*0.435));
        eng = new DrawView(MainActivity.this, (int)(x2*0.515), (int)(y2*0.335), (int)(x2*0.655), (int)(y2*0.445));
        yoshiro = new DrawView(MainActivity.this, (int)(x2*0.0850), (int)(y2*0.558), (int)(x2*0.200), (int)(y2*0.632));
        studentunion = new DrawView(MainActivity.this, (int)(x2*0.510), (int)(y2*0.455), (int)(x2*0.680), (int)(y2*0.500));
        bbc = new DrawView(MainActivity.this, (int)(x2*0.800), (int)(y2*0.510), (int)(x2*0.900), (int)(y2*0.565));
        southparking = new DrawView(MainActivity.this, (int)(x2*0.300), (int)(y2*0.725), (int)(x2*0.480), (int)(y2*0.810));

        buildings = new String[]{"King Library", "Engineering Building", "Yoshihiro Uchida Hall", "Student Union", "BBC", "South Parking Garage"};
        listBuilding = new ArrayList<>(Arrays.asList(buildings));

        adapter = new ArrayAdapter(this, R.layout.list_items, R.id.txtitem, listBuilding);
        listview.setAdapter(adapter);
        listview.setVisibility(View.INVISIBLE);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                // based on the item clicked go to the relevant activity
                String clickedItem = (String) adapter.getItemAtPosition(position);
                TextView textView = (TextView) v.findViewById(R.id.txtitem);
                String text = textView.getText().toString();

                removeDrawViews();
               // removeLocationDrawViews();
                if (text.toLowerCase().contains(buildings[0].toLowerCase())) {//King Library
                    fl_Main.addView(king);

                } else if (text.toLowerCase().contains(buildings[1].toLowerCase())) {//Engineering Building
                    fl_Main.addView(eng);
                } else if (text.toLowerCase().contains(buildings[2].toLowerCase())) {//Yoshihiro Uchida Hall
                    fl_Main.addView(yoshiro);
                } else if (text.toLowerCase().contains(buildings[3].toLowerCase())) {//Student Union
                    fl_Main.addView(studentunion);
                } else if (text.toLowerCase().contains(buildings[4].toLowerCase())) {//BBC
                    fl_Main.addView(bbc);
                } else if (text.toLowerCase().contains(buildings[5].toLowerCase())) {//South Parking Garage
                    fl_Main.addView(southparking);
                }
                listview.setVisibility(View.INVISIBLE);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;

    }



    @Override
    public void onLocationChanged(Location location) {

        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        if(currentLatitude>=37.331489 && currentLatitude<=37.338939 && currentLongitude>=-121.886130 && currentLongitude<=-121.876430){
            mode="Walking";
        }
        else mode = "Driving";

        Log.d("Location", "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude", "status");

    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude", "enable");

    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude", "disable");

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ListView listview = (ListView) findViewById(R.id.listview);
        if (newText.isEmpty()) {
            initialise();
            removeDrawViews();
            //removeLocationDrawViews();
        } else {
            for (String item : buildings) {
                if (!item.toLowerCase().contains(newText.toLowerCase())) {
                    listBuilding.remove(item);
                }
            }
            listview.setVisibility(View.VISIBLE);
            listview.bringToFront();
            adapter.notifyDataSetChanged();
        }

        // Toast.makeText(this, newText + "new", Toast.LENGTH_SHORT)
        //       .show();
        return false;
    }

    class DrawView extends ImageView {

        float leftx;
        float topy;
        float rightx;
        float bottomy;
        float xpixel;
        float ypixel;
        boolean rectangle=false;
        public DrawView(Context context) {
            super(context);
        }

        public DrawView(Context context, float leftx, float topy, float rightx, float bottomy) {
            super(context);
            removeDrawViews();
            this.leftx = leftx;
            this.topy = topy;
            this.rightx = rightx;
            this.bottomy = bottomy;
            this.rectangle=true;
        }

        public DrawView(Context context, double xpixel,double ypixel) {
            super(context);
            removeDrawViews();
            this.xpixel = (float)xpixel;
            this.ypixel = (float)ypixel;
        }

        DrawView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        DrawView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
          //  removeDrawViews();
            if(this.rectangle==true){
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(10);

//                Log.d(String.valueOf(leftx),String.valueOf(rightx));
//                Log.d(String.valueOf(topy),String.valueOf(bottomy));
               /* float leftx = 20;
                float topy = 20;
                float rightx = 50;
                float bottomy = 100;
                canvas.drawRect(leftx, topy, rightx, bottomy, paint);*/
                    //60.029297:524.21875 topleft x y
                //1350.8789:1987.8125
                canvas.drawRect(leftx, topy, rightx, bottomy, paint);
                /*canvas.drawRect(120, 569, 270, 879, paint);//King
                canvas.drawRect(720, 574, 935, 894, paint);//Eng
                canvas.drawRect(100, 1213, 215, 1438, paint);//Yoshihiro
                canvas.drawRect(720, 919, 985, 1073, paint);//SU
                canvas.drawRect(1155, 1133, 1305, 1223, paint);//BBC
                canvas.drawRect(420, 1718, 695, 1947, paint);//SPG*/
            }
            else{
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(xpixel, ypixel, 30, paint);
            }

        }


    }

    public void removeDrawViews() {
        fl_Main.removeView(king);
        fl_Main.removeView(eng);
        fl_Main.removeView(yoshiro);
        fl_Main.removeView(studentunion);
        fl_Main.removeView(bbc);
        fl_Main.removeView(bbc);
        fl_Main.removeView(southparking);
    }

    public void removeLocationDrawViews() {
        fl_Main.removeView(userlocation);
    }

    public void showlocation(View v){

        x1=mapimage.getLeft();
        y1=mapimage.getTop();
        x2 = mapimage.getRight();
        y2 = mapimage.getBottom();


        removeLocationDrawViews();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(provider);
        //onLocationChanged(location);

        if(location != null){

            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

            Log.i("Status", "Latitude is: "+ currentLatitude.toString());
            Log.i("Status", "Longitude is: "+ currentLongitude.toString());

             lat=currentLatitude;//Pass User Latitude here
           // Log.d("lat:",String.valueOf(lat));

             lng=currentLongitude;//Pass User Longitude here
            //Log.d("long:",String.valueOf(lng));

        }

//        double lat=currentLatitude;//Pass User Latitude here
//        Log.d("lat:",String.valueOf(lat));
//
//        double lng=currentLongitude;//Pass User Longitude here
//        Log.d("long:",String.valueOf(lng));





        double X_offset=x2*.0347;
        //Log.d("X_offset:",String.valueOf(X_offset));
        double Y_offset=y2*0.317;
       // Log.d("Y_offset:",String.valueOf(Y_offset));

        double mapXsize=x2*.93-X_offset;
        //Log.d("mapXsize:",String.valueOf(mapXsize));
        double mapYsize=y2*.81-Y_offset;
        //Log.d("mapYsize:",String.valueOf(mapYsize));


        if(lat>=37.331489 && lat<=37.338939 && lng>=-121.886130 && lng<=-121.876430){



            double betweenK_W=newdistance (King_lat,King_lng, Westgarage_lat, Westgarage_lng);
           // Log.d("betweenK_W:",String.valueOf(betweenK_W));


            double betweenK_N=newdistance (King_lat,King_lng, North_lat, North_lng);
           // Log.d("betweenK_N:",String.valueOf(betweenK_N));

            double betweenK_point=newdistance (King_lat,King_lng, lat, lng);    //a1 square
           // Log.d("betweenK_point:",String.valueOf(betweenK_point));

            double betweenW_point=newdistance (Westgarage_lat,Westgarage_lng, lat, lng);    //a2 square
           // Log.d("betweenW_point:",String.valueOf(betweenW_point));

            double betweenW_Y=(Math.pow(betweenW_point,2)+Math.pow(betweenK_W,2)-Math.pow(betweenK_point,2))/(2*betweenK_W);//y
           // Log.d("betweenW_Y:",String.valueOf(betweenW_Y));
            
            double XVal=Math.sqrt(Math.pow(betweenW_point,2)-Math.pow(betweenW_Y,2));
           // Log.d("XVal:",String.valueOf(XVal));

            double YVal=betweenK_W-betweenW_Y;
           // Log.d("YVal:",String.valueOf(YVal));

            double Xpixel=X_offset+(mapXsize*XVal)/betweenK_N;
           // Log.d("Xpixel:",String.valueOf(Xpixel));
            double Ypixel=Y_offset+(mapYsize*YVal)/betweenK_W;

           // Log.d("Ypixel:",String.valueOf(Ypixel));
            removeLocationDrawViews();
            userlocation=new DrawView(MainActivity.this,Xpixel,Ypixel);
            fl_Main.addView(userlocation);
//            Toast.makeText(MainActivity.this, lat + ":" + lng, Toast.LENGTH_SHORT)
//            .show();


        }
        else{

            Toast.makeText(MainActivity.this, "You are not on the campus!", Toast.LENGTH_SHORT)
                    .show();
        }
//        Toast.makeText(MainActivity.this, lat + ":" + lng, Toast.LENGTH_SHORT)
//                .show();

    }


    public double newdistance(double lat1,double lon1,double lat2,double lon2){

	/*var lat2=37.331532;
	var lon2=-121.882858;

	var lat1=37.335844;
	var lon1=-121.886071;*/


        double radlat1 = Math.PI * lat1/180;
        double radlat2 = Math.PI * lat2/180;
        double theta = lon1-lon2;
        double radtheta = Math.PI * theta/180;
        double dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
        dist = Math.acos(dist);
        dist = dist * 180/Math.PI;
        dist = dist * 60 * 1.1515;

        //console.log("Distance:"+dist);
        return dist;
    }
}



