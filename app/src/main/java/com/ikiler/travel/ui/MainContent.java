package com.ikiler.travel.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.util.Log;

import com.google.android.material.navigation.NavigationView;
import com.ikiler.travel.APIconfig;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Model.bean.WeatherBean;
import com.ikiler.travel.MainActivity;
import com.ikiler.travel.R;
import com.ikiler.travel.ui.Food.FoodListActivity;
import com.ikiler.travel.ui.Spot.SpotActivity;
import com.ikiler.travel.ui.fragement.FeedFragment;
import com.ikiler.travel.ui.fragement.PersonalFragment;
import com.ikiler.travel.ui.fragement.TrainTicketFragment;
import com.ikiler.travel.ui.weather.BaseWeatherType;
import com.ikiler.travel.ui.weather.DynamicWeatherView;
import com.ikiler.travel.ui.weather.dynamic.FogType;
import com.ikiler.travel.ui.weather.dynamic.HailType;
import com.ikiler.travel.ui.weather.dynamic.HazeType;
import com.ikiler.travel.ui.weather.dynamic.OvercastType;
import com.ikiler.travel.ui.weather.dynamic.RainType;
import com.ikiler.travel.ui.weather.dynamic.SandstormType;
import com.ikiler.travel.ui.weather.dynamic.ShortWeatherInfo;
import com.ikiler.travel.ui.weather.dynamic.SnowType;
import com.ikiler.travel.ui.weather.dynamic.SunnyType;
import com.ikiler.travel.util.GsonUtil;
import com.ikiler.travel.util.OkHttpUtil;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.TextView;

import org.feezu.liuli.timeselector.TimeSelector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainContent extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DynamicWeatherView dynamicWeatherView;
    private TextView degree,loaction,weatherInfo,updataTime;
    private WeatherBean.HeWeather6Bean weatherBean;
    private FeedFragment feedFragment;
    private PersonalFragment personalFragment;
    private TrainTicketFragment trainTicketFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (null ==feedFragment)
                        feedFragment = new FeedFragment();
                    switchFragement(feedFragment);
                    return true;
                case R.id.navigation_train:
                    if (null == trainTicketFragment)
                        trainTicketFragment = new TrainTicketFragment();
                    switchFragement(trainTicketFragment);
                    return true;
                case R.id.navigation_person:
                    if (null == personalFragment)
                        personalFragment = new PersonalFragment();
                    switchFragement(personalFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        dynamicWeatherView = navigationView.getHeaderView(0).findViewById(R.id.dynamic_header);
        degree = navigationView.getHeaderView(0).findViewById(R.id.degree_text);
        loaction = navigationView.getHeaderView(0).findViewById(R.id.location);
        weatherInfo = navigationView.getHeaderView(0).findViewById(R.id.weather_info_text);
        updataTime = navigationView.getHeaderView(0).findViewById(R.id.update_time_text);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initWeather();
        switchFragement(FeedFragment.instance());

        String fromDate = new SimpleDateFormat("yyyy-MM-dd")
                .format(new Date());
    }

    private void initWeather() {
        Map<String,String> params = new HashMap();
        params.put("location","auto_ip");
        params.put("key","b2e77245d3424482ad0984ccb82f4b99");
        OkHttpUtil.post(APIconfig.WeatherUrl, params, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                Log.e("ml",data);
                if (flage){
                    WeatherBean weatherBean1 = GsonUtil.GsonToBean(data,WeatherBean.class);
                    if (weatherBean1.getWeather().getStatus().equals("ok")){
                        weatherBean = weatherBean1.getWeather();
                        showWeather();
                    }
                }
            }
        });
    }

    private void showWeather() {
        degree.setText(weatherBean.getNow().getTmp());
        loaction.setText(weatherBean.getBasic().getLocation());
        weatherInfo.setText(weatherBean.getNow().getCond_txt());
        updataTime.setText("更新时间："+weatherBean.getUpdate().getLoc());
//        final String[] items = new String[]{"晴（白天）", "晴（夜晚）", "多云", "阴", "雨", "雨夹雪",
//                "雪", "冰雹", "雾", "雾霾", "沙尘暴"};

        switchDynamicWeather(weatherBean.getNow().getCond_code());

//        AlertDialog.Builder builder = new AlertDialog.Builder(MainContent.this);
//        builder.setTitle("动态天气预览");
//        builder.setCancelable(true);
//        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.create().show();

    }
    private void switchDynamicWeather(String which) {
        ShortWeatherInfo info = new ShortWeatherInfo();
        info.setCode("100");
        info.setWindSpeed("11");
        BaseWeatherType type;
        switch (which) {
            case "100"://情白天
                info.setSunrise("00:01");
                info.setSunset("23:59");
                info.setMoonrise("00:00");
                info.setMoonset("00:01");
                type = new SunnyType(MainContent.this, info);
                break;
            case "99"://晴（夜晚）
                info.setSunrise("00:00");
                info.setSunset("00:01");
                info.setMoonrise("00:01");
                info.setMoonset("23:59");
                type = new SunnyType(getActivity(), info);
                break;
            case "101"://多云
                info.setSunrise("00:01");
                info.setSunset("23:59");
                info.setMoonrise("00:00");
                info.setMoonset("00:01");
                SunnyType sunnyType = new SunnyType(getActivity(), info);
                sunnyType.setCloud(true);
                type = sunnyType;
                break;
            case "104"://阴
                type = new OvercastType(getActivity(), info);
                break;
            case "300"://雨:
                RainType rainType = new RainType(getActivity(), RainType.RAIN_LEVEL_2, RainType.WIND_LEVEL_2);
                rainType.setFlashing(true);
                type = rainType;
                break;
            case "404"://雨夹雪
                RainType rainSnowType = new RainType(getActivity(), RainType.RAIN_LEVEL_1, RainType.WIND_LEVEL_1);
                rainSnowType.setSnowing(true);
                type = rainSnowType;
                break;
            case "400"://雪
                type = new SnowType(getActivity(), SnowType.SNOW_LEVEL_2);
                break;
            case "410"://冰雹大雪暴雪
                type = new HailType(getActivity());
                break;
            case "501"://雾
                type = new FogType(getActivity());
                break;
            case "502"://雾霾
                type = new HazeType(getActivity());
                break;
            case "507"://沙尘暴
                type = new SandstormType(getActivity());
                break;
            default:
                type = new SunnyType(getActivity(), info);
        }
        dynamicWeatherView.setType(type);

    }

    private Context getActivity() {
        return MainContent.this;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() <= 0)//这里是取出我们返回栈存在Fragment的个数
                super.onBackPressed();
            else
                getSupportFragmentManager().popBackStack();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_content, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(getApplicationContext(), FoodListActivity.class));
//            transaction.replace(R.id.content, Weather.newInstance());
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(getApplicationContext(), SpotActivity.class));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        transaction.commitAllowingStateLoss();
        return true;
    }

    /**
     * 管理fragement
     * @param fragment 目标feagement
     * */
    private void switchFragement(Fragment fragment){
        boolean flage = false;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        for (Fragment item:fragments){
            if (item != fragment){
                transaction.hide(item);
            }else {
                flage = true;
            }
        }
        if (!flage){
            transaction.add(R.id.content,fragment);
        }
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
    }
}
