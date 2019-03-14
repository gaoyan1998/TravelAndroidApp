package com.ikiler.travel.ui.fragement;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikiler.travel.R;
import com.ikiler.travel.Model.BaseWeatherType;
import com.ikiler.travel.ui.CustomView.DynamicWeatherView;
import com.ikiler.travel.Model.FogType;
import com.ikiler.travel.Model.HailType;
import com.ikiler.travel.Model.HazeType;
import com.ikiler.travel.Model.OvercastType;
import com.ikiler.travel.Model.RainType;
import com.ikiler.travel.Model.SandstormType;
import com.ikiler.travel.Model.ShortWeatherInfo;
import com.ikiler.travel.Model.SnowType;
import com.ikiler.travel.Model.SunnyType;


public class Weather extends Fragment {

    private DynamicWeatherView dynamicWeatherView;

    public static Weather newInstance() {
        Weather fragment = new Weather();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dynamicWeatherView = view.findViewById(R.id.dynamic);
        previewDynamicWeather();
    }

    private void previewDynamicWeather() {
        final String[] items = new String[]{"晴（白天）", "晴（夜晚）", "多云", "阴", "雨", "雨夹雪",
                "雪", "冰雹", "雾", "雾霾", "沙尘暴"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("动态天气预览");
        builder.setCancelable(true);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switchDynamicWeather(items[which]);
            }
        });
        builder.create().show();

    }
    private void switchDynamicWeather(String which) {
        ShortWeatherInfo info = new ShortWeatherInfo();
        info.setCode("100");
        info.setWindSpeed("11");
        BaseWeatherType type;
        switch (which) {
            case "晴（白天）":
                info.setSunrise("00:01");
                info.setSunset("23:59");
                info.setMoonrise("00:00");
                info.setMoonset("00:01");
                type = new SunnyType(getActivity(), info);
                break;
            case "晴（夜晚）":
                info.setSunrise("00:00");
                info.setSunset("00:01");
                info.setMoonrise("00:01");
                info.setMoonset("23:59");
                type = new SunnyType(getActivity(), info);
                break;
            case "多云":
                info.setSunrise("00:01");
                info.setSunset("23:59");
                info.setMoonrise("00:00");
                info.setMoonset("00:01");
                SunnyType sunnyType = new SunnyType(getActivity(), info);
                sunnyType.setCloud(true);
                type = sunnyType;
                break;
            case "阴":
                type = new OvercastType(getActivity(), info);
                break;
            case "雨":
                RainType rainType = new RainType(getActivity(), RainType.RAIN_LEVEL_2, RainType.WIND_LEVEL_2);
                rainType.setFlashing(true);
                type = rainType;
                break;
            case "雨夹雪":
                RainType rainSnowType = new RainType(getActivity(), RainType.RAIN_LEVEL_1, RainType.WIND_LEVEL_1);
                rainSnowType.setSnowing(true);
                type = rainSnowType;
                break;
            case "雪":
                type = new SnowType(getActivity(), SnowType.SNOW_LEVEL_2);
                break;
            case "冰雹":
                type = new HailType(getActivity());
                break;
            case "雾":
                type = new FogType(getActivity());
                break;
            case "雾霾":
                type = new HazeType(getActivity());
                break;
            case "沙尘暴":
                type = new SandstormType(getActivity());
                break;
            default:
                type = new SunnyType(getActivity(), info);
        }
        dynamicWeatherView.setType(type);

    }

}
