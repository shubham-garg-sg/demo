package com.example.demo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class newActivity extends AppCompatActivity {

    private TextView te;
    private RequestQueue mQueue;
    public String ctr;

    String URL_DATA = "https://covid-19-data.p.rapidapi.com/country?name=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        te = findViewById(R.id.te);

        ctr = getIntent().getStringExtra("n");
        //String new_url = URL_DATA+country;

        mQueue = Volley.newRequestQueue(this);

        jsonParse();

    }

    private void jsonParse() {
        String new_url = URL_DATA + ctr;

        StringRequest request = new StringRequest(Request.Method.GET, new_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    int i=0;
                    JSONObject cntry = jsonArray.getJSONObject(i);
                    String country = cntry.getString("country");
                    int confirmed = cntry.getInt("confirmed");
                    int recovered = cntry.getInt("recovered");
                    int deaths = cntry.getInt("deaths");

                    Log.d("taggggggggggggggg", String.valueOf(confirmed));
                    te.setText(country + String.valueOf(confirmed) + String.valueOf(recovered) + String.valueOf(deaths));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("x-rapidapi-host", "covid-19-data.p.rapidapi.com");
                params.put("x-rapidapi-key", "24383480d1msh8dd6a556e058ee6p13b948jsn0a53a01d32d3");
                return params;
            }
        };
        mQueue.add(request);
    }
}