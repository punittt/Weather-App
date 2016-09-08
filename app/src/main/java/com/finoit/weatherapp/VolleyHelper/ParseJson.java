package com.finoit.weatherapp.VolleyHelper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.finoit.weatherapp.Interfaces.OnParseInterface;
import com.finoit.weatherapp.Models.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by emp269 on 9/3/2016.
 */
public class ParseJson extends AsyncTask<JSONObject, Void, WeatherData[]> {
    OnParseInterface onParseInterface;

    public ParseJson(Context context) {
        onParseInterface = (OnParseInterface) context;
    }

    @Override
    protected WeatherData[] doInBackground(JSONObject... params) {
        JSONArray list = null;
        JSONObject main;
        String mintemp, maxtemp, day, morn, eve, night, pressure, humidity, weather;

        WeatherData[] weatherData = new WeatherData[5];
        Long date;
        try {
            list = params[0].getJSONArray("list");
            for (int i = 0; i < 5; i++) {
                main = list.getJSONObject(i).getJSONObject("temp");
                mintemp = main.getString("min");
                maxtemp = main.getString("max");
                date = list.getJSONObject(i).getLong("dt");
                morn = main.getString("morn");
                eve = main.getString("eve");
                night = main.getString("night");
                pressure = list.getJSONObject(i).getString("pressure");
                humidity = list.getJSONObject(i).getString("humidity");
                weather = list.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main");
                day = utctodate(date);
                WeatherData model = new WeatherData();
                model.setDay(day);
                model.setEve(eve);
                model.setHumidity(humidity);
                model.setMaxtemp(maxtemp);
                model.setMintemp(mintemp);
                model.setMorn(morn);
                model.setNight(night);
                model.setPressure(pressure);
                model.setWeather(weather);
                weatherData[i] = model;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weatherData;
    }

    @Override
    protected void onPostExecute(WeatherData[] weatherData) {
        onParseInterface.onParse(weatherData);
        super.onPostExecute(weatherData);
    }

    private String utctodate(Long unixSeconds) throws ParseException {
        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // the format of your date
        sdf.setTimeZone(TimeZone.getDefault()); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        date = sdf.parse(formattedDate);
        DateFormat dateFormat = new SimpleDateFormat("EEEE");
        String day = dateFormat.format(date);
        return day;
    }
}
