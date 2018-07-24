package com.example.sveto.zavrsnirad.connection;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


import com.example.sveto.zavrsnirad.CalculationActivity;
import com.example.sveto.zavrsnirad.R;
import com.example.sveto.zavrsnirad.Utils.FitbitUtils;
import com.example.sveto.zavrsnirad.Utils.TextViewSyncCallback;
import com.example.sveto.zavrsnirad.models.FitbitParameters;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ConnectFitbitActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.btn_ConnectFitbit)
    Button btnConnectFitbit;

    @BindView(R.id.tvNet)
    TextView tvNet;

    public static String EXTRA_STEPS = "extra_steps";
    public static String EXTRA_CALORIES = "extra_calories";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_fitbit);
        ButterKnife.bind(this);
        btnConnectFitbit.setOnClickListener(this);

        Log.e("create", "crataeConnect");

        getFitbibtParameters();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ConnectFitbit: {
                if (connectedToInternet()) {
                    connectToFitbit();
                }
            }

        }
    }


    public void connectToFitbit() {

        Intent intent = new Intent(ConnectFitbitActivity.this, CalculationActivity.class);
        intent.putExtra(EXTRA_STEPS, FitbitParameters.STEPS);
        intent.putExtra(EXTRA_CALORIES, FitbitParameters.CALORIES);
        startActivity(intent);
    }

    public boolean connectedToInternet() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void getFitbibtParameters() {

        FitbitUtils.sync(this, new TextViewSyncCallback() {
            @Override
            public void textViewCallback() {
                String data = "";
                data += "Score: " + FitbitParameters.STEPS +
                        "Calories : " + FitbitParameters.CALORIES;

                tvNet.setText(data);
            }
        });

    }
}


