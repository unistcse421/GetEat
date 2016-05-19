package com.unist.am.lineup;

/**
 * Created by mark_mac on 2015. 10. 30..
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Rest_info_tab1 extends BaseFragment {

    static final String TAG = "tag.tab1";
    private ScrollView mScrollView;

    String name = null;
    String cuisine = null;
    String timing = null;
    String location = null;
    Double x_coordinate = null;
    Double y_coordinate = null;
    String phone_num = null;
    String dummyname=null;
    String username=null;


    ImageView resinfo_image;
    TextView resinfo_name;
    TextView resinfo_cuisine;
    TextView resinfo_cuisine2;
    TextView resinfo_timing;
    TextView resinfo_location;
    TextView resinfo_phone_num;
    TextView resinfo_webpage;
    Context parent_context;
    Menu_Dialog menuDialog;

    RelativeLayout go_to_map_btn;
    LinearLayout menu;
    public static Rest_info_tab1 newInstance(Context context){
        final Rest_info_tab1 fragment = new Rest_info_tab1();
        fragment.parent_context = context;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        name=getArguments().getString("name");
        cuisine=getArguments().getString("cuisine");
        timing=getArguments().getString("timing");
        location=getArguments().getString("location");
        x_coordinate=getArguments().getDouble("x_coordinate");
        y_coordinate=getArguments().getDouble("y_coordinate");
        phone_num=getArguments().getString("phone_num");
        dummyname=getArguments().getString("dummy_name");
        username=getArguments().getString("username");





        final View view = inflater.inflate(R.layout.res_info_tab1, parent, false);
        resinfo_timing = (TextView) view.findViewById(R.id.res_time);
        resinfo_cuisine= (TextView) view.findViewById(R.id.res_cuisine);
        resinfo_location=(TextView) view.findViewById(R.id.res_location);
        resinfo_phone_num=(TextView)view.findViewById(R.id.res_phonenum);

        resinfo_timing.setText(timing);
        resinfo_cuisine.setText(cuisine);
        resinfo_location.setText(location);
        resinfo_phone_num.setText(phone_num);
        mScrollView = findView(view, R.id.scroll_view_tab01);

        go_to_map_btn = (RelativeLayout) view.findViewById(R.id.go_to_map);
        menu = (LinearLayout) view.findViewById(R.id.menu);

        go_to_map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map_intent = new Intent(parent_context, MapActivity.class);
                map_intent.putExtra("flag",true);
                map_intent.putExtra("lat",x_coordinate);
                map_intent.putExtra("lon",y_coordinate);
                startActivityForResult(map_intent,1);
            }
        });
        menuDialog = new Menu_Dialog(parent_context,dummyname);
        menuDialog.setTitle("메뉴");
        menuDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });
        menuDialog.setCanceledOnTouchOutside(false);
        menuDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
            }

        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    menuDialog.show();
                }


            });



        return view;
    }

    @Override
    public CharSequence getTitle(Resources r) {
        return "매장정보";
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

