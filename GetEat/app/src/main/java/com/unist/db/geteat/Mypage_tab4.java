package com.unist.db.geteat;

/**
 * Created by Jeonghyun on 2015. 10. 30..
 */

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class Mypage_tab4 extends BaseFragment_myPage {

    static final String TAG = "tag.myPage_tab4";
    private ScrollView mScrollView;



    public static Mypage_tab4 newInstance(){
        final Mypage_tab4 fragment = new Mypage_tab4();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.my_partylist, parent, false);
    //    mScrollView = findView(view, R.id.scroll_view_my_tab04);
        return view;
    }

    @Override
    public CharSequence getTitle(Resources r) {
        return "파티";
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

