package com.bluewater.business.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.bluewater.base_app.R;
import com.bluewater.base_app.base_activity.ActivityCollector;
import com.bluewater.base_app.base_activity.BaseActivity;
import com.bluewater.business.function_test.PermissionXTestActivity;
import com.bluewaterlib.toastutilslib.ToastUtils;

/**
 * 测试主界面
 */
public class MainTestActivity extends BaseActivity
{
    private Context mContext;
    private Activity mActivity;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        setTitle("MainTestActivity");

        mContext = this;
        mActivity = this;
    }


    public void onClickPermissionX(View view)
    {
        Intent intent = new Intent(mContext, PermissionXTestActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)   //返回键
        {
            exit();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 间隔的2秒内，连续点击两次返回键退出程序
     */
    public void exit()
    {
        if ((System.currentTimeMillis() - exitTime) > 2000)
        {
            ToastUtils.onInfoShowToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        }
        else
        {
            ActivityCollector.finishAll();      //销毁全部活动
        }
    }
}