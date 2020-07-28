package com.bluewater.base_app.welcome;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.bluewater.base_app.R;
import com.bluewater.base_app.base_activity.ActivityCollector;
import com.bluewater.base_app.base_activity.BaseActivity;
import com.bluewater.business.main.MainActivity;
import com.bluewater.business.main.MainTestActivity;
import com.bluewater.toolutilslib.AppUtils;
import com.bluewaterlib.toastutilslib.ToastUtils;

import java.lang.reflect.Field;

/**
 * 欢迎页
 */
public class WelcomeActivity extends BaseActivity
{
    private Context mContext;
    private Activity mActivity;

    private MyInterceptViewPager mTextPager;        //文字viewpager
    private MyInterceptViewPager mImagePager;       //图片viewpager

    private RelativeLayout mTouchLayout;            //用于触摸的RelativeLayout

    private ImageView mIndicatorOne;                //指示器1
    private ImageView mIndicatorTwo;                //指示器2
    private ImageView mIndicatorThree;              //指示器3

    private TextView mbtnLoginOrRegister;           //登录/注册
    private TextView mbtnUse;                       //立即体验

    private ObjectAnimator mOAZoomInX;              //X轴放大
    private ObjectAnimator mOAZoomInY;              //Y轴放大
    private ObjectAnimator mOAFadeIn;               //淡入
    private AnimatorSet mAnimSetZoomInFadeIn;       //放大淡入动画

    int pageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mContext = this;
        mActivity = this;

        initView();
        initAnim();
        initEvent();

    }

    /**
     * 初始化视图
     */
    private void initView()
    {
        //去掉状态栏，实现全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mTextPager = findViewById(R.id.welcome_page_text_pager);
        mImagePager = findViewById(R.id.welcome_page_image_pager);
        mTouchLayout = findViewById(R.id.welcome_page_touch_layout);
        mIndicatorOne = findViewById(R.id.iv_welcome_page_indicator_1);
        mIndicatorTwo = findViewById(R.id.iv_welcome_page_indicator_2);
        mIndicatorThree = findViewById(R.id.iv_welcome_page_indicator_3);
        mbtnLoginOrRegister = findViewById(R.id.tv_welcome_page_login_register);
        mbtnUse = findViewById(R.id.tv_welcome_page_use);

        mIndicatorOne.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_page_circle_main));
        mIndicatorTwo.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_page_circle_gray));
        mIndicatorThree.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_page_circle_gray));

        /*通过反射，实现viewpager切换动画速度修改*/
        try
        {
            Field field = ViewPager.class.getDeclaredField("mScroller");//反射
            field.setAccessible(true);

            FixedSpeedScroller scrollerText = new FixedSpeedScroller(this, new AccelerateInterpolator());
            field.set(mTextPager, scrollerText);
            scrollerText.setmDuration(350);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        //文字
        TextPagerAdapter textPagerAdapter = new TextPagerAdapter();
        mTextPager.setAdapter(textPagerAdapter);

        //图片
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter();
        mImagePager.setAdapter(imagePagerAdapter);
    }

    /**
     * 初始化动画
     */
    private void initAnim()
    {
        //X轴放大
        mOAZoomInX = ObjectAnimator.ofFloat(mImagePager, "scaleX", 0.8f, 1.0f);
        mOAZoomInX.setDuration(500);

        //Y轴放大
        mOAZoomInY = ObjectAnimator.ofFloat(mImagePager, "scaleY", 0.8f, 1.0f);
        mOAZoomInY.setDuration(500);

        //淡入
        mOAFadeIn = ObjectAnimator.ofFloat(mImagePager, "alpha", 0.5f, 1.0f);
        mOAFadeIn.setDuration(500);

        mAnimSetZoomInFadeIn = new AnimatorSet();
        mAnimSetZoomInFadeIn.playTogether(mOAZoomInX, mOAZoomInY, mOAFadeIn);   //选择视图动画
    }

    /**
     * 初始化事件
     */
    private void initEvent()
    {
        //触摸滑动事件
        mTouchLayout.setOnTouchListener(new View.OnTouchListener()
        {
            float startX;   //手指滑动开始X坐标
            float endX;     //手指滑动结束X坐标

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        startX = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = motionEvent.getX();
                        int screenWith = AppUtils.getScreenWidthPX(mContext);       //获取屏幕宽度
                        if (startX - endX >= (screenWith / 8))       // startX - endX 大于0 且大于宽的1/8 可以往后翻页
                        {
                            if (pageIndex == 0)
                            {
                                viewPageAndIndicatorShow(1);
                            }
                            else if (pageIndex == 1)
                            {
                                viewPageAndIndicatorShow(2);
                            }
                            else if (pageIndex == 2)
                            {
                                viewPageAndIndicatorShow(2);
                            }
                        }
                        else if (endX - startX >= (screenWith / 8))  // endX - startX   大于0 且大于宽的1/8 可以往前翻页
                        {
                            if (pageIndex == 2)
                            {
                                viewPageAndIndicatorShow(1);
                            }
                            else if (pageIndex == 1)
                            {
                                viewPageAndIndicatorShow(0);
                            }
                            else if (pageIndex == 0)
                            {
                                viewPageAndIndicatorShow(0);
                            }
                        }

                        mAnimSetZoomInFadeIn.start();   //执行动画

                        break;
                }
                return true;
            }
        });

        //登录、注册
        mbtnLoginOrRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ToastUtils.onShowToast("登录/注册");
            }
        });

        //立即体验
        mbtnUse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Intent intent = new Intent(mContext, MainActivity.class);         //进入主界面
                Intent intent = new Intent(mContext, MainTestActivity.class);           //进入测试主界面
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * viewpage和指示器显示
     * @param iNum  显示号，从0开始
     */
    private void viewPageAndIndicatorShow(int iNum)
    {
        pageIndex = iNum;
        mTextPager.setCurrentItem(iNum, true);
        mImagePager.setCurrentItem(iNum);

        switch (iNum)
        {
            case 0:
                mIndicatorOne.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_page_circle_main));
                mIndicatorTwo.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_page_circle_gray));
                mIndicatorThree.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_page_circle_gray));
                break;
            case 1:
                mIndicatorOne.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_page_circle_gray));
                mIndicatorTwo.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_page_circle_main));
                mIndicatorThree.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_page_circle_gray));
                break;
            case 2:
                mIndicatorOne.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_page_circle_gray));
                mIndicatorTwo.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_page_circle_gray));
                mIndicatorThree.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.welcome_page_circle_main));
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        ActivityCollector.finishAll();
    }
}
