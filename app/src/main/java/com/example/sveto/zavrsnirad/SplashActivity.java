package com.example.sveto.zavrsnirad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.SplashLogo)
    ImageView splashLogo;
    @BindView(R.id.SplashFitbitLogo)
    ImageView splashFitbitLogo;
    @BindView(R.id.tvSplash)
    TextView tvSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        startAnimation();

    }

    private void startAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animationsplash);
        splashLogo.startAnimation(animation);
        splashFitbitLogo.startAnimation(animation);
        tvSplash.startAnimation(animation);

        Thread timer = new Thread() {

            @Override
            public void run() {

                try {
                    sleep(4000);
                    startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        timer.start();
    }


}

