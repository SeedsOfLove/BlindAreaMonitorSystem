package com.bluewater.business.monitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.bluewater.base_app.R;
import com.bluewater.base_app.base_activity.BaseActivity;
import com.bluewater.utils.NavigationBarUtils;

/**
 * logo界面
 */
public class LogoActivity extends BaseActivity
{
    private Context mContext;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        mContext = this;
        mActivity = this;

        NavigationBarUtils.navigationBarStatusBar(mActivity, true);      //隐藏导航栏和状态栏
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_VOLUME_UP:
                Intent intent = new Intent(mContext, CameraActivity.class);
                mContext.startActivity(intent);
                break;
            case KeyEvent.KEYCODE_ENTER:
                break;
        }
        return true;
    }

}
