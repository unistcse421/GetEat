package com.unist.db.geteat;

/**
 * Created by Jeonghyun on 2015. 10. 30..
 */

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;

public class Mypage_tab1 extends BaseFragment_myPage {

    static final String TAG = "tag.myPage_tab1";
    private ScrollView mScrollView;
    public ListView history_list;
    public HistroyAdapter adapter;
    public ArrayList<HistoryItem> items;

    LinearLayout layout_1;
    LinearLayout layout_2;



    public static Mypage_tab1 newInstance(){
        final Mypage_tab1 fragment = new Mypage_tab1();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.mypage_tab01, parent, false);
        mScrollView = findView(view, R.id.scroll_view_my_tab01);
        history_list = (ListView) view.findViewById(R.id.history_list);
        layout_1 = (LinearLayout) view.findViewById(R.id.layout_1);
        layout_2 = (LinearLayout) view.findViewById(R.id.layout_2);
        DBManager_history history = new DBManager_history(getActivity(), "history.db", null, 1);
        items = history.returnHistroies();
        Log.e("item size: ", " " + items.size());
        adapter = new HistroyAdapter(getActivity(),R.layout.history_item,items);
        history_list.setAdapter(adapter);
        if(items.size()>0){
            layout_1.setVisibility(View.GONE);
            layout_2.setVisibility(View.VISIBLE);
        }else{
            layout_1.setVisibility(View.VISIBLE);
            layout_2.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public CharSequence getTitle(Resources r) {
        return "히스토리";
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

