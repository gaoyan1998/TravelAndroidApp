package com.ikiler.travel.Base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.ikiler.travel.Model.bean.User;
import com.ikiler.travel.ui.Food.FoodEditActivity;
import com.tencent.mmkv.MMKV;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private MMKV mmkv;
    protected ProgressDialog dialog;

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
    public void showDialog(String msg, final CallBack callBack){
        AlertDialog alertDialog = new AlertDialog.Builder(BaseActivity.this)
                .setMessage(msg)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.calBack(true);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.calBack(false);
                    }
                }).create();
        alertDialog.show();
    }
    public void showNetProgress(){
        if (dialog == null){
            dialog = new ProgressDialog(BaseActivity.this);
            dialog.setMessage("正在请求中......");
            dialog.setCancelable(false);
        }
        if (dialog.isShowing()){
            dialog.cancel();
        }
        dialog.show();
    }
    public void cancelNetDialog(){
        if (dialog!=null && dialog.isShowing()){
            dialog.cancel();
        }
    }
    public MMKV getMmkv() {
        return mmkv;
    }

    protected interface CallBack{
        void calBack(boolean flage);
    }
}
