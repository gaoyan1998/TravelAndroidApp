package com.ikiler.travel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private long exitFirstTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
    }
    public void goMeishi(View view)
    {
        Intent intent = new Intent(this,MeishiActivity.class);
        startActivity(intent);
    }
    public void goBiji(View view)
    {
        Intent intent = new Intent(this,BijiActivity.class);
        startActivity(intent);
    }
    public void goDianhua(View view)
    {
        Intent intent = new Intent(this,DianHuaActivity.class);
        startActivity(intent);
    }
    public void goTianqi(View view)
    {
        Intent intent = new Intent(this,TianQiActivity.class);
        startActivity(intent);
    }
    public void goDitu(View view)
    {
        Intent intent = new Intent(this,DiTuActivity.class);
        startActivity(intent);
    }
    public void goJiandian(View view)
    {
        Intent intent = new Intent(this,JianDianActivity.class);
        startActivity(intent);
    }
}
