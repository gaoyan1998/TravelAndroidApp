package com.ikiler.travel;

import android.content.pm.PackageManager;
import android.os.Bundle;
 import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class DiTuActivity extends AppCompatActivity {

    public LocationClient locationClient;
    public TextView positionText;
    MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFristLocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   getSupportActionBar().hide();
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_di_tu);
        positionText = (TextView) findViewById(R.id.position_text_view);
        mapView = (MapView) findViewById(R.id.bmapview);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(DiTuActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(DiTuActivity.this, android.Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(DiTuActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(DiTuActivity.this,permissions,1);
        }else {
            requestLoction();
        }
    }

    public void requestLoction(){
        initLocation();
        locationClient.start();
    }

    public void initLocation(){
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setScanSpan(5000);
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        locationClient.setLocOption(locationClientOption);
    }

    public void navigateTo(BDLocation bdLocation){
      if(isFristLocate){
             LatLng cenpt = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
//定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(cenpt)
                    .zoom(18)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//改变地图状态
            baiduMap.setMapStatus(mMapStatusUpdate);

       }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(bdLocation.getLatitude());
        locationBuilder.longitude(bdLocation.getLongitude());

        MyLocationData myLocationData = locationBuilder.build();

        baiduMap.setMyLocationData(myLocationData);
    }

    public class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("维度：").append(bdLocation.getLatitude()).append("\n");
            stringBuilder.append("经线：").append(bdLocation.getLongitude()).append("\n");
            stringBuilder.append("国家：").append(bdLocation.getCountry()).append("\n");
            stringBuilder.append("省：").append(bdLocation.getProvince()).append("\n");
            stringBuilder.append("市：").append(bdLocation.getCity()).append("\n");
            stringBuilder.append("区：").append(bdLocation.getDistrict()).append("\n");
            stringBuilder.append("街道：").append(bdLocation.getStreet()).append("\n");
            stringBuilder.append("定位方式：");
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation){
                stringBuilder.append("GPS");
            }else {
                stringBuilder.append("网络");
            }
            positionText.setText(stringBuilder);
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(bdLocation);
            }
        }
    }

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
        locationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }}
