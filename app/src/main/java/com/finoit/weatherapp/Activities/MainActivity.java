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
import com.finoit.weatherapp.Models.WeatherData;
import com.finoit.weatherapp.R;
import com.finoit.weatherapp.VolleyHelper.MySingleton;
import com.finoit.weatherapp.VolleyHelper.ParseJson;
import com.google.android.gms.location.LocationListener;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements VolleyInterface, OnParseInterface,
                LocationListener {

    TextView mTxtDisplay;
    LocationHelper locationHelper;
    WeatherData[] weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherData = new WeatherData[5];
        locationHelper = new LocationHelper(this,this);
        mTxtDisplay = (TextView) findViewById(R.id.txtDisplay);

    }

    @Override
    public void parseJson(JSONObject response) throws JSONException {
        new ParseJson(this).execute(response);

    }

    @Override
    public void onParse(WeatherData[] weatherData) {
        this.weatherData = weatherData;
        for(int i = 0; i<5; i++){
        Log.d("*****final data******",this.weatherData[i].getDay());}
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
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
        String apikey = "&cnt=5&mode=json&appid=a6d73826d5ad5774ce341694609eca85";
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?lat="+latitude+"&lon="+longitude
                +apikey+"&units=metric";
        MySingleton.getWeather(this, url);
    }

    public void refresh(View view){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}

