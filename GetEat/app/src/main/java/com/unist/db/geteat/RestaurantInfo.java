package com.unist.db.geteat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
    String location = null;
    String phone_num = null;
    String Delivery_Fee = null;
    String Delivery_Min = null;
    String start = null;
    String end = null;
    String dummyname;
    String res_id;

    RelativeLayout lineup_btn;
    TextView add_star;
    ImageView resinfo_image;
    TextView resinfo_name;
    TextView resinfo_cuisine;
    TextView resinfo_cuisine2;
    TextView resinfo_timing;
    TextView resinfo_location;
    TextView resinfo_phone_num;
    TextView resinfo_webpage;
    LinearLayout frame;
    ImageView star01, star02, star03, star04, star05;
    int width_image;
    int height_image;
    String rate ="0";
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_info);
        Intent intent = getIntent();
        img_large = intent.getExtras().getString("img_large");
        name = intent.getExtras().getString("name");
        cuisine = intent.getExtras().getString("cuisine");
        start = intent.getExtras().getString("start");
        end = intent.getExtras().getString("end");
        Delivery_Fee = intent.getExtras().getString("Delivery_Fee");
        Delivery_Min = intent.getExtras().getString("Delivery_Min");

        location = intent.getExtras().getString("location");
        phone_num = intent.getExtras().getString("phone_num");
        username = intent.getExtras().getString("username");
        dummyname = intent.getExtras().getString("dummy_name");
        res_id = intent.getExtras().getString("res_id");
        this.setResult(Activity.RESULT_OK);
        add_star = (TextView) findViewById(R.id.add_star);
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
        rb.putString("start",start);
        rb.putString("start",end);
        rb.putString("start",Delivery_Fee);
        rb.putString("start",Delivery_Min);
        rb.putString("location",location);
        rb.putString("phone_num",phone_num);
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

        add_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View dialog = getLayoutInflater().inflate(R.layout.dialog_add_star, null);

                AlertDialog.Builder builder= new AlertDialog.Builder(RestaurantInfo.this);

                star01 =  (ImageView) dialog.findViewById(R.id.star_01);
                star02 = (ImageView) dialog.findViewById(R.id.star_02);
                star03 = (ImageView) dialog.findViewById(R.id.star_03);
                star04 = (ImageView) dialog.findViewById(R.id.star_04);
                star05 = (ImageView) dialog.findViewById(R.id.star_05);
                star01.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rate="1";
                        star01.setImageResource(R.drawable.fullstar);
                        star02.setImageResource(R.drawable.empty_star);
                        star03.setImageResource(R.drawable.empty_star);
                        star04.setImageResource(R.drawable.empty_star);
                        star05.setImageResource(R.drawable.empty_star);
                    }
                });
                star02.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rate="2";
                        star01.setImageResource(R.drawable.fullstar);
                        star02.setImageResource(R.drawable.fullstar);
                        star03.setImageResource(R.drawable.empty_star);
                        star04.setImageResource(R.drawable.empty_star);
                        star05.setImageResource(R.drawable.empty_star);
                    }
                });
                star03.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rate="3";
                        star01.setImageResource(R.drawable.fullstar);
                        star02.setImageResource(R.drawable.fullstar);
                        star03.setImageResource(R.drawable.fullstar);
                        star04.setImageResource(R.drawable.empty_star);
                        star05.setImageResource(R.drawable.empty_star);
                    }
                });
                star04.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rate="4";
                        star01.setImageResource(R.drawable.fullstar);
                        star02.setImageResource(R.drawable.fullstar);
                        star03.setImageResource(R.drawable.fullstar);
                        star04.setImageResource(R.drawable.fullstar);
                        star05.setImageResource(R.drawable.empty_star);
                    }
                });
                star05.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rate="5";
                        star01.setImageResource(R.drawable.fullstar);
                        star02.setImageResource(R.drawable.fullstar);
                        star03.setImageResource(R.drawable.fullstar);
                        star04.setImageResource(R.drawable.fullstar);
                        star05.setImageResource(R.drawable.fullstar);
                    }
                });
                builder.setView(dialog);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new add_star().execute(name,rate);
                        Toast.makeText(getApplicationContext(), "별점을 달았습니다.", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dial=builder.create();
                dial.setCanceledOnTouchOutside(false);
                dial.show();
            }
        });
        lineup_btn = (RelativeLayout) header.findViewById(R.id.lineup_btn);
        lineup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MenuSelectActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("resname",name);
                intent.putExtra("dummy_name",dummyname);
                intent.putExtra("res_id",res_id);
                intent.putExtra("phone_num",phone_num);
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
            tab01.start = start;
            tab01.end = end;
            tab01.Delivery_Fee = Delivery_Fee;
            tab01.Delivery_Min = Delivery_Min;
            tab01.location = location;
            tab01.phone_num = phone_num;
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
    public class add_star extends AsyncTask<String,Void,String> {
        String sResult="error";
        @Override
        protected String doInBackground(String... info) {
            URL url = null;
            try {
                url = new URL("http://uni07.unist.ac.kr/~cs20121092/html/add_review.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                String post_value = "name=" + info[0] + "&" +"score=" +info[1];
                Log.d("POST_VALUE", post_value);
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                osw.write(post_value);
                osw.flush();

                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    builder.append(str);
                }
                sResult = builder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }


            return sResult;
        }
        @Override
        protected void onPostExecute(String result){
            Log.e("RESULT",result);
            String jsonall = result;
            JSONArray jArray = null;

        }
    }
}
