package com.ikiler.travel.ui.Food;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.Model.FoodLiveDataModel;
import com.ikiler.travel.Model.bean.Food;
import com.ikiler.travel.R;
import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.util.DialogUtil;
import com.ikiler.travel.util.HttpConfig;
import com.ikiler.travel.util.ImageDealUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodEditActivity extends BaseActivity {

    private static final int SELECT_PIC_BY_PICK_PHOTO = 888;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.detail)
    EditText detail;
    private Uri imageUri;
    private String imagePath;
    private FoodLiveDataModel model;
    private boolean isEditable = true;
    protected String action = "add";
    protected Food mFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initLiveData();
    }

    private void initLiveData() {
        model = FoodLiveDataModel.instance();
        model.getMutableLiveData().observe(this, new Observer<Food>() {
            @Override
            public void onChanged(Food food) {
                if (food == null) {
                    mFood = new Food();
                    return;
                }
                name.setText(food.getName());
                detail.setText(food.getText());
                Glide.with(FoodEditActivity.this).load(food.getImagePath())
                        .into(img);
                img.setVisibility(View.VISIBLE);
                setEditable(false);
                action = "update";
                mFood = food;
            }
        });
    }

    private void setEditable(boolean editable) {
        isEditable = editable;
        name.setEnabled(editable);
        detail.setEnabled(editable);
        Drawable drawable = null;
        if (editable) {
            drawable = getResources().getDrawable(R.drawable.edit_bg);
        }
        detail.setBackground(drawable);
        name.setBackground(drawable);
//        Menu menu = toolbar.getMenu();
//        if (menu != null) {
//            menu.findItem(R.id.action_camera).setEnabled(editable);
//            menu.findItem(R.id.action_ok).setEnabled(editable);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_food_edit, menu);
        menu.findItem(R.id.action_editable).setIcon(isEditable ? R.drawable.region_edit : R.drawable.region_edit_on);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (R.id.action_ok == id) {
            submit();
        } else if (R.id.action_camera == id) {
            createSelectDialog();
        } else if (R.id.action_editable == id) {
            setEditable(!isEditable);
            item.setIcon(isEditable ? R.drawable.region_edit : R.drawable.region_edit_on);
        }

        return super.onOptionsItemSelected(item);
    }

    private void createSelectDialog() {
        DialogUtil util = new DialogUtil();
        util.dialogList(FoodEditActivity.this, new String[]{"相册选取", "拍照"}, "选择", new CallBack() {
            @Override
            public void calBack(boolean flage, int code) {
                if (flage) {
                    switch (code) {
                        case 0:
                            selPhoto();
                            break;
                        case 1:
                            takePhoto();
                            break;
                    }
                }
            }
        });
    }

    /**
     * 获取照片
     */
    private void selPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }


    private void takePhoto() {
        File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.cameraalbumtest.fileprovier", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
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
        if (TextUtils.isEmpty(imagePath)) {
            showToast("还没有上传照片哦！");
            return;
        }
        Log.e("ml", imagePath);
        mFood.setName(nameString);
        mFood.setText(detailString);
        mFood.setImagePath(imagePath);
        showNetProgress();
        editItem();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bitmap bitmap = null;
        if (resultCode != RESULT_OK) {
            return;
        }
        try {
            switch (requestCode) {
                case TAKE_PHOTO:
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    break;
                case SELECT_PIC_BY_PICK_PHOTO:
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imagePath = Environment.getExternalStorageDirectory() + "/output_image.jpg";
        ImageDealUtil.saveBitmap(bitmap, 50, Environment.getExternalStorageDirectory() + "", "output_image.jpg");
        img.setImageBitmap(bitmap);
        img.setVisibility(View.VISIBLE);
    }

    protected void editItem() {
        APIconfig.editFood(action, mFood, new CallBack() {
            @Override
            public void calBack(boolean flage, int code) {
                cancelNetDialog();
                switch (code) {
                    case HttpConfig.REQUEST_SUCCESS:
                        showToast("修改成功");
                        APIconfig.refershFoods();
                        finish();
                        break;
                    case HttpConfig.NET_ERR:
                        showToast("网络连接失败！");
                        break;
                    default:
                        showToast("出错");
                }
            }
        });
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
