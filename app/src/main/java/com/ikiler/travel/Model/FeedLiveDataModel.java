package com.ikiler.travel.Model;

import com.ikiler.travel.Base.BaseLiveData;

public class FeedLiveDataModel extends BaseLiveData<RssItem> {

    private static FeedLiveDataModel model;

    public static FeedLiveDataModel instance() {
        if (null == model)
            model = new FeedLiveDataModel() {
            };
        return model;
    }

}
