package com.ikiler.travel.ui.Spot;

import android.content.Intent;
import android.os.Bundle;

import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.Model.bean.Food;
import com.ikiler.travel.ui.Food.FoodListActivity;

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
        model.getMutableLiveData().setValue(item);
        startActivity(new Intent(getApplicationContext(), SpotEditActivity.class));
    }

    @Override
    protected void itemDelete(Food food, CallBack callBack) {
        APIconfig.deleteSpot(food.getId(), callBack);
    }
}
