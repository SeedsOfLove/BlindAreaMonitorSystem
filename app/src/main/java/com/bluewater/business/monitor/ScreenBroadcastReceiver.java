package com.bluewater.business.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * 监听屏幕唤醒、关闭和解锁的广播
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver
{
    private final String TAG = "ScreenBroadcastReceiver";
    private boolean isRegisterReceiver = false;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action))
        {
            // 开屏
            Log.i(TAG, "开屏");
        }
        else if (Intent.ACTION_SCREEN_OFF.equals(action))
        {
            // 锁屏
            Log.i(TAG, "锁屏");
        }
        else if (Intent.ACTION_USER_PRESENT.equals(action))
        {
            // 解锁
            Log.i(TAG, "解锁");
        }
    }

    public void registerScreenBroadcastReceiver(Context mContext)
    {
        if (!isRegisterReceiver)
        {
            isRegisterReceiver = true;
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            mContext.registerReceiver(ScreenBroadcastReceiver.this, filter);
        }
    }

    public void unRegisterScreenBroadcastReceiver(Context mContext)
    {
        if (isRegisterReceiver)
        {
            isRegisterReceiver = false;
            mContext.unregisterReceiver(ScreenBroadcastReceiver.this);
        }
    }
}
