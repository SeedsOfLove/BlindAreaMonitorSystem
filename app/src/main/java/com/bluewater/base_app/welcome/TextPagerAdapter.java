package com.bluewater.base_app.welcome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bluewater.base_app.R;

/**
 * 文字viewpager
 */
public class TextPagerAdapter extends PagerAdapter
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
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.welcome_pager_adapter_text, null);
        TextView mTitle = view.findViewById(R.id.tv_welcome_pager_text_title);
        TextView mInfo = view.findViewById(R.id.tv_welcome_pager_text_info);

        switch (position)
        {
            case 0:
                mTitle.setText(container.getResources().getString(R.string.guid_text_one_title));
                mInfo.setText(container.getResources().getString(R.string.guid_text_one_info));
                break;
            case 1:
                mTitle.setText(container.getResources().getString(R.string.guid_text_two_title));
                mInfo.setText(container.getResources().getString(R.string.guid_text_two_info));
                break;
            case 2:
                mTitle.setText(container.getResources().getString(R.string.guid_text_three_title));
                mInfo.setText(container.getResources().getString(R.string.guid_text_three_info));
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
    public void destroyItem(ViewGroup container, int position, Object object)
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
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }
}
