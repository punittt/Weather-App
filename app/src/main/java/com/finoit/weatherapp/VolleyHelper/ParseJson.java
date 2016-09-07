package com.finoit.weatherapp.VolleyHelper;

import android.content.Context;
import android.os.AsyncTask;

import com.finoit.weatherapp.Interfaces.OnParseInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by emp269 on 9/3/2016.
 */
public class ParseJson extends AsyncTask<JSONObject,Void,String>{
    OnParseInterface onParseInterface;

    public ParseJson(Context context){
        onParseInterface = (OnParseInterface)context;
    }

    @Override
    protected String doInBackground(JSONObject... params) {
        JSONArray list = null;
        String result = "";
        JSONObject main;
        String mintemp;
        String maxtemp;
        String date;
        try {
            list = params[0].getJSONArray("list");
            for(int i=0;i<5;i++) {
                main = list.getJSONObject(i).getJSONObject("temp");
                mintemp = main.getString("min");
                maxtemp = main.getString("max");
                date = list.getJSONObject(i).getString("dt");
                date = utctodate(date);
                result += "Date: " + date + ", Min Temp: " + mintemp + ", Max Temp: " + maxtemp + "\n";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        onParseInterface.onParse(s);
        super.onPostExecute(s);
    }

    private String utctodate(String unixSeconds){
        Date date = new Date(Long.valueOf(unixSeconds)*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // the format of your date
        sdf.setTimeZone(TimeZone.getDefault()); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}
