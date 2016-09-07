package com.finoit.weatherapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.finoit.weatherapp.GoogleApiHelper.LocationHelper;
import com.finoit.weatherapp.Interfaces.OnParseInterface;
import com.finoit.weatherapp.Interfaces.VolleyInterface;
import com.finoit.weatherapp.R;
import com.finoit.weatherapp.VolleyHelper.MySingleton;
import com.finoit.weatherapp.VolleyHelper.ParseJson;
import com.google.android.gms.location.LocationListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements VolleyInterface, OnParseInterface,
                LocationListener {

    TextView mTxtDisplay;
    LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationHelper = new LocationHelper(this,this);
        mTxtDisplay = (TextView) findViewById(R.id.txtDisplay);
        MySingleton.getWeather(this);

    }

    @Override
    public void parseJson(JSONObject response) throws JSONException {
        new ParseJson(this).execute(response);

    }

    @Override
    public void onParse(String s) {
        mTxtDisplay.setText(s);
    }


    @Override
    protected void onStart() {
        super.onStart();
        locationHelper.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationHelper.disconnect();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == locationHelper.REQUEST_CHECK_SETTINGS){
            if(resultCode == RESULT_OK){
                locationHelper.checkLocationSettings();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("**update location**",location.toString());
    }

    public void refresh(View view){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}

