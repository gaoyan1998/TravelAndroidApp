package com.ikiler.travel.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageDealUtil {
    //设置新图片宽高
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // 得到新的图片   www.2cto.com
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    //压缩图片
    public static Bitmap decodeBitmapFromFile(String path,
                                              int reqWidth, int reqHeight) {
// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
// 调用上面定义的方法计算inSampleSize值
        int inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
// 使用获取到的inSampleSize值再次解析图片 
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap decodeBitmapFromFile(String path,int reqWidth) {
// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
// 调用上面定义的方法计算inSampleSize值
        int inSampleSize = calculateInSampleSize(options, reqWidth, 9999999);
// 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }
    //计算缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
// 源图片的高度和宽度 
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
// 计算出实际宽高和目标宽高的比率 
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高 
// 一定都会大于等于目标的宽和高。 
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    //把view变为bitmap
    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    /**
     * 保存图片到本地
     * @param bitmap 源图片
     * @param name 文件名
     * @param quality 图片质量 0-100
     * @param path 文件路径
     * */
    public static boolean saveBitmap(Bitmap bitmap,int quality,String path,String name){
        File file = new File(path,name);
        FileOutputStream outputStream = null;
        if (file.exists()){
            file.mkdir();
        }
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
//    public static boolean saveBitmapNewQuality(Bitmap bitmap,String path,String name,int quality){
//               return saveBitmap(bitmap,0,quality,path,name);
//    }
//    public static boolean saveBitmap(Bitmap bitmap,String path,String name,int reqWidth){
//        return saveBitmap(bitmap,reqWidth,100,path,name);
//    }

}
