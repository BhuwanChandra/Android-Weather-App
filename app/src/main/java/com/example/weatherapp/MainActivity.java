package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result;
    String z;
    RequestQueue requestQueue;
    InputMethodManager mgr;

    String baseURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=f094315a16fabfa06fb7acffcb20701c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        city = findViewById(R.id.editText);
        result = findViewById(R.id.result);

        requestQueue = MySingleton.getInstance(this).getRequestQueue();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                z = city.getText().toString();
                if (TextUtils.isEmpty(z)){
                    city.setError("Empty User Input");
                }

                String myURL = baseURL + z + API;
                Log.i("URL","URL: " + myURL);

                sendAPIRequest(myURL);
            }
        });
    }

    private void sendAPIRequest(String myURL){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, myURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String info = response.getString("weather");

                    mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(city.getWindowToken(),0);

                    JSONArray ar = new JSONArray(info);

                    for (int i = 0; i<ar.length(); i++){
                        JSONObject parObj = ar.getJSONObject(i);
                        String main = parObj.getString("main");

                        Log.i("callApi"," " + main);
                        result.setText(main);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error",error.toString());
            }
        });

        requestQueue.add(request);
    }
}
