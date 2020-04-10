package com.example.lib.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Created by dmx on 2017/11/15.
 */

public class FileUtil {
    //参数类型
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    //创建OkHttpClient实例
    private static  final OkHttpClient client = new OkHttpClient();




    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/Photo_LJ/";
    public static void createSDCardDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // 创建一个文件夹对象，赋值为外部存储器的目录
            File sdcardDir = Environment.getExternalStorageDirectory();
            //得到一个路径，内容是sdcard的文件夹路径和名字
            String path = sdcardDir.getPath() + "/Photo_LJ/";
            File path1 = new File(path);
            if (!path1.exists()) {
                //若不存在，创建目录，可以在应用启动的时候创建
                path1.mkdirs();

            }
        }
    }

//    public static void uploadImg(String imgUrl, final Context context) {
//
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        File f=new File(imgUrl);
//        if (f!=null) {
//            builder.addFormDataPart("file", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
//            builder.addFormDataPart("name",""+f.getName());
//        }
//        Log.d("uploadImg",imgUrl+"----"+ f.getName());
//        MultipartBody requestBody = builder.build();
//
//        //构建请求
//        Request request = new Request.Builder()
//                .url(Const.LOADIMAGE+StrUtil.GetEncryption())//地址  ??????????????????????????????????????????????????????????????????????????
//                .addHeader("cookie",""+SharedPredUtils.getCookie(context))
//                .post(requestBody)//添加请求体
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println("上传照片成功：response = " + response.body().string());
//            }
//        });
//
//    }

    /**
     * 上传图片
     * @param urlStr
     * @param
     * @return
     */
    public static String formUpload(String urlStr, List<String> filelist) {
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
            if (filelist != null) {

                for (int i=0;i<filelist.size();i++) {

                    String inputValue = filelist.get(i);
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();


                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"files\"; filename=\"" + filename + "\"\r\n");
                    strBuf.append("Content-Type:image/jpg\r\n\r\n");

                    out.write(strBuf.toString().getBytes());

                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        System.out.println(bytes+"~~~~~length changdu ");
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }

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


}

