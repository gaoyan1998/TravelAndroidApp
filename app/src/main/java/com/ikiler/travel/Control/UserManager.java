package com.ikiler.travel.Control;

import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.Model.bean.Code;
import com.ikiler.travel.Model.bean.User;
import com.ikiler.travel.util.OkHttpUtil;
import com.ikiler.travel.util.GsonUtil;
import com.tencent.mmkv.MMKV;

public class UserManager {

    public static void Login(final User user, final CallBack callBack){
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.encode("name",user.getName());
        mmkv.encode("pwd",user.getPwd());
        OkHttpUtil.postJsonBody(APIconfig.Login, "", new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage){
                    Code code = GsonUtil.GsonToBean(data,Code.class);
                    if (code.getCode() == 200){
                        User user1 = GsonUtil.GsonToBean(code.getData(),User.class);
                        callBack.Calback(user1,"登录成功");
                    }
                    else callBack.Calback(null,"登录失败，请检查用户名密码");
                }else {
                    callBack.Calback(null,"网络连接错误");
                }
            }
        });
    }

    public interface CallBack{
        void Calback(User user, String msg);
    }
}
