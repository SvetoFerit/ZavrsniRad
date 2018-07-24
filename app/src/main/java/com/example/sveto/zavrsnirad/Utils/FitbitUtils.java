package com.example.sveto.zavrsnirad.Utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.example.sveto.zavrsnirad.connection.Config;
import com.example.sveto.zavrsnirad.connection.Singleton;
import com.example.sveto.zavrsnirad.models.FitbitParameters;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.sveto.zavrsnirad.connection.Config.USER_ID;

public class FitbitUtils {


    public static void sync(Context context, final TextViewSyncCallback TextViewSyncCallback) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = df.format(Calendar.getInstance().getTime());


        Log.e("date", date);

        String url = "https://api.fitbit.com/1/user/" + USER_ID + "/activities/date/" + date + ".json";

        Log.e("link for api", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.e("Json object", String.valueOf(response));
                    JSONObject obj = response.getJSONObject("summary");

                    FitbitParameters.STEPS = obj.getInt("steps");
                    FitbitParameters.CALORIES = obj.getInt("caloriesOut");

                    TextViewSyncCallback.textViewCallback();


                } catch (JSONException e) {
                    Log.e("exception", "enter");
                    Log.e("exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "response");
                Log.e("error", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Bearer " + Config.TOKEN);
                return headers;
            }
        };
        Singleton.getInstance(context).addToRequestqueue(jsonObjectRequest);

    }

}
