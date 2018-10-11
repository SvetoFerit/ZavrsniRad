package com.example.sveto.zavrsnirad.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;


import com.example.sveto.zavrsnirad.connection.Config;
import com.example.sveto.zavrsnirad.connection.FitbitInterface;

import com.example.sveto.zavrsnirad.models.FitbitParameters;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FitbitUtils {

    public static void sync(final Context context, final TextViewSyncCallback TextViewSyncCallback) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = df.format(Calendar.getInstance().getTime());

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.fitbit.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        FitbitInterface fitbitInterface = retrofit.create(FitbitInterface.class);
        Call<FitbitParameters> call = fitbitInterface.getFitbitParameters(date,Config.TOKEN);
        call.enqueue(new Callback<FitbitParameters>() {
            @Override
            public void onResponse( Call<FitbitParameters>  call, retrofit2.Response<FitbitParameters> response) {
                if (response.isSuccessful()) {
                    FitbitParameters.STEPS = response.body().getSummary().getSteps();
                    FitbitParameters.CALORIES = response.body().getSummary().getCaloriesOut();
                    Log.d("steps",String.valueOf(response.body().getSummary().getSteps()));

                    TextViewSyncCallback.textViewCallback();
                }

            }

            @Override
            public void onFailure(Call<FitbitParameters> call, Throwable t) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });


    }


    public static boolean connectedToInternet(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;


        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                if (netInfo.isConnected()) {
                    haveConnectedWifi = true;
                }
            }
            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (netInfo.isConnected()) {
                    haveConnectedMobile = true;
                }
            }
        } catch (NullPointerException e) {
            //Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}

