package com.ikiler.travel.Base;

import com.ikiler.travel.Model.FoodLiveDataModel;
import com.ikiler.travel.Model.RssItem;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public  class BaseLiveData <T> extends ViewModel {
    private MutableLiveData<T> mutableLiveData;
    private MutableLiveData<List<T>> mutableLiveDatas;


    public MutableLiveData<T> getMutableLiveData() {
        if (mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();
        return mutableLiveData;
    }

    public MutableLiveData<List<T>> getMutableLiveDatas() {
        if (mutableLiveDatas == null)
            mutableLiveDatas = new MutableLiveData<>();
        return mutableLiveDatas;
    }
}
