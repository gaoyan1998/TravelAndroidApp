package com.ikiler.travel.Model;

import com.ikiler.travel.Base.BaseLiveData;
import com.ikiler.travel.Model.bean.Food;

public class FoodLiveDataModel extends BaseLiveData<Food> {

//    private MutableLiveData<Food> mutableLiveData;
//    private MutableLiveData<List<Food>> mutableLiveDatas;
//
    private static FoodLiveDataModel model;

    public static FoodLiveDataModel instance() {
        if (null == model)
            model = new FoodLiveDataModel();
        return model;
    }
//
//    public MutableLiveData<Food> getMutableLiveData() {
//        if (mutableLiveData == null)
//            mutableLiveData = new MutableLiveData<>();
//        return mutableLiveData;
//    }
//
//    public MutableLiveData<List<Food>> getMutableLiveDatas() {
//        if (mutableLiveDatas == null)
//            mutableLiveDatas = new MutableLiveData<>();
//        return mutableLiveDatas;
//    }
}
