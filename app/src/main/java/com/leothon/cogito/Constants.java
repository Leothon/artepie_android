package com.leothon.cogito;

import com.leothon.cogito.Bean.SaveUploadData;
import com.leothon.cogito.Bean.UploadSave;
import com.leothon.cogito.Bean.User;

import java.util.ArrayList;

/*
* created by leothon on 2018.7.22
* */
public class Constants {

    /*
    * 此类存储全局使用常量及变量等*/

    public static long onlineTime = 0;//在线时长统计
    public static int loginStatus = 0;//登录状态，0表示未登录，1表示已登录


    public static ArrayList<UploadSave> uploadSaves;
    public static String classTitle;
    public static String classDesc;
    public static ArrayList<String> tags;

    public static boolean isMobilenet = false;

    public static String rechargeCount = "0";



}
