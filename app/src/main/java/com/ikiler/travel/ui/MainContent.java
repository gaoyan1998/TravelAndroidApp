package com.ikiler.travel.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Model.bean.WeatherBean;
import com.ikiler.travel.R;
import com.ikiler.travel.ui.CustomView.HeaderWeather;
import com.ikiler.travel.ui.fragement.FeedFragment;
import com.ikiler.travel.ui.fragement.PersonalFragment;
import com.ikiler.travel.ui.fragement.TrainTicketFragment;
import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.util.LiveBus;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainContent extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;


    private HeaderWeather headerWeather;
    private FeedFragment feedFragment;
    private PersonalFragment personalFragment;
    private TrainTicketFragment trainTicketFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        inits();
    }

    private void inits() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        headerWeather = navigationView.getHeaderView(0).findViewById(R.id.dynamic_header);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        switchFragement(FeedFragment.instance());
        LiveBus.getDefault().subscribe("Weather",WeatherBean.class).observe(this, new Observer<WeatherBean>() {
            @Override
            public void onChanged(WeatherBean weatherBean) {
                headerWeather.showWeather(weatherBean.getWeather());
            }
        });
        String city = getMmkv().decodeString("city");
        if (TextUtils.isEmpty(city)){
            city = "auto_ip";
        }
        APIconfig.refershWeather(city);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() <= 0)//这里是取出我们返回栈存在Fragment的个数
                super.onBackPressed();
            else
                getSupportFragmentManager().popBackStack();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(getApplicationContext(), FoodListActivity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(getApplicationContext(), SpotActivity.class));
        } else if (id == R.id.nav_map) {
            startActivity(new Intent(getApplicationContext(), MapActivity.class));
        } else if (id == R.id.nav_music) {
            startActivity(new Intent(getApplicationContext(), MusicPlayActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        transaction.commitAllowingStateLoss();
        return true;
    }

    /**
     * 管理fragement
     *
     * @param fragment 目标feagement
     */
    private void switchFragement(Fragment fragment) {
        boolean flage = false;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        for (Fragment item: fragments) {
            if (item != fragment) {
                transaction.hide(item);
            } else {
                flage = true;
            }
        }
        if (!flage) {
            transaction.add(R.id.content, fragment);
        }
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (null == feedFragment)
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
}
