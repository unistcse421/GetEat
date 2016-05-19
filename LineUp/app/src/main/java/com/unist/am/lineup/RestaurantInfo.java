package com.unist.am.lineup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;

public class RestaurantInfo extends BaseActivity {
    private static final String ARG_LAST_SCROLL_Y = "arg.LastScrollY";
    private ScrollableLayout mScrollableLayout;
    Toolbar toolbar;
    FrameLayout res_confirm;
    Context mcontext;

    String name = null;
    String cuisine = null;
    String img_large = null;
    String timing = null;
    String location = null;
    Double x_coordinate = null;
    Double y_coordinate = null;
    String phone_num = null;
    String dummyname;

    RelativeLayout lineup_btn;

    ImageView resinfo_image;
    TextView resinfo_name;
    TextView resinfo_cuisine;
    TextView resinfo_cuisine2;
    TextView resinfo_timing;
    TextView resinfo_location;
    TextView resinfo_phone_num;
    TextView resinfo_webpage;
    LinearLayout frame;

    int width_image;
    int height_image;

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_info);
        Intent intent = getIntent();
        img_large = intent.getExtras().getString("img_large");
        name = intent.getExtras().getString("name");
        cuisine = intent.getExtras().getString("cuisine");
        timing = intent.getExtras().getString("timing");
        location = intent.getExtras().getString("location");
        phone_num = intent.getExtras().getString("phone_num");
        x_coordinate = intent.getExtras().getDouble("x_coordinate");
        y_coordinate = intent.getExtras().getDouble("y_coordinate");
        username = intent.getExtras().getString("username");
        dummyname = intent.getExtras().getString("dummy_name");
        this.setResult(Activity.RESULT_OK);

        resinfo_image = (ImageView) findViewById(R.id.res_image);
        resinfo_name = (TextView) findViewById(R.id.res_name);
        resinfo_name.setText(name);
        frame = (LinearLayout) findViewById(R.id.resinfo_frame);


        final View header = findViewById(R.id.header);
        final TabsLayout tabs = findView(R.id.tabs);

        Bundle rb = new Bundle();
        rb.putString("img_large",img_large);
        rb.putString("name",name);
        rb.putString("cuisine",cuisine);
        rb.putString("timing",timing);
        rb.putString("location",location);
        rb.putString("phone_num",phone_num);
        rb.putDouble("x_coordinate", x_coordinate);
        rb.putDouble("y_coordinate",y_coordinate);
        rb.putString("username", username);
        rb.putString("dummy_name",dummyname);

        mScrollableLayout = findView(R.id.scrollable_layout);
        mScrollableLayout.setDraggableView(tabs);
        mcontext = this;
        final ViewPager viewPager = findView(R.id.view_pager);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getResources(), getFragments());
        adapter.getItem(0).setArguments(rb);
        viewPager.setAdapter(adapter);

        tabs.setViewPager(viewPager);

        mScrollableLayout.setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
            @Override
            public boolean canScrollVertically(int direction) {
                return adapter.canScrollVertically(viewPager.getCurrentItem(), direction);
            }
        });

        mScrollableLayout.setOnScrollChangedListener(new OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int y, int oldY, int maxY) {

                final float tabsTranslationY;
                if (y < maxY) {
                    tabsTranslationY = .0F;
                } else {
                    tabsTranslationY = y - maxY;
                }

                tabs.setTranslationY(tabsTranslationY);

                header.setTranslationY(y / 2);
            }
        });

        if (savedInstanceState != null) {
            final int y = savedInstanceState.getInt(ARG_LAST_SCROLL_Y);
            mScrollableLayout.post(new Runnable() {
                @Override
                public void run() {
                    mScrollableLayout.scrollTo(0, y);
                }
            });
        }


        lineup_btn = (RelativeLayout) header.findViewById(R.id.lineup_btn);
        lineup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ConfirmActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("resname",name);
                intent.putExtra("dummy_name",dummyname);
                startActivity(intent);
            }
        });

        /*
        go_to_map_btn = (Button) findVIewById(R.id.go_to_map);

         */


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_LAST_SCROLL_Y, mScrollableLayout.getScrollY());
        super.onSaveInstanceState(outState);
    }

    private List<BaseFragment> getFragments() {
        final FragmentManager manager = getSupportFragmentManager();
        final List<BaseFragment> list = new ArrayList<>();
        Rest_info_tab1 tab01 = (Rest_info_tab1) manager.findFragmentByTag(Rest_info_tab1.TAG);
        Rest_info_tab2 tab02 = (Rest_info_tab2) manager.findFragmentByTag(Rest_info_tab2.TAG);
        if (tab01 == null) {
            tab01 = tab01.newInstance(mcontext);
            tab01.name = name;
            tab01.cuisine = cuisine;
            tab01.timing = timing;
            tab01.location = location;
            tab01.phone_num = phone_num;
            tab01.x_coordinate = x_coordinate;
            tab01.y_coordinate = y_coordinate;
            tab01.dummyname = dummyname;
        }
        if (tab02 == null) {
            tab02 = tab02.newInstance();
            tab02.name = name;
        }
        Collections.addAll(list, tab01, tab02);

        return list;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        height_image = frame.getHeight();
        width_image = frame.getWidth();
        Picasso.with(getApplicationContext()).load(img_large).resize(width_image, height_image).centerCrop().into(resinfo_image);
    }
}
