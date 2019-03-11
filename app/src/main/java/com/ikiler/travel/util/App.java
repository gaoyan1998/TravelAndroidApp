package com.ikiler.travel.util;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.ikiler.travel.Model.bean.User;
import com.tencent.mmkv.MMKV;

public class App extends Application {
    User user;

    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
        SDKInitializer.initialize(getApplicationContext());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
