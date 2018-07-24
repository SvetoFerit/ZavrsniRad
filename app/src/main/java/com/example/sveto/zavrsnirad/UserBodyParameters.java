package com.example.sveto.zavrsnirad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sveto.zavrsnirad.utils.PreferenceUtils;
import com.example.sveto.zavrsnirad.connection.ConnectFitbitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserBodyParameters extends AppCompatActivity {


    @BindView(R.id.etWeight)
    EditText etWeight;
    @BindView(R.id.etHeight)
    EditText etHeight;
    @BindView(R.id.btn_body_parameters)
    Button btnBodyParameters;
    @BindView(R.id.et_years)
    EditText etYears;

    private String weight, height, years;
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_body_parameters);
        ButterKnife.bind(this);


        if (PreferenceUtils.getHeight(this) != 0) {
            startActivity(new Intent(this, CalculationActivity.class));
        }

        btnBodyParameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etHeight.getText().toString().isEmpty() || etWeight.getText().toString().isEmpty() || etYears.getText().toString().isEmpty()) {
                    Toast.makeText(UserBodyParameters.this, "Please enter all the details", Toast.LENGTH_SHORT).show();

                } else if (etHeight.getText().toString().equals("0") || etWeight.getText().toString().equals("0") || etYears.getText().toString().equals("0")) {
                    Toast.makeText(UserBodyParameters.this, "Wrong entered value", Toast.LENGTH_SHORT).show();

                } else {

                    weight = etWeight.getText().toString();
                    float finalWeight = Float.parseFloat(weight);

                    height = etHeight.getText().toString();
                    float finalHeight = Float.parseFloat(height);

                    years = etYears.getText().toString();
                    int finalYears = Integer.parseInt(years);

                    PreferenceUtils.saveWeight(finalWeight, UserBodyParameters.this);
                    PreferenceUtils.saveHeight(finalHeight, UserBodyParameters.this);
                    PreferenceUtils.saveYears(finalYears, UserBodyParameters.this);
                    Log.e("counter", String.valueOf(PreferenceUtils.getCounter(UserBodyParameters.this)));
                    if (PreferenceUtils.getCounter(UserBodyParameters.this) == 0) {
                        startActivity(new Intent(UserBodyParameters.this, ConnectFitbitActivity.class));
                    } else {
                        startActivity(new Intent(UserBodyParameters.this, CalculationActivity.class));
                    }
                    counter++;
                    PreferenceUtils.saveCounter(counter, UserBodyParameters.this);
                    Log.e("counter", String.valueOf(PreferenceUtils.getCounter(UserBodyParameters.this)));
                }
            }
        });
    }
}
