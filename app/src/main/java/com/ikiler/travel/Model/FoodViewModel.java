package com.ikiler.travel.Model;

import com.ikiler.travel.Model.bean.Food;

import java.util.List;

import androidx.lifecycle.MutableLiveData;


public class FoodViewModel {

    private MutableLiveData<Food> mutableLiveData;
    private MutableLiveData<List<Food>> mutableLiveDatas;

    private static FoodViewModel model;

    public static FoodViewModel instance() {
        if (null == model)
            model = new FoodViewModel();
        return model;
    }

    public MutableLiveData<Food> getMutableLiveData() {
        if (mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();
        return mutableLiveData;
    }

    public MutableLiveData<List<Food>> getMutableLiveDatas() {
        if (mutableLiveDatas == null)
            mutableLiveDatas = new MutableLiveData<>();
        return mutableLiveDatas;
    }
}
