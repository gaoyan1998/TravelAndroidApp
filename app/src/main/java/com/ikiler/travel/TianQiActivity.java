package com.ikiler.travel;

import android.os.Bundle;
 import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class TianQiActivity extends AppCompatActivity {

    TextView tianqi_paomadeng_tv;
    TextView tianqi_date_tv;
    TextView tianqi_humi_tv;
    EditText tianqi_diqu_edit;
    ImageView tianqi_sousuo_btn;
    ImageView tianqi_imageview;
    TextView tianqi_state_tv;
    TextView tianqi_feng_tv;
    TextView tianqi_ziwaixian_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tian_qi);
        tianqi_date_tv = (TextView) findViewById(R.id.tianqi_date_tv);
        tianqi_humi_tv = (TextView) findViewById(R.id.tianqi_humi_tv);
        tianqi_diqu_edit = (EditText) findViewById(R.id.tianqi_diqu_edit);
        tianqi_sousuo_btn = (ImageView) findViewById(R.id.tianqi_sousuo_btn);
        tianqi_imageview = (ImageView) findViewById(R.id.tianqi_imageview);
        tianqi_state_tv = (TextView) findViewById(R.id.tianqi_state_tv);
        tianqi_feng_tv = (TextView) findViewById(R.id.tianqi_feng_tv);
        tianqi_paomadeng_tv= (TextView) findViewById(R.id.tianqi_paomadeng_tv);
        tianqi_ziwaixian_tv = (TextView) findViewById(R.id.tianqi_ziwaixian_tv);
        tianqi_sousuo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diqu = tianqi_diqu_edit.getText().toString();
                if (!diqu.equals("") && diqu != null) {
                    updateText(diqu, new VolleyCallBack() {
                        @Override
                        public void onSuccess(int maxhumi, int minhumi, String state, String feng, String miaoshu, String ziwaixian) {
                            updataview(maxhumi,minhumi, state,feng,miaoshu,ziwaixian);
                        }
                    });
                } else {
                    Toast.makeText(TianQiActivity.this, "地区不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initview();
        updateText("长沙", new VolleyCallBack() {
            @Override
            public void onSuccess(int maxhumi, int minhumi, String state, String feng, String miaoshu, String ziwaixian) {
                updataview(maxhumi,minhumi, state,feng,miaoshu,ziwaixian);
            }
        });
    }


    public void initview() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        tianqi_date_tv.setText(simpleDateFormat.format(new Date()));

    }

    public void updateText(String diqu, final VolleyCallBack volleyCallBack) {
        String url = "https://free-api.heweather.com/v5/weather?city=" + diqu + "&key=0212d7a29af041dca0b9852d6d0fbed2";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
                    if(jsonArray.getJSONObject(0).getString("status").equals("unknown city")){
                        Toast.makeText(TianQiActivity.this, "地区有误", Toast.LENGTH_SHORT).show();
                    }
                    else if(jsonArray.getJSONObject(0).getString("status").equals("ok")){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        JSONArray daily_forecast = jsonObject1.getJSONArray("daily_forecast");
                        Log.i("-----", "onResponse: " + daily_forecast.toString());
                        int maxhumi = daily_forecast.getJSONObject(0).getJSONObject("tmp").getInt("max");
                        int minhumi = daily_forecast.getJSONObject(0).getJSONObject("tmp").getInt("min");
                        String state = daily_forecast.getJSONObject(0).getJSONObject("cond").getString("txt_d");
                        String feng = daily_forecast.getJSONObject(0).getJSONObject("wind").getString("dir");
                        int uv = daily_forecast.getJSONObject(0).getInt("uv");
                        String uv_str = "中等";
                        if(uv<3){
                            uv_str = "轻微";
                        }
                        else if(uv>=3&&uv<6){
                            uv_str = "中等";
                        }
                        else {
                            uv_str = "强";
                        }
                        JSONObject suggestion = jsonObject1.getJSONObject("suggestion");
                        String miaoshu  = suggestion.getJSONObject("drsg").getString("txt");

                        volleyCallBack.onSuccess(maxhumi, minhumi, state, feng,miaoshu,uv_str);
                        Log.i("------", "1:"+maxhumi+"2:"+minhumi+"3:"+state+"4:"+feng+"5:"+miaoshu+"6:"+uv_str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(TianQiActivity.this, "无网络", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);

    }

    public interface VolleyCallBack {
        void onSuccess(int maxhumi, int minhumi, String state, String feng, String miaoshu, String ziwaixian);
    }

    public void updataview(int maxhumi, int minhumi, String state, String feng, String miaoshu, String ziwaixian){
        tianqi_humi_tv.setText(maxhumi+"°C~"+minhumi+"°C");
        tianqi_state_tv.setText(state);
        tianqi_feng_tv.setText(feng);

        tianqi_paomadeng_tv.setText(miaoshu);
        tianqi_ziwaixian_tv.setText(ziwaixian);
        switch (state){
            case "晴":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_01);
                break;
            case "晴间多云":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_02);
                break;
            case "多云":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_03);
                break;
            case "阴":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_04);
                break;
            case "清风":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_05);
                break;
            case "强风/劲风":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_06);
                break;
            case "阵雨":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_07);
                break;
            case "小雨":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_08);
                break;
            case "中雨":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_09);
                break;
            case "雷阵雨":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_10);
                break;
            case "小雪":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_11);
                break;
            case "大雪":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_12);
                break;
            case "雨夹雪":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_13);
                break;
            case "雨雪天气":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_14);
                break;
            case "未知":
                tianqi_imageview.setImageResource(R.drawable.weathericon_condition_16);
                break;
        }
    }

}
