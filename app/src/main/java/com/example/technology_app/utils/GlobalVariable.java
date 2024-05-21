package com.example.technology_app.utils;

import android.Manifest;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class GlobalVariable {
    public static String BASE_URL = "http://192.168.1.11:8000/api/v1/";
    public static final String SEND_ID = "idsend";
    public  static final String ID_RECEIVED = "660278540a0d57489cda9b47";
    public static final String RECEIVED_ID = "idreceived";
    public static final String MESS = "message";
    public static final String DATE_TIME = "datetime";
    public static final String CHAT_PATH = "message";

    public static String[] storage_permissons = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33 = {
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
    };

}
