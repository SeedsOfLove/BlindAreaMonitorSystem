package com.bluewater.business.monitor;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.bluewater.base_app.base_application.BaseApplication;
import com.bluewater.utils.ToBeAddUtils;

/**
 * 后台按键监听服务
 */
public class MyService extends AccessibilityService
{
    protected static final String TAG = "MyService";

    private int count = 0;

    @Override
    public void onCreate()
    {
        super.onCreate();

        Log.i(TAG, "onCreate()执行");

    }

    /**
     * 当启动服务的时候就会被调用,系统成功绑定该服务时被触发，也就是当你在设置中开启相应的服务，
     * 系统成功的绑定了该服务时会触发，通常我们可以在这里做一些初始化操作
     */
    @Override
    protected void onServiceConnected()
    {
        super.onServiceConnected();
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event)
    {
        if (!BaseApplication.IS_APP_ON_FOREGROUND)
        {
            ToBeAddUtils.wakeUpAndUnlock(BaseApplication.getContext());     //唤醒屏保和解锁

            int keyCode = event.getKeyCode();

            String desc = String.format("物理按键的编码是%d", keyCode);

            switch (keyCode)
            {
                case KeyEvent.KEYCODE_VOLUME_UP:
                    count++;
                    if (count == 2)
                    {
                        desc = String.format("%s, 按键为音量增加键", desc);
                        Intent intent = new Intent(BaseApplication.getContext(), MonitorActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        BaseApplication.getContext().startActivity(intent);

                        count = 0;
                    }
                    break;
                case KeyEvent.KEYCODE_ENTER:
                    desc = String.format("%s, 按键为回车键", desc);
                    break;
            }

            Log.d(TAG, desc);
        }

        return false;   //如果返回true，就会导致其他应用接收不到事件了，但是对KeyEvent的修改是不会分发到其他应用中的！
    }

    /**
     * 通过系统监听窗口变化的回调,sendAccessibiliyEvent()不断的发送AccessibilityEvent到此处
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent)
    {
//        Log.i(TAG, "KEYCODE_onAccessibilityEvent");

    }

    /**
     * 中断服务时的回调.
     */
    @Override
    public void onInterrupt()
    {

    }

    @Override
    public void onDestroy()
    {
        Log.i(TAG, "onDestroy()执行");
        super.onDestroy();
    }
}
