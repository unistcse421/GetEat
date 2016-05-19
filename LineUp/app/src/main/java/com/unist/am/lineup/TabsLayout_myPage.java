package com.unist.am.lineup;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ActionMenuView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Dimitry Ivanov (mail@dimitryivanov.ru) on 29.03.2015.
 */
public class TabsLayout_myPage extends LinearLayout {

    private ViewGroup mContainer;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private LayoutInflater mInflater;
    private int num_page;
    private Context mcontext;

    private final Rect mRect;
    {
        mRect = new Rect();
    }

    public TabsLayout_myPage(Context context) {
        super(context);
        init(context, null);
        mcontext = context;
    }

    public TabsLayout_myPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        mcontext = context;
    }

    public TabsLayout_myPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        mcontext = context;
    }

    private void init(Context context, AttributeSet attributeSet) {

        mInflater = LayoutInflater.from(context);

        mContainer = new LinearLayout(context);
        ((LinearLayout) mContainer).setOrientation(LinearLayout.HORIZONTAL);

        mContainer.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mContainer.setLayoutTransition(new LayoutTransition());

        addView(mContainer);
    }

    public void setViewPager(ViewPager pager) {
        if (mPagerAdapter != null) {
            mContainer.removeAllViews();
        }

        mPager = pager;
        mPagerAdapter = pager.getAdapter();
        populateViews();
        setItemSelected(0);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setItemSelected(position);
                mContainer.getChildAt(position).getHitRect(mRect);
                for(int k = 0 ; k < num_page ; k++) {
                    if(k == position){
                        mContainer.getChildAt(k).setBackgroundResource(R.color.maincolor);
                    }
                    else{
                        mContainer.getChildAt(k).setBackgroundResource(R.color.white);
                    }


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void populateViews() {
        final int count = mPagerAdapter != null ? mPagerAdapter.getCount() : 0;
        num_page = count;
        if (count < 0) {
            return;
        }

        TextView view;
        for (int i = 0; i < count; i++) {
            view = createTabView();

            view.setText(mPagerAdapter.getPageTitle(i));
            if(i == 0){
                view.setBackgroundResource(R.color.maincolor);
            }
            final int position = i;

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPager.setCurrentItem(position);

                }
            });

            mContainer.addView(view, i);
        }
    }

    private TextView createTabView() {
        return (TextView) mInflater.inflate(R.layout.view_tab_item_mypage, mContainer, false);
    }

    public void setItemSelected(int position) {
        final int count = mContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            mContainer.getChildAt(i).setSelected(i == position);
        }
    }
}
