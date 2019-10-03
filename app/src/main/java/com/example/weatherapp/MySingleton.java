package com.example.weatherapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    public MySingleton(Context context){

        mCtx = context;
        getRequestQueue();
    }

    public RequestQueue getRequestQueue(){

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context){
        if (mInstance == null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }
}
