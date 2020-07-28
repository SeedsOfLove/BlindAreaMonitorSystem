package com.bluewater.base_app.base_application;

import android.app.Application;
import android.content.Context;

import com.bluewaterlib.toastutilslib.ToastUtils;

/**
 * 基础Application
 */
public class BaseApplication extends Application
{
    private static Context context;      //全局Context

    public static boolean IS_APP_ON_FOREGROUND = false; //APP是否显示在前台

    @Override
    public void onCreate()
    {
        super.onCreate();

        context = getApplicationContext();

        ToastUtils.initToast(context);  //初始化吐司工具

    }

    /**
     * 获取全局Context
     * @return
     */
    public static Context getContext()
    {
        return context;
    }




}
