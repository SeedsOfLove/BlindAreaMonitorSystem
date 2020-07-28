package com.bluewater.base_app.base_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

/**
 * 基础活动类
 */
public class BaseActivity extends AppCompatActivity
{
    private static final String TAG = "基础活动类(BaseActivity)";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy()
    {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
    }

    //------------------------------实现字体大小不随系统改变-------------------------
    /*
        https://www.jianshu.com/p/2fdc97ae74a8
        1.xml方式：
            TextView的字体单位不使用sp，而是用dp。因为sp单位的字体大小会随系统字体大小的改变而改变，而dp单位则不会。
        2.动态编码方式：
            字体大小是否随系统改变可以通过Configuration类的fontScale变量来控制，
            fontScale变量默认为1，表示字体大小不随系统字体大小的改变而改变，
            那么我们只需要保证fontScale始终为1即可。

        虽然两种方式都可以解决场景二的问题，但是一般都是使用动态编码方式，原因如下：
            》若应用需要增加类似微信可以改变字体大小的功能，如果在xml中用的是dp单位，那么该功能将无法实现！
            》若需求改成字体大小需要随系统字体大小的改变而改变，只需要删掉该段代码即可。
            》官方推荐使用sp作为字体单位。

       下面使用动态编码方式
    */

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        if (newConfig.fontScale != 1)   //fontScale不为1，需要强制设置为1
        {
            getResources();
        }
    }

    @Override
    public Resources getResources()
    {
        Resources resources = super.getResources();
        if (resources.getConfiguration().fontScale != 1)    //fontScale不为1，需要强制设置为1
        {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();                      //设置成默认值，即fontScale为1
            resources.updateConfiguration(newConfig, resources.getDisplayMetrics());
        }
        return resources;
    }

    //------------------------------实现字体大小不随系统改变 END-------------------------
}