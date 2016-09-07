package com.finoit.weatherapp.Interfaces;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by emp269 on 8/31/2016.
 */
public interface VolleyInterface {
    public void parseJson(JSONObject response) throws JSONException;
}
