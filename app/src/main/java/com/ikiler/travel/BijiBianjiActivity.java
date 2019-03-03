package com.ikiler.travel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
 import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

public class BijiBianjiActivity extends AppCompatActivity {

    public final static int TAKE_PHOTO=1;
    private Uri imageUri;
    ImageView xiangji,ok;
    EditText biaoti,neirong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biji_bianji);
//        getSupportActionBar().hide();
        xiangji =(ImageView) findViewById(R.id.biji_xiangji);
        ok =(ImageView) findViewById(R.id.biji_ok);
        biaoti =(EditText) findViewById(R.id.biji_biaoti);
        neirong =(EditText) findViewById(R.id.biji_neirong);
        xiangji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    imageUri = FileProvider.getUriForFile(BijiBianjiActivity.this,"com.example.cameraalbumtest.fileprovier",outputImage);
                }else {
                    imageUri = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = biaoti.getText().toString();
                String content = neirong.getText().toString();
                if(!title.equals("")&&!content.equals("")){
                    new BijiSQLUtil(BijiBianjiActivity.this).add(title,content);
                    Toast.makeText(BijiBianjiActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BijiBianjiActivity.this,BijiActivity.class));
                }else{
                    Toast.makeText(BijiBianjiActivity.this,"标题或内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode==RESULT_OK){
                    try{
                        ImageView imageView = new ImageView(this);
                        imageView.setImageURI(imageUri);
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(40,40));
//                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        ImageSpan imageSpan = new ImageSpan(this,bitmap);
                        SpannableString spannableString = new SpannableString("/" + "123");
                        // 用ImageSpan对象替换文本文件名
                        spannableString.setSpan(imageSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        // 将随机获得的图像追加到EditText控件的最后
                        neirong.append(spannableString);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }

    }
}
