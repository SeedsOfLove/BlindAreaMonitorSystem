package com.bluewater.base_app.base_activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 专门管理所有活动的类
 */
public class ActivityCollector
{
    public static List<Activity> activityList = new ArrayList<>();    //存放所有活动的List

    /**
     * 添加活动
     * @param activity
     */
    public static void addActivity(Activity activity)
    {
        activityList.add(activity);
    }

    /**
     * 移除活动
     * @param activity
     */
    public static void removeActivity(Activity activity)
    {
        activityList.remove(activity);
    }

    /**
     * 将List中的活动全部销毁
     */
    public static void finishAll()
    {
        for (Activity activity : activityList)
        {
            if (!activity.isFinishing())
            {
                activity.finish();
            }
        }

        android.os.Process.killProcess(android.os.Process.myPid());     //杀掉当前进程

        System.exit(0);
    }
}
