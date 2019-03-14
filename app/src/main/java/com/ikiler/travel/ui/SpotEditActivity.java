package com.ikiler.travel.ui;

import android.os.Bundle;

import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.util.HttpConfig;

public class SpotEditActivity extends FoodEditActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setTitle("景点编辑");
    }

    @Override
    protected void editItem(){
        APIconfig.editSpot(action, mFood, new CallBack() {
            @Override
            public void calBack(boolean flage, int code) {
                cancelNetDialog();
                switch (code) {
                    case HttpConfig.REQUEST_SUCCESS:
                        showToast("修改成功");
                        APIconfig.refershSpots();
                        finish();
                        break;
                    case HttpConfig.NET_ERR:
                        showToast("网络连接失败！");
                        break;
                    default:
                        showToast("出错");
                }
            }
        });
    }
}
