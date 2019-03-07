package com.ikiler.travel.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ikiler.travel.APIconfig;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Model.bean.Code;
import com.ikiler.travel.Model.bean.User;
import com.ikiler.travel.R;
import com.ikiler.travel.util.OkHttpUtil;
import com.ikiler.travel.util.AppUtils;
import com.ikiler.travel.util.GsonUtil;
import com.ikiler.travel.util.HttpConfig;

public class Register extends BaseActivity {
    private EditText editText_Email,editText_validate_number,editText_password_new,editText_name;
    private ImageView imageView_show_password;

    private boolean showPassword = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editText_Email = (EditText) findViewById(R.id.editText_phone);
        editText_validate_number = (EditText) findViewById(R.id.editText_validate_number);
        editText_password_new = (EditText) findViewById(R.id.editText_password_new);
        editText_name = findViewById(R.id.editText_name);
        imageView_show_password = (ImageView) findViewById(R.id.imageView_show_password);
    }

    public void resetPasswordClick(View view){
        AppUtils.hideInputMethod(view);
        String phone = editText_Email.getText().toString();
        if(TextUtils.isEmpty(phone)){
            showToast("请输入邮箱号码");
            return;
        }
        String name = editText_name.getText().toString();
        if (TextUtils.isEmpty(name)){
            showToast("请输入用户名");
            return;
        }
        String password = editText_password_new.getText().toString();
        if(TextUtils.isEmpty(password)){
            showToast("请输入新密码");
            return;
        }
        String validateNumber = editText_validate_number.getText().toString();
        if(TextUtils.isEmpty(validateNumber)){
            showToast("请输入验证码");
            return;
        }

        User user = new User();
        user.setId(Integer.parseInt(validateNumber));//id为验证码
        user.setEmail(phone);
        user.setPwd(password);
        user.setName(name);
        OkHttpUtil.postJsonBody(APIconfig.Register, GsonUtil.GsonString(user), new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                Code code = GsonUtil.GsonToBean(data,Code.class);
                if (!flage){
                    showToast("服务器错误");
                    return;
                }
                if (code.getCode() == 200){
                    showToast("注册成功");
                    finish();
                }else if (code.getCode() == HttpConfig.REQUEST_FORBIDEN){
                    showToast("验证码错误");
                }else if (code.getCode() == HttpConfig.USER_ALREDAY_EXITS){
                    showToast("用户已存在");
                }else {
                    showToast("未知错误");
                }
            }
        });
    }

    public void getValidateNumberClick(View view){
        AppUtils.hideInputMethod(view);
        String phone = editText_Email.getText().toString();
        if(TextUtils.isEmpty(phone)){
            showToast("请输入邮箱号码");
            return;
        }
        String name = editText_name.getText().toString();
        if (TextUtils.isEmpty(name)){
            showToast("请输入用户名");
            return;
        }
        User user = new User();
        user.setId(0);//0表示注册，发送验证码
        user.setEmail(phone);
        user.setName(name);
        user.setPwd("admin");

        OkHttpUtil.postJsonBody(APIconfig.Register, GsonUtil.GsonString(user), new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                Code code = GsonUtil.GsonToBean(data,Code.class);
                if (!flage){
                    showToast("服务器错误");
                    return;
                }
                if (code.getCode() == 200){
                    showToast("发送成功");
                }else if (code.getCode() == HttpConfig.REQUEST_FORBIDEN){
                    showToast("验证码错误");
                }else if (code.getCode() == HttpConfig.USER_ALREDAY_EXITS){
                    showToast("用户已存在");
                }else {
                    showToast("未知错误");
                }
            }
        });
    }

    public void showHidePasswordClick(View view){
        AppUtils.hideInputMethod(view);
        if (showPassword){
            imageView_show_password.setSelected(true);
            editText_password_new.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showPassword = false;
        }else{
            imageView_show_password.setSelected(false);
            editText_password_new.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showPassword = true;
        }
    }
}
