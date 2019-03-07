package com.ikiler.travel.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.R;

public class Splash extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AutoLogin();
            }
        },3000);
    }

    private void AutoLogin(){
        if (null != getUser()){
            startActivity(new Intent(getApplicationContext(),MainContent.class));
        }else {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        finish();
    }
}
