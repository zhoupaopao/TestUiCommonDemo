package com.example.lib.http;

import android.Manifest;
import android.util.Log;

import com.example.lib.utils.SharedPref;
import com.example.lib.utils.SharedPrefUtil;

/**
 * @author zcxiao
 */
public class Constants {
//    public static String online = "http://192.168.1.51:8080/";

    public static String getOnline() {
        return SharedPrefUtil.getIpPort();
    }

//    public static  void setOnline(String online) {
//        Constants.online = online;
//    }
//    public  class Server {
//
//
//    }
    /**
     * Permission
     */
    public static class Permission {
        public static String[] needPermission = {
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

//        public static String[] call = {
//                Manifest.permission.CALL_PHONE
//        };
//
//        public static String[] location = {
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.READ_PHONE_STATE,
//        };
//        public static String[] blueTooth = {
//                Manifest.permission.BLUETOOTH_ADMIN,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//        };
//
//        public static String[] camera = {
//                Manifest.permission.CAMERA,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//        };
//
//        public static String[] wx_permission = {
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//        };
//
//        public static String[] scan = {
//                Manifest.permission.CAMERA,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//        };

        public static String[] saveFile = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
        };


    }

}
