package com.ikiler.travel.Base;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.ikiler.travel.Model.bean.User;
import com.tencent.mmkv.MMKV;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private MMKV mmkv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mmkv = MMKV.defaultMMKV();
    }

    public User getUser() {
        User user = new User();
        String name = mmkv.decodeString("name", "");
        String pwd = mmkv.decodeString("pwd", "");
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd))
            return null;
        user.setPwd(pwd);
        user.setName(name);
        return user;
    }

    /**
     * Toast提示
     *
     * @param text
     */
    public void showToast(String text) {
        Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT).show();
    }


    public MMKV getMmkv() {
        return mmkv;
    }
}
