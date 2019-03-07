package com.ikiler.travel.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.R;

import java.util.List;

public class Splash extends BaseActivity {

     private static final int RC_CAMERA_AND_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        methodRequiresTwoPermission();
    }

    private void AutoLogin(){
        if (null != getUser()){
            startActivity(new Intent(getApplicationContext(),MainContent.class));
        }else {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        finish();
    }


    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            //如果已经获取权限，在这里做一些事情
            // ...
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AutoLogin();
                }
            },3000);

        } else {
            // Do not have permissions, request them now
            //如果没有获取到权限，在这里获取权限，其中RC_CAMERA_AND_LOCATION是自己定义的一个唯一标识int值
            EasyPermissions.requestPermissions(this, "！！！！！！！",
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        //将结果传入EasyPermissions中
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
