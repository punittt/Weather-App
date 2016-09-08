package com.finoit.weatherapp.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by emp269 on 9/8/2016.
 */
public class WeatherDatabaseContract {

    public static class WeatherEntry implements BaseColumns {
        public static final String TABLE_NAME = "WeatherEntry";
        public static final String Day = "day";
        public static final String MinTemp = "mintemp";
        public static final String MaxTemp = "maxtemp";
        public static final String Morn = "morn";
        public static final String Eve = "eve";
        public static final String Night = "night";
        public static final String Pressure = "pressure";
        public static final String Humidity = "humidity";
        public static final String Icon = "icon";
    }

    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + WeatherEntry.TABLE_NAME + " (" +
                    WeatherEntry._ID + " INTEGER PRIMARY KEY," +
                    WeatherEntry.Day + TEXT_TYPE + COMMA_SEP +
                    WeatherEntry.MinTemp + TEXT_TYPE + COMMA_SEP +
                    WeatherEntry.MaxTemp + TEXT_TYPE + COMMA_SEP +
                    WeatherEntry.Morn + TEXT_TYPE + COMMA_SEP +
                    WeatherEntry.Eve + TEXT_TYPE + COMMA_SEP +
                    WeatherEntry.Night + TEXT_TYPE + COMMA_SEP +
                    WeatherEntry.Pressure + TEXT_TYPE + COMMA_SEP +
                    WeatherEntry.Humidity + TEXT_TYPE + COMMA_SEP +
                    WeatherEntry.Icon + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WeatherEntry.TABLE_NAME;

}
