package com.ikiler.travel.util;

import android.app.Application;

import com.ikiler.travel.Model.User;
import com.tencent.mmkv.MMKV;

public class App extends Application {
    User user;

    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
