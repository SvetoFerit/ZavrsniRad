package com.example.sveto.zavrsnirad.connection;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


import android.view.View;

import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


import com.example.sveto.zavrsnirad.CalculationActivity;
import com.example.sveto.zavrsnirad.R;
import com.example.sveto.zavrsnirad.utils.FitbitUtils;
import com.example.sveto.zavrsnirad.utils.TextViewSyncCallback;
import com.example.sveto.zavrsnirad.models.FitbitParameters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectFitbitActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.btn_ConnectFitbit)
    Button btnConnectFitbit;

    public static String EXTRA_STEPS = "extra_steps";
    public static String EXTRA_CALORIES = "extra_calories";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_fitbit);
        ButterKnife.bind(this);
        btnConnectFitbit.setOnClickListener(this);
        getFitbitParameters();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ConnectFitbit: {
                if (FitbitUtils.connectedToInternet(this)) {
                    final ProgressDialog progressBar = new ProgressDialog(ConnectFitbitActivity.this);
                    progressBar.setMessage("Connecting....");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.dismiss();
                            Toast.makeText(ConnectFitbitActivity.this, "You are successfully connected", Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            connectToFitbit();
                        }
                    }, 4000);
                } else {
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
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

    public void getFitbitParameters() {

        FitbitUtils.sync(this, new TextViewSyncCallback() {
            @Override
            public void textViewCallback() {
            }
        });

    }
}


