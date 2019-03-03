package com.ikiler.travel;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class JianDianActivity extends AppCompatActivity implements View.OnClickListener{

    ViewPager viewPager;
    String [] strings=new String[]{"黄鹤楼","东湖","博物馆","九峰公园"};
    ViewGroup viewGroup;
    TextView [] textViews;
     List<Fragment> list = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jian_dian);
//        getSupportActionBar().hide();
        viewPager =(ViewPager) findViewById(R.id.jiandian_vp);
        viewGroup =(ViewGroup) findViewById(R.id.JianDian_l);
        getData();
        FragmentManager fragmentManager = getSupportFragmentManager();//获取
        viewPager.setAdapter(new MyAdapter(fragmentManager,list));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < list.size(); i++) {
                    if(position==i){
                        textViews[i].setTextColor(Color.RED);
                    }
                    else {
                        textViews[i].setTextColor(Color.WHITE);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        intText();

        for(int i = 0;i<textViews.length;i++)
        {
            textViews[i].setOnClickListener(this);
            textViews[i].setTag(i);
        }
        }

    @Override
    public void onClick(View v) {
        //getTag  获得标签
        switch (v.getTag().toString()){
            case "0" :
                viewPager.setCurrentItem(0);
                break;

            case "1" :
                viewPager.setCurrentItem(1);
                break;

            case "2" :
                viewPager.setCurrentItem(2);
                break;

            case "3" :
                viewPager.setCurrentItem(3);
                break;
        }

    }

    public void getData(){
        JianDian_OneActivity oneFragment = new JianDian_OneActivity();
        list.add(oneFragment);
        JianDian_TwoActivity twoFragment = new JianDian_TwoActivity();
        list.add(twoFragment);
        JianDian_ThreeActivity threeFragment = new JianDian_ThreeActivity();
        list.add(threeFragment);
        JianDian_FourActivity fourFragment = new JianDian_FourActivity();
        list.add(fourFragment);

    }
    class MyAdapter extends FragmentPagerAdapter {
        private  List<Fragment> list_adpter = new ArrayList<Fragment>();

        @Override
        public Fragment getItem(int position) {
            return list_adpter.get(position);
        }

        @Override
        public int getCount() {
            return list_adpter.size();
        }

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyAdapter(FragmentManager fm,List<Fragment> list) {
            super(fm);
            this.list_adpter = list;
        }
    }
    public void intText() {
        textViews = new TextView[list.size()];
        for (int i = 0; i < textViews.length; i++) {
            TextView textView = new TextView(JianDianActivity.this);
            LinearLayout.LayoutParams paramsWeight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            paramsWeight.weight = 1;
            textView.setLayoutParams(paramsWeight);
            textView.setGravity(Gravity.CENTER);
            if (i == 0) {
                textView.setText(strings[i]);
                textView.setTextColor(Color.RED);
            } else {
                textView.setText(strings[i]);
                textView.setTextColor(Color.WHITE);
            }
            textViews[i] = textView;

            viewGroup.addView(textViews[i], new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

}
