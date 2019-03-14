package com.ikiler.travel.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.Model.bean.Food;

public class SpotActivity extends FoodListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_food_list);
    }

    @Override
    protected void refersh() {
        APIconfig.refershSpots();
    }

    @Override
    protected void itemClick(Food item) {
        Log.e("ml","跳转spot");
        model.getMutableLiveData().setValue(item);
        startActivity(new Intent(getApplicationContext(), SpotEditActivity.class));
    }

    @Override
    protected void itemDelete(Food food, CallBack callBack) {
        APIconfig.deleteSpot(food.getId(), callBack);
    }

    @Override
    public void onAddClicked() {
        model.getMutableLiveData().setValue(null);
        startActivity(new Intent(getApplicationContext(), SpotEditActivity.class));
    }
}
