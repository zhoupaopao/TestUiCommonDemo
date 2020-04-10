package com.example.lib.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.lib.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by dmx on 2017/11/6.
 */

public class ImageUtil {

    //裁剪图片
    public static void startPhotoZoom(Activity activity, Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//        }
       // intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 130);
        intent.putExtra("outputY", 130);
        intent.putExtra("return-data", true);

        activity.startActivityForResult(intent, 2);

    }

    //裁剪图片
    public static Uri startPhotoZoomxiaomi(Activity activity, Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//        }
        // intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 130);
        intent.putExtra("outputY", 130);
        //    intent.putExtra("return-data", true);
        Uri uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/emr.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        try {
            activity.startActivityForResult(intent, 2);
            return uritempFile;
        }catch (Exception e){
            Log.d("asdsss",e.toString());
        }
        return null;
    }


    /**
     * 图片压缩 上传图片时建议compress为30
     *
     * @param bm
     * @param f
     */
    public static void compressImage(Bitmap bm, File f, int compress) {
        if (bm == null)
            return;
        File file = f;
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, compress,
                    outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩图片（质量压缩）
     * @param bitmap
     */
    public static File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.PNG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(),filename+".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }

    /**
     * Bitmap对象保存味图片文件
     */
    public static String saveBitmapFile(Bitmap bitmap){
        Date date1 = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss");
        String imgName = dateFormat1.format(date1);
        String imgString = FileUtil.SDPATH + imgName + ".JPEG";
        FileUtil.createSDCardDir();
        File file = new File(imgString);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return imgString;
    }

    /**
     * 上传图片
     * @param urlStr
     * @param
     * @return
     */
    public static String formUpload(String urlStr, File files) {
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // file
                    String filename = files.getName();
                    StringBuffer strBufre = new StringBuffer();
                    strBufre.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBufre.append("Content-Disposition: form-data; name=\"files\"; filename=\"" + filename + "\"\r\n");
                    strBufre.append("Content-Type:image/jpg\r\n\r\n");

                    out.write(strBufre.toString().getBytes());

                    DataInputStream in = new DataInputStream(new FileInputStream(files));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        System.out.println(bytes+"~~~~~length changdu ");
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();


            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }




    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps==null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }
    /**
     * 根据路径 转bitmap
     * @param urlpath
     * @return
     */
    public static Bitmap getBitMBitmap(String urlpath) {
        Bitmap bitmap = null;
        try {
            bitmap= BitmapFactory.decodeFile(urlpath,getBitmapOption(2)); //将图片的长和宽缩小味原来的1/2
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    private static BitmapFactory.Options getBitmapOption(int inSampleSize){
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    public static Bitmap getBitmapFromUri(Activity activity, Uri uri)
  {
          try
        {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                return bitmap;
               }
           catch (Exception e)
           {

                Log.e("[Android]", "目录为：" + uri);
                e.printStackTrace();
                return null;
               }
          }

    /**
     * 获取网落图片资源
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void setImageView(String url, ImageView imageView,Activity activity){

        Glide.with(activity).load("http://goo.gl/gEgYUd").placeholder( R.mipmap.icon_header ).error( R.mipmap.icon_header ).into(imageView);

    }


}
