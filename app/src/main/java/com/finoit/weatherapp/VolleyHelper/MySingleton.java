package com.finoit.weatherapp.VolleyHelper;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.finoit.weatherapp.Interfaces.VolleyInterface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by emp269 on 8/31/2016.
 */
public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private static VolleyInterface volleyInterface;

    private MySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


    public static void getWeather(final Context context, String url){

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyInterface = (VolleyInterface)context;
                        try {
                            volleyInterface.parseJson(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) { }
                });

        // Access the RequestQueue through your singleton class.
        getInstance(context).addToRequestQueue(jsObjRequest);
    }
}
