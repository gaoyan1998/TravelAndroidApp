package com.ikiler.travel.Model;

import com.ikiler.travel.Model.bean.Food;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoodViewModel extends ViewModel {

    private MutableLiveData<Food> mutableLiveData;
    private MutableLiveData<List<Food>> mutableLiveDatas;

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
