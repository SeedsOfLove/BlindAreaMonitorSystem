package com.bluewater.business.main;

import android.app.Activity;
import android.app.NativeActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bluewater.base_app.R;
import com.bluewater.base_app.base_activity.ActivityCollector;
import com.bluewater.base_app.base_activity.BaseActivity;
import com.bluewaterlib.toastutilslib.ToastUtils;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity
{
    private static final String TAG = "MainActivity";

    private Context mContext;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉状态栏，实现全屏
        setContentView(R.layout.activity_main);

        mContext = this;
        mActivity = this;
    }
}