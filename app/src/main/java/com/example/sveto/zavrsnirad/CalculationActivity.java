package com.example.sveto.zavrsnirad;

import android.app.ProgressDialog;

import android.content.Intent;

import android.os.Bundle;

import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sveto.zavrsnirad.utils.FitbitUtils;
import com.example.sveto.zavrsnirad.utils.PreferenceUtils;
import com.example.sveto.zavrsnirad.utils.TextViewSyncCallback;
import com.example.sveto.zavrsnirad.connection.ConnectFitbitActivity;
import com.example.sveto.zavrsnirad.models.FitbitParameters;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CalculationActivity extends AppCompatActivity {

    @BindView(R.id.tv_recommended_calories)
    TextView tvRecommendedCalories;
    @BindView(R.id.tv_recommended_kilometers)
    TextView tvRecommendedKilometers;
    @BindView(R.id.tv_recommended_steps)
    TextView tvRecommendedSteps;
    @BindView(R.id.tv_current_calories)
    TextView tvCurrentCalories;
    @BindView(R.id.tv_current_kilometers)
    TextView tvCurrentKilometers;
    @BindView(R.id.tv_current_steps)
    TextView tvCurrentSteps;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private View navHeader;
    private TextView txtName, txtWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        ButterKnife.bind(this);


        navHeader = navView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.name);
        txtName = navHeader.findViewById(R.id.name);
        txtWebsite = navHeader.findViewById(R.id.website);

        loadNavHeader();
        setUpNavigationView();

        calculation();
    }

    private void loadNavHeader() {
        txtName.setText("Svetozar Radić");
        txtWebsite.setText("sveto1997@gmail.com");
    }


    private void setUpNavigationView() {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_sync:
                        final ProgressDialog progressBar = new ProgressDialog(CalculationActivity.this);
                        progressBar.setMessage("Syncing....");
                        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressBar.setProgress(3);
                        progressBar.show();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.dismiss();
                                Toast.makeText(CalculationActivity.this, "You are successfully synced", Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawers();
                                syncFitbit();
                            }
                        }, 3000);

                        break;
                    case R.id.nav_set_weight_and_height:
                        PreferenceUtils.clearHeightPreference(CalculationActivity.this);
                        startActivity(new Intent(CalculationActivity.this, UserBodyParameters.class));
                        break;
                    case R.id.nav_logout:
                        PreferenceUtils.clearEmailPreference(CalculationActivity.this);
                        startActivity(new Intent(CalculationActivity.this, RegisterActivity.class));
                        return true;

                }
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
    }


    public void calculation() {

        AlertDialog.Builder alert = new AlertDialog.Builder(CalculationActivity.this);
        alert.setTitle("Pažnja!");
        alert.setPositiveButton("OK", null);

        float weight = PreferenceUtils.getWeight(this);
        float height = PreferenceUtils.getHeight(this);
        int years = PreferenceUtils.getYears(this);
        float BMI = weight / (((height) / 100) * ((height) / 100));

        if (BMI > 25) {

            alert.setMessage(R.string.pazi_na_tezinu);
            alert.show();

            int recommended_calories = (int) (10 * weight + 6.25 * height - 5 * years + 5 + 700);
            float recommended_kilometers = 0.414f * (height / 100000) * 15000;
            tvRecommendedCalories.setText(String.valueOf(recommended_calories));
            tvRecommendedSteps.setText("15000");
            tvRecommendedKilometers.setText(String.format(Locale.getDefault(), "%.3f", recommended_kilometers));
        } else if (BMI < 18.5) {
            alert.setMessage(R.string.pazi_na_tezinu);
            alert.show();

            int recommended_calories = (int) (10 * weight + 6.25 * height - 5 * years + 5);
            float recommended_kilometers = 0.414f * (height / 100000) * 20000;
            tvRecommendedCalories.setText(String.valueOf(recommended_calories));
            tvRecommendedSteps.setText("2000");
            tvRecommendedKilometers.setText(String.format(Locale.getDefault(), "%.3f", recommended_kilometers));
        } else {
            alert.setMessage(R.string.tezine_je_dobra);
            alert.show();

            int recommended_calories = (int) (10 * weight + 6.25 * height - 5 * years + 5 + 400);
            float recommended_kilometers = 0.414f * (height / 100000) * 10000;

            tvRecommendedCalories.setText(String.valueOf(recommended_calories));
            tvRecommendedSteps.setText("10000");
            tvRecommendedKilometers.setText(String.format(Locale.getDefault(), "%.3f", recommended_kilometers));
        }

        tvCurrentSteps.setText(String.valueOf(getIntent().getIntExtra(ConnectFitbitActivity.EXTRA_STEPS, 0)));
        tvCurrentCalories.setText(String.valueOf(getIntent().getIntExtra(ConnectFitbitActivity.EXTRA_CALORIES, 0)));
        tvCurrentKilometers.setText(String.format(Locale.getDefault(), "%.3f", 0.414f * (height / 100000) * getIntent().getIntExtra(ConnectFitbitActivity.EXTRA_STEPS, 0)));
    }

    public void syncFitbit() {
        FitbitUtils.sync(this, new TextViewSyncCallback() {
            @Override
            public void textViewCallback() {
                float height = PreferenceUtils.getHeight(CalculationActivity.this);

                tvCurrentCalories.setText(String.valueOf(FitbitParameters.CALORIES));
                tvCurrentSteps.setText(String.valueOf(FitbitParameters.STEPS));
                tvCurrentKilometers.setText(String.format(Locale.getDefault(), "%.3f", 0.414f * (height / 100000) * FitbitParameters.STEPS));

            }
        });

    }
}




