package com.ikiler.travel.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Bean.User;
import com.ikiler.travel.Control.UserManager;
import com.ikiler.travel.MainActivity;
import com.ikiler.travel.R;
import com.ikiler.travel.util.App;
import com.ikiler.travel.util.AppUtils;

public class LoginActivity extends BaseActivity {

    private EditText editText_password,editText_phone;
    private ImageView imageView_show_password;
    private SharedPreferences.Editor editor;
    private CheckBox rember_pwd_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_phone = (EditText) findViewById(R.id.editText_phone);
        imageView_show_password = (ImageView) findViewById(R.id.imageView_show_password);
        rember_pwd_box = findViewById(R.id.rember_pwd);
    }

    public void forgetPasswordClick(View view){
        startActivity(new Intent(this,Register.class));
    }

    private boolean showPassword = false;

    /**
     * 显示或隐藏密码
     * @param view
     */
    public void showHidePasswordClick(View view){
        if (showPassword){
            imageView_show_password.setSelected(true);
            editText_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showPassword = false;
        }else{
            imageView_show_password.setSelected(false);
            editText_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showPassword = true;
        }
    }

    /**
     * 登录失败显示消息
     * */
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
            dialog.setMessage("用户名和密码错误");
            dialog.show();
        }
    };
    /**
     * 登录事件方法
     * @param view
     */
    public void loginClick(View view){
        AppUtils.hideInputMethod(view);
        login();
    }

    /**
     * 用户登录
     */
    private void login() {
        final String phone = editText_phone.getText().toString();
        if(TextUtils.isEmpty(phone)){
            showToast("请输入用户名");
            return;
        }
        final String password = editText_password.getText().toString();
        if(TextUtils.isEmpty(password)){
            showToast("请输入密码");
            return;
        }

        final User user = new User();
        user.setName(phone);
        user.setPwd(password);
        UserManager.Login(user, new UserManager.CallBack() {
            @Override
            public void Calback(boolean flage) {
                if (rember_pwd_box.isChecked()) {
                    getMmkv().encode("name",phone);
                    getMmkv().encode("pwd",password);
                    getMmkv().commit();
                }
                App app = (App) getApplication();
                app.setUser(user);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}

