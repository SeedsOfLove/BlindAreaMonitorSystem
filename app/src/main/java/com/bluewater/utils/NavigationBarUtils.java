package com.bluewater.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 导航栏
 */
public class NavigationBarUtils
{
    /**
     * 隐藏导航栏
     */
    public static void hideNavigationBar(Activity activity)
    {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 显示导航栏
     */
    public static void showNavigationBar(Activity activity)
    {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     *  隐藏导航栏和状态栏(当我们需要用到状态栏或导航栏时，只需要在屏幕顶部向下拉，或者在屏幕下侧向上拉，状态栏和导航栏就会显示出来)
     * @param activity
     * @param hasFocus
     */
    public static void navigationBarStatusBar(Activity activity, boolean hasFocus)
    {
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE|View.SYSTEM_UI_FLAG_FULLSCREEN;
        window.setAttributes(params);

        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar

        if (android.os.Build.VERSION.SDK_INT >= 19)
        {
            uiFlags |= 0x00001000;    //SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide navigation bars - compatibility: building API level is lower thatn 19, use magic number directly for higher API target level
        }
        else
        {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }

        activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }


    /**
     * 修改导航栏颜色，支持4.4以上版本
     *
     * @param colorId 颜色
     */
    public static void setNavigationBarColor(Activity activity, int colorId)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = activity.getWindow();
            window.setNavigationBarColor(colorId);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            //使用SystemBarTintManager,需要先将导航栏设置为透明
            setTranslucentNavigation(activity);
            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(activity);
            systemBarTintManager.setNavigationBarTintEnabled(true);//显示导航栏
            systemBarTintManager.setNavigationBarTintColor(colorId);//设置导航栏颜色
        }
    }

    /**
     * 设置状态栏透明
     */
    @TargetApi(19)
    public static void setTranslucentNavigation(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

//            window.setStatusBarColor(Color.TRANSPARENT);
            //导航栏颜色也可以正常设置
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = activity.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            attributes.flags |= flagTranslucentNavigation;
            window.setAttributes(attributes);
        }
    }
}
