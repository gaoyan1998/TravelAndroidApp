package com.ikiler.travel.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.adapter.IWEngineInitListener;
import com.baidu.mapapi.walknavi.adapter.IWRoutePlanListener;
import com.baidu.mapapi.walknavi.model.WalkRoutePlanError;
import com.baidu.mapapi.walknavi.params.WalkNaviLaunchParam;
import com.google.android.material.resources.TextAppearance;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MapActivity extends BaseActivity {

    MapView mapView;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.list)
    ListView list;

    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private SuggestionSearch mSuggestionSearch;
    private String city;
    private MyAdapter adapter = new MyAdapter();
    private LatLng currentLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_di_tu);
        ButterKnife.bind(this);
        mapView =  findViewById(R.id.bmapview);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        list.setAdapter(adapter);
        requestLoction();

    }

    public void requestLoction() {
        initLocationOption();
        initSug();
        initWalk();
    }

    private void initSug() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                        .city(city)
                        .keyword(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSuggestionSearch = SuggestionSearch.newInstance();
        OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                if (suggestionResult == null || suggestionResult.getAllSuggestions()==null){
                    return;
                }
                List<SuggestionResult.SuggestionInfo> suggestionList = suggestionResult.getAllSuggestions();
                adapter.setSuggestionList(suggestionList);
            }
        };
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);


    }

    private void initWalk() {
        // 获取导航控制类
        // 引擎初始化
        WalkNavigateHelper.getInstance().initNaviEngine(this, new IWEngineInitListener() {

            @Override
            public void engineInitSuccess() {
                //引擎初始化成功的回调
//                routeWalkPlanWithParam();
                showToast("succcess");
            }

            @Override
            public void engineInitFail() {
                //引擎初始化失败的回调
                showToast("fa");

            }
        });
    }

    /**
     * 初始化定位参数配置
     */

    private void initLocationOption() {
//定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        locationClient = new LocationClient(getApplicationContext());
//声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        MyLocationListener myLocationListener = new MyLocationListener();
//注册监听函数
        locationClient.registerLocationListener(myLocationListener);
//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("bd09ll");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000);
//可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
//可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
//可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(true);
//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
//可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
//可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
//开始定位
        locationClient.start();
    }

    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
//
//            //获取纬度信息
//            double latitude = location.getLatitude();
//            //获取经度信息
//            double longitude = location.getLongitude();
//            //获取定位精度，默认值为0.0f
//            float radius = location.getRadius();
//            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
//            String coorType = location.getCoorType();
//            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
//            int errorCode = location.getLocType();

            if (location == null || mapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
// 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            currentLat = ll;
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            city = location.getCity();
            Log.e("ml","A:"+ll.latitude);
        }
    }


    class MyAdapter extends BaseAdapter{

        List<SuggestionResult.SuggestionInfo> suggestionList = new ArrayList<>();

        public void setSuggestionList(List<SuggestionResult.SuggestionInfo> suggestionList) {
            this.suggestionList = suggestionList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return suggestionList.size();
        }

        @Override
        public Object getItem(int position) {
            return suggestionList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(30);
            textView.setTextColor(Color.BLACK);
            textView.setText(suggestionList.get(position).getKey());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //起终点位置
                    LatLng endPt = suggestionList.get(position).pt;
                    //构造WalkNaviLaunchParam
                    WalkNaviLaunchParam mParam = new WalkNaviLaunchParam().stPt(currentLat).endPt(endPt);
                    mParam.extraNaviMode(0);
                    //发起算路
                    WalkNavigateHelper.getInstance().routePlanWithParams(mParam, new IWRoutePlanListener() {
                        @Override
                        public void onRoutePlanStart() {
                            //开始算路的回调
                        }

                        @Override
                        public void onRoutePlanSuccess() {
//                            //算路成功
//                            //跳转至诱导页面
                            Log.d("ml", "onRoutePlanSuccess");
                            Intent intent = new Intent();
                            intent.setClass(MapActivity.this, WNaviGuideActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onRoutePlanFail(WalkRoutePlanError walkRoutePlanError) {
                            //算路失败的回调
                        }
                    });
                }
            });
            return textView;
        }
    }
//    public class MyLocationListener implements BDLocationListener{
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("维度：").append(bdLocation.getLatitude()).append("\n");
//            stringBuilder.append("经线：").append(bdLocation.getLongitude()).append("\n");
//            stringBuilder.append("国家：").append(bdLocation.getCountry()).append("\n");
//            stringBuilder.append("省：").append(bdLocation.getProvince()).append("\n");
//            stringBuilder.append("市：").append(bdLocation.getCity()).append("\n");
//            stringBuilder.append("区：").append(bdLocation.getDistrict()).append("\n");
//            stringBuilder.append("街道：").append(bdLocation.getStreet()).append("\n");
//            stringBuilder.append("定位方式：");
//            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation){
//                stringBuilder.append("GPS");
//            }else {
//                stringBuilder.append("网络");
//            }
//            positionText.setText(stringBuilder);
//            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
//                navigateTo(bdLocation);
//            }
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        locationClient.stop();
        mSuggestionSearch.destroy();
        baiduMap.setMyLocationEnabled(false);
    }
}
