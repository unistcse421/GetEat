package com.unist.am.lineup;

/**
 * Created by mark_mac on 2015. 10. 30..
 */

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class Rest_info_tab2 extends BaseFragment {
    static final String TAG = "tag.tab2";
    private ScrollView mScrollView;
    String name;

    public static Rest_info_tab2 newInstance() {
        final Rest_info_tab2 fragment = new Rest_info_tab2();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.res_info_tab2, parent, false);
        mScrollView = findView(view, R.id.scroll_view_tab02);
        return view;
    }
    @Override
    public CharSequence getTitle(Resources r) {
        return "리뷰";
    }

    @Override
    public String getSelfTag() {
        return TAG;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return mScrollView != null && mScrollView.canScrollVertically(direction);
    }
}
