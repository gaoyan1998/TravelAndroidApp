package com.ikiler.travel.ui.Food;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ikiler.travel.APIconfig;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.BijiBianjiActivity;
import com.ikiler.travel.Model.Code;
import com.ikiler.travel.Model.Food;
import com.ikiler.travel.R;
import com.ikiler.travel.util.GsonUtil;
import com.ikiler.travel.util.HttpConfig;
import com.ikiler.travel.util.OkHttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

public class FoodEditActivity extends BaseActivity {

    private EditText name;
    private EditText detail;
    private ImageView img;
    public final static int TAKE_PHOTO=1;
    private Uri imageUri;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_food_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (R.id.action_ok == id) {
            submit();
        } else if (R.id.action_camera == id) {
            takePhoto();
        }

        return super.onOptionsItemSelected(item);
    }

    private void takePhoto() {
        File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
        try{
            if (outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT>=24){
            imageUri = FileProvider.getUriForFile(getApplicationContext(),"com.example.cameraalbumtest.fileprovier",outputImage);
        }else {
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    private void initView() {
        name = (EditText) findViewById(R.id.name);
        detail = (EditText) findViewById(R.id.detail);
        img = (ImageView) findViewById(R.id.img);
    }

    private void submit() {
        // validate
        String nameString = name.getText().toString().trim();
        if (TextUtils.isEmpty(nameString)) {
            Toast.makeText(this, "名字不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String detailString = detail.getText().toString().trim();
        if (TextUtils.isEmpty(detailString)) {
            Toast.makeText(this, "介绍不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageUri == null){
            showToast("还没有上传照片哦！");
            return;
        }
        Food food = new Food();
        food.setName(nameString);
        food.setText(detailString);
        Map<String,String> map = new HashMap<>();
        map.put("action","add");
        map.put("json", GsonUtil.GsonString(food));
        dialog = new ProgressDialog(FoodEditActivity.this);
        dialog.setMessage("正在上传......");
        dialog.show();
        dialog.setCancelable(false);
        OkHttpUtil.postWithFile(APIconfig.Food+"?action=add", imageUri.getPath(), map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                Log.e("ml2",data);
                if (flage){
                    Code code = GsonUtil.GsonToBean(data,Code.class);
                    if (code.getCode() == HttpConfig.REQUEST_SUCCESS){
                        showToast("上传成功");
                        dialog.cancel();
                        finish();
                    }else dialog.cancel();
                }else dialog.cancel();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode==RESULT_OK){
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        img.setImageBitmap(bitmap);
                        img.setVisibility(View.VISIBLE);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
