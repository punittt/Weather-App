package com.finoit.weatherapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.finoit.weatherapp.Adapters.MyCursorAdapter;
import com.finoit.weatherapp.DatabaseHelper.WeatherDataAcessHelper;
import com.finoit.weatherapp.DatabaseHelper.WeatherDatabaseContract;
import com.finoit.weatherapp.ErrorListener;
import com.finoit.weatherapp.GoogleApiHelper.LocationHelper;
import com.finoit.weatherapp.Interfaces.OnParseInterface;
import com.finoit.weatherapp.Interfaces.VolleyInterface;
import com.finoit.weatherapp.Models.WeatherData;
import com.finoit.weatherapp.R;
import com.finoit.weatherapp.VolleyHelper.MySingleton;
import com.finoit.weatherapp.VolleyHelper.ParseJson;
import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.location.LocationListener;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements VolleyInterface, OnParseInterface,
                LocationListener {

    LocationHelper locationHelper;
    WeatherDataAcessHelper weatherDataAcessHelper;
    Boolean isConnected;
    ListView list;
    MyCursorAdapter myadapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkNetworkState();
        weatherDataAcessHelper = new WeatherDataAcessHelper(this);
        locationHelper = new LocationHelper(this,this);
        list = (ListView)findViewById(R.id.listview);

    }

    @Override
    public void parseJson(JSONObject response) throws JSONException {
        Log.d("*****","Calling Parse Json");
        new ParseJson(this).execute(response);

    }

    @Override
    public void onParse(WeatherData[] weatherData) {
        weatherDataAcessHelper.deleteDB();
        for(int i = 0; i<5; i++){
            weatherDataAcessHelper.putData(weatherData[i]);
            Log.d("data is inserting",weatherData[i].getDay());
        }
        retreiveData();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(isConnected){
            locationHelper.connect();
        }
        else{
            retreiveData();
        }
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

    private void checkNetworkState(){
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            isConnected = true;
        } else {
            isConnected = false;
        }
    }

    private void retreiveData(){
        cursor = weatherDataAcessHelper.getData();
        cursor.moveToFirst();
        myadapter = new MyCursorAdapter(this,cursor);
        list.setAdapter(myadapter);

//        do{
//            String result = cursor.getString(cursor.getColumnIndex(WeatherDatabaseContract.WeatherEntry.Day))
//                    +": Max Temp: "
//                    +cursor.getString(cursor.getColumnIndex(WeatherDatabaseContract.WeatherEntry.MaxTemp))
//                    +", Min Temp: "
//                    +cursor.getString(cursor.getColumnIndex(WeatherDatabaseContract.WeatherEntry.MinTemp))
//                    +"\n";
//            Log.d("**final data result**",result);
//        }while (cursor.moveToNext());
    }
}

