package com.bluewater.base_app.welcome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bluewater.base_app.R;

/**
 * 图片viewpager
 */
public class ImagePagerAdapter extends PagerAdapter
{
    private final static int PAGE_COUNT = 3;

    /**
     * 往viewpage中添加控件，添加内容
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.welcome_pager_adapter_image, null);
        ImageView imageView = view.findViewById(R.id.iv_welcome_pager_image);

        switch (position)
        {
            case 0:
                imageView.setImageResource(R.mipmap.welcome_page_1);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.welcome_page_2);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.welcome_page_3);
                break;
            default:
                break;
        }

        container.addView(view);
        return view;
    }

    /**
     * 加入页面的时候，默认缓存三个，如不做处理，滑多了程序就会蹦
     *  (另外，不要返回父类的作用)
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((View) object);
//        super.destroyItem(container, position, object);
    }

    /**
     * 判断是否是否为同一个view
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == object;
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }
}

