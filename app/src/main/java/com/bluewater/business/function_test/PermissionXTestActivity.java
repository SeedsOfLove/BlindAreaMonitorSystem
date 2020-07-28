package com.bluewater.business.function_test;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bluewater.base_app.R;
import com.bluewater.base_app.base_activity.BaseActivity;
import com.bluewaterlib.toastutilslib.ToastUtils;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;

import java.util.List;

/**
 * PermissionX测试
 */
public class PermissionXTestActivity extends BaseActivity
{
    private Context mContext;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_x_test);

        setTitle("PermissionXTestActivity");

        mContext = this;
        mActivity = this;
    }

    public void onClickCall(View view)
    {
        PermissionX.init((FragmentActivity) mActivity)
                .permissions(Manifest.permission.CALL_PHONE)
                .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam()
                {
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest)
                    {
                        scope.showRequestReasonDialog(deniedList, "电话权限是程序必须依赖的权限", "我已明白", "取消");
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
                            ToastUtils.onInfoShowToast("电权限已通过");
                        }
                        else
                        {
                            ToastUtils.onErrorShowToast("您拒绝了如下权限" + deniedList);
                        }
                    }
                });
    }

    public void onClickCamera(View view)
    {
        PermissionX.init((FragmentActivity) mActivity)
                .permissions(Manifest.permission.CAMERA)
                .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam()
                {
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest)
                    {
                        scope.showRequestReasonDialog(deniedList, "相机权限是程序必须依赖的权限", "我已明白", "取消");
                    }
                })
                .onForwardToSettings(new ForwardToSettingsCallback()
                {
                    @Override
                    public void onForwardToSettings(ForwardScope scope, List<String> deniedList)
                    {
                        scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白", "取消");
                    }
                })
                .request(new RequestCallback()
                {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList)
                    {
                        if (allGranted)
                        {
                            ToastUtils.onInfoShowToast("相机权限都已通过");
                        }
                        else
                        {
                            ToastUtils.onErrorShowToast("您拒绝了如下权限" + deniedList);
                        }
                    }
                });
    }

    public void onClickContact(View view)
    {
        PermissionX.init((FragmentActivity) mActivity)
                .permissions(Manifest.permission.READ_CONTACTS)
                .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam()
                {
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest)
                    {
                        scope.showRequestReasonDialog(deniedList, "联系人权限是程序必须依赖的权限", "我已明白", "取消");
                    }
                })
                .onForwardToSettings(new ForwardToSettingsCallback()
                {
                    @Override
                    public void onForwardToSettings(ForwardScope scope, List<String> deniedList)
                    {
                        scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白", "取消");
                    }
                })
                .request(new RequestCallback()
                {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList)
                    {
                        if (allGranted)
                        {
                            ToastUtils.onInfoShowToast("联系人权限都已通过");
                        }
                        else
                        {
                            ToastUtils.onErrorShowToast("您拒绝了如下权限" + deniedList);
                        }
                    }
                });
    }
}