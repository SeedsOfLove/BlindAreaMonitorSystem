package com.bluewater.business.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.bluewater.base_app.R;
import com.bluewater.base_app.base_activity.BaseActivity;
import com.bluewater.business.monitor.CameraActivity;
import com.bluewaterlib.toastutilslib.ToastUtils;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;

import java.util.List;

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

        getPermission();

        TextView textView = findViewById(R.id.tv_main);
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Intent intent = new Intent(mContext, MonitorActivity.class);
                Intent intent = new Intent(mContext, CameraActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    private void getPermission()
    {
        PermissionX.init((FragmentActivity) mActivity)
                .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam()
                {
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest)
                    {
                        scope.showRequestReasonDialog(deniedList, "即将申请的权限是程序必须依赖的权限", "我已明白", "取消");
                    }
                })
                .onForwardToSettings(new ForwardToSettingsCallback()
                {
                    @Override
                    public void onForwardToSettings(ForwardScope scope, List<String> deniedList)
                    {
                        scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白");
                    }
                })
                .request(new RequestCallback()
                {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList)
                    {
                        if (allGranted)
                        {
                            ToastUtils.onInfoShowToast("所有申请的权限都已通过");
                        }
                        else
                        {
                            ToastUtils.onErrorShowToast("您拒绝了如下权限" + deniedList);
                        }
                    }
                });
    }
}