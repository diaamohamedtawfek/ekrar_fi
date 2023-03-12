package com.otex.ekrar;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import com.otex.ekrar.Home.Home;
import com.otex.ekrar.startApp.LoginActivity;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
//    UserSharedPreferencesManager userSharedPreferencesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetLocal("en");
        setContentView(R.layout.activity_main);

//         userSharedPreferencesManager= UserSharedPreferencesManager.getInstance(this);;
        Thread mythread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(SPLASH_DISPLAY_LENGTH);
                    if (!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                        finish();
                    }

//                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mythread.start();
    }


    public void SetLocal(String lang)
    {
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(locale);
            getApplicationContext().createConfigurationContext(conf);
        }

        DisplayMetrics dm = res.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            conf.setLocales(new LocaleList(locale));
        } else {
            conf.locale = locale;
        }
        res.updateConfiguration(conf, dm);

    }
}
