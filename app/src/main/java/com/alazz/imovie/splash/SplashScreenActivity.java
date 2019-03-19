package com.alazz.imovie.splash;

import android.os.Bundle;
import android.os.Handler;

import com.alazz.imovie.R;
import com.alazz.imovie.home.HomeActivity;
import com.alazz.imovie.utils.ActivityUtils;
import androidx.appcompat.app.AppCompatActivity;
import static com.alazz.imovie.utils.Constant.DELAY_SPLASH_SCREEN;


public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        startHomeActivity();

    }


    private void startHomeActivity() {

        new Handler().postDelayed(() -> ActivityUtils.onNavigateToActivity(this, HomeActivity.class, true), DELAY_SPLASH_SCREEN);
    }



}
