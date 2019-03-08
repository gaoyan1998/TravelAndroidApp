package com.ikiler.travel.ui.Spot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ikiler.travel.APIconfig;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.R;
import com.ikiler.travel.ui.Food.FoodEditActivity;
import com.ikiler.travel.util.HttpConfig;

public class SpotEditActivity extends FoodEditActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                        APIconfig.refershFoods();
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
