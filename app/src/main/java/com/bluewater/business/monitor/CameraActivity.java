package com.bluewater.business.monitor;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bluewater.base_app.R;
import com.bluewater.base_app.base_application.BaseApplication;
import com.bluewater.utils.LockScreen.LockScreenUtils;
import com.bluewater.utils.NavigationBarUtils;
import com.bluewaterlib.toastutilslib.ToastUtils;
import com.serenegiant.common.BaseActivity;
import com.serenegiant.usb.DeviceFilter;
import com.serenegiant.usb.USBMonitor;
import com.serenegiant.usb.UVCCamera;
import com.serenegiant.usbcameracommon.UVCCameraHandler;
import com.serenegiant.widget.CameraViewInterface;

import java.util.List;

/**
 * 摄像头界面
 */
public class CameraActivity extends BaseActivity
{
    private static final boolean DEBUG = true;
    private static final String TAG = "CameraActivity";

    private static final float[] BANDWIDTH_FACTORS = {0.5f, 0.5f};

    private Context mContext;
    private Activity mActivity;

    // for accessing USB and USB camera
    private USBMonitor mUSBMonitor;

    private UVCCameraHandler mHandlerFirst;
    private CameraViewInterface mUVCCameraViewFirst;
    private Surface mFirstPreviewSurface;

    private ScreenBroadcastReceiver mScreenReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);

        mContext = this;
        mActivity = this;

        NavigationBarUtils.navigationBarStatusBar(mActivity, true);      //隐藏导航栏和状态栏

        mScreenReceiver = new ScreenBroadcastReceiver();
        mScreenReceiver.registerScreenBroadcastReceiver(this);

        LockScreenUtils.init(BaseApplication.getContext()); //初始化锁屏工具

//        findViewById(R.id.RelativeLayout1).setOnClickListener(mOnClickListener);
        mUSBMonitor = new USBMonitor(this, mOnDeviceConnectListener);
        resultFirstCamera();
    }

    /**
     * 带有回调数据的初始化
     */
    private void resultFirstCamera()
    {
        mUVCCameraViewFirst = (CameraViewInterface) findViewById(R.id.camera_view_first);
        //设置显示宽高
        mUVCCameraViewFirst.setAspectRatio(UVCCamera.DEFAULT_PREVIEW_WIDTH / (float) UVCCamera.DEFAULT_PREVIEW_HEIGHT);
//        ((UVCCameraTextureView) mUVCCameraViewFirst).setOnClickListener(mOnClickListener);

        mHandlerFirst = UVCCameraHandler.createHandler(this, mUVCCameraViewFirst
                , UVCCamera.DEFAULT_PREVIEW_WIDTH, UVCCamera.DEFAULT_PREVIEW_HEIGHT
                , BANDWIDTH_FACTORS[0]);
    }

    private void doShow()
    {
        final List<DeviceFilter> filter = DeviceFilter.getDeviceFilters(mActivity, com.serenegiant.uvccamera.R.xml.device_filter);
        List<UsbDevice> list = mUSBMonitor.getDeviceList(filter.get(0));

        if (list.size() == 0)
        {
            Toast.makeText(mContext, "无设备", Toast.LENGTH_SHORT).show();
            return;
        }

        UsbDevice usbDevice = list.get(0);
        Toast.makeText(mContext, usbDevice.getDeviceName(), Toast.LENGTH_SHORT).show();

        mUSBMonitor.requestPermission(usbDevice);

    }

    @Override
    protected void onStart()
    {
        Log.i(TAG, "onStart: ");

        super.onStart();
        mUSBMonitor.register();

        if (mUVCCameraViewFirst != null)
            mUVCCameraViewFirst.onResume();

        BaseApplication.IS_APP_ON_FOREGROUND = true;
    }

    @Override
    protected void onStop()
    {
        Log.i(TAG, "onStop: ");

        BaseApplication.IS_APP_ON_FOREGROUND = false;

        if (mUVCCameraViewFirst != null)
            mUVCCameraViewFirst.onPause();

        mUSBMonitor.unregister();//usb管理器解绑
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        Log.i(TAG, "onDestroy: ");

        if (mHandlerFirst != null)
        {
            mHandlerFirst = null;
        }

        if (mUSBMonitor != null)
        {
            mUSBMonitor.destroy();
            mUSBMonitor = null;
        }

        mUVCCameraViewFirst = null;

        mScreenReceiver.unRegisterScreenBroadcastReceiver(this);

        super.onDestroy();
    }

//    private final View.OnClickListener mOnClickListener = new View.OnClickListener()
//    {
//        @Override
//        public void onClick(final View view)
//        {
//            switch (view.getId())
//            {
//                case R.id.camera_view_first:
//                    if (mHandlerFirst != null)
//                    {
//                        if (!mHandlerFirst.isOpened())
//                        {
//                            doShow();
//                        }
//                        else
//                        {
//                            mHandlerFirst.close();
//                        }
//                    }
//                    break;
//            }
//        }
//    };

    private final USBMonitor.OnDeviceConnectListener mOnDeviceConnectListener = new USBMonitor.OnDeviceConnectListener()
    {
        @Override
        public void onAttach(final UsbDevice device)
        {
            if (DEBUG) Log.v(TAG, "onAttach:" + device);
            Toast.makeText(mContext, "USB_DEVICE_ATTACHED", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onConnect(final UsbDevice device, final USBMonitor.UsbControlBlock ctrlBlock, final boolean createNew)
        {
            //设备连接成功
            if (DEBUG) Log.v(TAG, "onConnect:" + device);
            if (!mHandlerFirst.isOpened())
            {
                mHandlerFirst.open(ctrlBlock);
                final SurfaceTexture st = mUVCCameraViewFirst.getSurfaceTexture();
                mHandlerFirst.startPreview(new Surface(st));
            }
        }

        @Override
        public void onDisconnect(final UsbDevice device, final USBMonitor.UsbControlBlock ctrlBlock)
        {
            if (DEBUG) Log.v(TAG, "onDisconnect:" + device);
            if ((mHandlerFirst != null) && !mHandlerFirst.isEqual(device))
            {
                queueEvent(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mHandlerFirst.close();
                        if (mFirstPreviewSurface != null)
                        {
                            mFirstPreviewSurface.release();
                            mFirstPreviewSurface = null;
                        }
                    }
                }, 0);
            }
        }

        @Override
        public void onDettach(final UsbDevice device)
        {
            if (DEBUG) Log.v(TAG, "onDettach:" + device);
            Toast.makeText(mContext, "USB_DEVICE_DETACHED", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(final UsbDevice device)
        {
            if (DEBUG) Log.v(TAG, "onCancel:");
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (mHandlerFirst != null)
                {
                    if (!mHandlerFirst.isOpened())
                    {
                        doShow();
                    }
                    else
                    {
                        mHandlerFirst.close();
                        doScreenOff();
                    }
                }

                break;
            case KeyEvent.KEYCODE_ENTER:
                break;
        }
        return true;
    }

    /**
     * 熄屏
     */
    private void doScreenOff()
    {
        finish();   //返回logo界面Activity

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