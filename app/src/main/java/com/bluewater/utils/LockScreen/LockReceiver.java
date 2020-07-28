package com.bluewater.utils.LockScreen;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LockReceiver extends DeviceAdminReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);
        Log.i("LockReceiver", "onreceive");
    }

    @Override
    public void onEnabled(Context context, Intent intent)
    {
        Log.i("LockReceiver", "激活使用 ");
        super.onEnabled(context, intent);
    }

    @Override
    public void onDisabled(Context context, Intent intent)
    {
        Log.i("LockReceiver", "取消激活");
        super.onDisabled(context, intent);
    }
}

