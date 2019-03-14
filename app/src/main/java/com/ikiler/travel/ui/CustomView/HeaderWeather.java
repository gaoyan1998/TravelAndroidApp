package com.ikiler.travel.ui.CustomView;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ikiler.travel.Model.BaseWeatherType;
import com.ikiler.travel.Model.FogType;
import com.ikiler.travel.Model.HailType;
import com.ikiler.travel.Model.HazeType;
import com.ikiler.travel.Model.OvercastType;
import com.ikiler.travel.Model.RainType;
import com.ikiler.travel.Model.SandstormType;
import com.ikiler.travel.Model.ShortWeatherInfo;
import com.ikiler.travel.Model.SnowType;
import com.ikiler.travel.Model.SunnyType;
import com.ikiler.travel.Model.bean.WeatherBean;
import com.ikiler.travel.R;
import com.ikiler.travel.ui.CitySelectActivity;
import androidx.annotation.Nullable;


public class HeaderWeather extends LinearLayout {

    View RootView;
    DynamicWeatherView dynamicHeader;
    TextView degreeText;
    TextView weatherInfoText;
    TextView updateTimeText;
    TextView location;
    RelativeLayout weatherNowLayout;

    public HeaderWeather(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RootView = layoutInflater.inflate(R.layout.header_layout, null);
        addView(RootView);
        dynamicHeader = RootView.findViewById(R.id.dynamic_header);
        degreeText = RootView.findViewById(R.id.degree_text);
        weatherInfoText = RootView.findViewById(R.id.weather_info_text);
        updateTimeText = RootView.findViewById(R.id.update_time_text);
        location = RootView.findViewById(R.id.location);
        weatherNowLayout = RootView.findViewById(R.id.weather_now_layout);
        location.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), CitySelectActivity.class));
            }
        });
    }
    public void showWeather(WeatherBean.HeWeather6Bean weatherBean) {
        degreeText.setText(weatherBean.getNow().getTmp());
        location.setText(weatherBean.getBasic().getLocation());
        weatherInfoText.setText(weatherBean.getNow().getCond_txt());
        updateTimeText.setText("更新时间："+weatherBean.getUpdate().getLoc());
        showWeatherAnimal(weatherBean.getStatus());

    }
    private void showWeatherAnimal(String which) {
        ShortWeatherInfo info = new ShortWeatherInfo();
        info.setCode("100");
        info.setWindSpeed("11");
        BaseWeatherType type;
        switch (which) {
            case "100"://情白天
                info.setSunrise("00:01");
                info.setSunset("23:59");
                info.setMoonrise("00:00");
                info.setMoonset("00:01");
                type = new SunnyType(getContext(), info);
                break;
            case "99"://晴（夜晚）
                info.setSunrise("00:00");
                info.setSunset("00:01");
                info.setMoonrise("00:01");
                info.setMoonset("23:59");
                type = new SunnyType(getContext(), info);
                break;
            case "101"://多云
                info.setSunrise("00:01");
                info.setSunset("23:59");
                info.setMoonrise("00:00");
                info.setMoonset("00:01");
                SunnyType sunnyType = new SunnyType(getContext(), info);
                sunnyType.setCloud(true);
                type = sunnyType;
                break;
            case "104"://阴
                type = new OvercastType(getContext(), info);
                break;
            case "300"://雨:
                RainType rainType = new RainType(getContext(), RainType.RAIN_LEVEL_2, RainType.WIND_LEVEL_2);
                rainType.setFlashing(true);
                type = rainType;
                break;
            case "404"://雨夹雪
                RainType rainSnowType = new RainType(getContext(), RainType.RAIN_LEVEL_1, RainType.WIND_LEVEL_1);
                rainSnowType.setSnowing(true);
                type = rainSnowType;
                break;
            case "400"://雪
                type = new SnowType(getContext(), SnowType.SNOW_LEVEL_2);
                break;
            case "410"://冰雹大雪暴雪
                type = new HailType(getContext());
                break;
            case "501"://雾
                type = new FogType(getContext());
                break;
            case "502"://雾霾
                type = new HazeType(getContext());
                break;
            case "507"://沙尘暴
                type = new SandstormType(getContext());
                break;
            default:
                info.setSunrise("00:01");
                info.setSunset("23:59");
                info.setMoonrise("00:00");
                info.setMoonset("00:01");
                type = new SunnyType(getContext(), info);
        }
        dynamicHeader.setType(type);

    }
}
