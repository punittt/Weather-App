package com.finoit.weatherapp.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.finoit.weatherapp.Models.WeatherData;

/**
 * Created by emp269 on 9/8/2016.
 */
public class WeatherDataAcessHelper {
    Context context;
    WeatherDatabaseHelper mDbHelper;

    public WeatherDataAcessHelper(Context context){
        this.context = context;
        mDbHelper = new WeatherDatabaseHelper(context);
    }

    public void putData(WeatherData weatherData) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(WeatherDatabaseContract.WeatherEntry.Day, weatherData.getDay());
        values.put(WeatherDatabaseContract.WeatherEntry.MinTemp, weatherData.getMintemp());
        values.put(WeatherDatabaseContract.WeatherEntry.MaxTemp, weatherData.getMaxtemp());
        values.put(WeatherDatabaseContract.WeatherEntry.Morn, weatherData.getMorn());
        values.put(WeatherDatabaseContract.WeatherEntry.Eve, weatherData.getEve());
        values.put(WeatherDatabaseContract.WeatherEntry.Night, weatherData.getNight());
        values.put(WeatherDatabaseContract.WeatherEntry.Pressure, weatherData.getPressure());
        values.put(WeatherDatabaseContract.WeatherEntry.Humidity, weatherData.getHumidity());
        values.put(WeatherDatabaseContract.WeatherEntry.Icon, weatherData.getWeather());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(WeatherDatabaseContract.WeatherEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteDB(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(WeatherDatabaseContract.WeatherEntry.TABLE_NAME,null,null);
        db.close();
    }

    public Cursor getData(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                WeatherDatabaseContract.WeatherEntry._ID,
                WeatherDatabaseContract.WeatherEntry.Day,
                WeatherDatabaseContract.WeatherEntry.MinTemp,
                WeatherDatabaseContract.WeatherEntry.MaxTemp,
                WeatherDatabaseContract.WeatherEntry.Morn,
                WeatherDatabaseContract.WeatherEntry.Eve,
                WeatherDatabaseContract.WeatherEntry.Night,
                WeatherDatabaseContract.WeatherEntry.Pressure,
                WeatherDatabaseContract.WeatherEntry.Humidity,
                WeatherDatabaseContract.WeatherEntry.Icon,
        };

        Cursor c = db.query(
                WeatherDatabaseContract.WeatherEntry.TABLE_NAME,    // The table to query
                projection,                                         // The columns to return
                null,                                               // The columns for the WHERE clause
                null,                                               // The values for the WHERE clause
                null,                                               // don't group the rows
                null,                                               // don't filter by row groups
                null                                                // The sort order
        );

        return c;
    }
}
