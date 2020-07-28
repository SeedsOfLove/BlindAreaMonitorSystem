package com.bluewater.business.monitor;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bluewater.base_app.R;
import com.bluewater.base_app.base_activity.BaseActivity;
import com.bluewater.base_app.base_application.BaseApplication;
import com.bluewater.utils.LockScreen.LockScreenUtils;
import com.bluewater.utils.NavigationBarUtils;
import com.bluewaterlib.toastutilslib.ToastUtils;

/**
 *
 */
public class MonitorActivity extends BaseActivity
{
    private static final String TAG = "MonitorActivity";

    private Context mContext;
    private Activity mActivity;

    private ScreenBroadcastReceiver mScreenReceiver;

    private ConstraintLayout logoView;

    private boolean isLogoShow = true;  //是否显示为logo界面

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        mContext = this;
        mActivity = this;

        NavigationBarUtils.navigationBarStatusBar(mActivity, true);      //隐藏导航栏和状态栏

        logoView = findViewById(R.id.logo);

        mScreenReceiver = new ScreenBroadcastReceiver();
        mScreenReceiver.registerScreenBroadcastReceiver(this);

        LockScreenUtils.init(BaseApplication.getContext()); //初始化锁屏工具
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i(TAG, "onStart()执行");

        logoView.setVisibility(View.VISIBLE);
        BaseApplication.IS_APP_ON_FOREGROUND = true;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.i(TAG, "onStop()执行");

        BaseApplication.IS_APP_ON_FOREGROUND = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (isLogoShow)
                {
                    isLogoShow = false;
                    logoView.setVisibility(View.GONE);

                }
                else
                {
                    isLogoShow = true;
                    doScreenOff();
                }
                break;
            case KeyEvent.KEYCODE_ENTER:
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mScreenReceiver.unRegisterScreenBroadcastReceiver(this);
    }

    /**
     * 熄屏
     */
    private void doScreenOff()
    {
        if (LockScreenUtils.reqPermission(mContext))     //判断是否有权限(激活了设备管理器)
        {
            LockScreenUtils.lockScreen();// 锁屏
        }
        else
        {
            ToastUtils.onWarnShowToast("无权限");
        }
    }
}
