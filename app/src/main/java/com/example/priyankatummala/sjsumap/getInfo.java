package com.example.priyankatummala.sjsumap;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by priyanka.tummala on 10/23/16.
 */

public class getInfo extends AsyncTask<String , Integer, JSONObject> {

    JSONObject matrix = new JSONObject();
    String total_distance,travel_time;
    int total_time;
    Object object;

    @Override
    protected JSONObject doInBackground(String... urls) {

        URL url = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            url = new URL(urls[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoInput(true);
            InputStreamReader stream = new InputStreamReader(urlConnection.getInputStream());
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }

            matrix = new JSONObject(stringBuilder.toString());
            Log.d("HTTP", "pass");

        } catch (Exception e) {
            Log.d("HTTP", "Fail");
            e.printStackTrace();
        }

        return matrix;
    }

    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        Log.i("progress :" , String.valueOf(progress[0]));
    }

    protected void onPostExecute(JSONObject matrix)
    {
        super.onPostExecute(matrix);
        Boolean status = false;
        if (matrix != null){
            try {

                JSONArray rows = matrix.getJSONArray("rows");

                JSONObject elements = rows.getJSONObject(0);

                JSONArray elementsArray = elements.getJSONArray("elements");

                JSONObject distance_time = elementsArray.getJSONObject(0);
                JSONObject distance = distance_time.getJSONObject("distance");


                JSONObject time = distance_time.getJSONObject("duration");


                total_distance = distance.getString("text");
                travel_time=time.getString("value");
                total_time = Integer.parseInt(travel_time);
                total_time=java.lang.Math.round((float)(total_time)/60);

                object = (Object) distance_time;
                status=true;


            } catch (Exception e) {

                BuildingInformation.receiver.obtainMessage(2,null ).sendToTarget();

            }
            if(status==true)
                BuildingInformation.receiver.obtainMessage(1, object).sendToTarget();
        }

    }
}
