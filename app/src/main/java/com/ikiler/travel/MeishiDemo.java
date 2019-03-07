package com.ikiler.travel;

import android.content.Intent;
import android.os.Bundle;
 import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MeishiDemo extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meishi_demo);
//        getSupportActionBar().hide();
        imageView = (ImageView) findViewById(R.id.meishitp);
        textView = (TextView) findViewById(R.id.meishimz);
        Intent intent = getIntent();
        int position = intent.getIntExtra("meishi_info",0);
        imageView.setImageResource(MeishiActivity.meishi_tu[position]);
        textView.setText(getResources().getStringArray(R.array.ms_info)[position]);

    }

    }


