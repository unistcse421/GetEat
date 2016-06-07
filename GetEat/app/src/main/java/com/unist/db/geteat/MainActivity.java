package com.unist.db.geteat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.APIErrorResult;
import com.kakao.kakaotalk.KakaoTalkHttpResponseHandler;
import com.kakao.kakaotalk.KakaoTalkProfile;
import com.kakao.kakaotalk.KakaoTalkService;
import com.kakao.usermgmt.MeResponseCallback;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.UserProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private BackPressCloseHandler backPressCloseHandler;
    LinearLayout btn_mypage;
    LinearLayout btn_map;
    LinearLayout btn_search;
    private static final int CALL_REQUEST = 123;
    ImageView category[] = new ImageView[8];
    ListView res_listview;
    ArrayList<ResListItem> items;
    ResListAdapter adapter;
    RelativeLayout layout_img;
    boolean lastItemVisibleFlag;
    public static SharedPreferences pref1;
    public static SharedPreferences pref2;
    SharedPreferences.Editor editor1;
    SharedPreferences.Editor editor2;
    String name = null;
    String cuisine = null;

    String img_large = null;
    String start = null;
    String end = null;
    String location = null;
    String phone_num = null;
    String Delivery_Fee = null;
    String Delivery_Min = null;
    String category_name = "-1";
    String delivery = "-1";
    String desc = "-1";
    String res_id = null;
    String score;

    String nickName;
    String profileImageURL ;
    String thumbnailURL ;
    String countryISO ;
    Switch switch_fee;
    Switch switch_score;
    DBManager_reserv manager;
    private int clicked = -1;
    TextView score_main;
    private DrawerLayout mDrawerLayout;
    private FrameLayout leftDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("OnCreate:", "MainActivity");
        backPressCloseHandler = new BackPressCloseHandler(this);
        requestMe();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //pref1 = getApplicationContext().getSharedPreferences("Delivery_Fee_ONOFF", MODE_PRIVATE);
        //pref2 = getApplicationContext().getSharedPreferences("Descending_Order_ONOFF", MODE_PRIVATE);
        //editor1 = pref1.edit();
        //editor2 = pref2.edit();
        switch_fee = (Switch) findViewById(R.id.switch_fee);
        switch_score = (Switch) findViewById(R.id.switch_score);
        btn_search = (LinearLayout) findViewById(R.id.searchBtn);
        btn_mypage = (LinearLayout) findViewById(R.id.mypageBtn);
        btn_map = (LinearLayout) findViewById(R.id.mapBtn);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftDrawer = (FrameLayout) findViewById(R.id.lDrawer);
        category[0] = (ImageView) findViewById(R.id.first);
        category[1] = (ImageView) findViewById(R.id.first_2);
        category[2] = (ImageView) findViewById(R.id.first_3);
        category[3] = (ImageView) findViewById(R.id.first_4);
        category[4] = (ImageView) findViewById(R.id.second);
        category[5] = (ImageView) findViewById(R.id.second_2);
        category[6] = (ImageView) findViewById(R.id.second_3);
        category[7] = (ImageView) findViewById(R.id.second_4);
        category[0].setOnClickListener(this);
        category[1].setOnClickListener(this);
        category[2].setOnClickListener(this);
        category[3].setOnClickListener(this);
        category[4].setOnClickListener(this);
        category[5].setOnClickListener(this);
        category[6].setOnClickListener(this);
        category[7].setOnClickListener(this);
        //switch_fee.setChecked(pref1.getBoolean("ONOFF", false));
        switch_fee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    delivery = "true";
                    Log.d("NOTIFY", "TF : " + delivery);
                } else {
                    delivery = "-1";
                    Log.d("NOTIFY", "TF : " + delivery);

                }
            }
        });
        //switch_score.setChecked(pref1.getBoolean("ONOFF", false));
        switch_score.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    desc = "true";
                    Log.d("NOTIFY", "TF : " + desc);
                } else {
                    desc = "-1";
                    Log.d("NOTIFY", "TF : " + desc);

                }
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!leftDrawer.isShown()){
                   mDrawerLayout.openDrawer(leftDrawer);
               }
               else{
                   mDrawerLayout.closeDrawer(leftDrawer);
               }
            }
        });

        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyPageActivity.class);
                intent.putExtra("nickName",nickName);
                intent.putExtra("profileImageURL",profileImageURL);
                startActivity(intent);
            }
        });
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d("CLOSED","CLOSE!!!!!!!!");
                adapter.clearItems();
                new getSpecificInfo().execute(category_name, delivery, desc);
                res_listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        res_listview = (ListView) findViewById(R.id.res_list);
        // layout_img = (RelativeLayout) v.findViewById(R.id.layout_large_img);
        items = new ArrayList<ResListItem>();
        adapter = new ResListAdapter(this,R.layout.res_list_item,items);

        //res_listview.setEnabled(false);
        new getResInfo().execute("");
        //String distance = String.valueOf((int)calDistance(37.557627, 126.936976,37.558627,126.936976));
        //items.add(new ResListItem("http://www.365food.com/resource/upload/mini/chan/soon_y.gif", "치킨파티", "치킨",
        //       distance, String.valueOf("123"),37.558627,126.936976,"구영리 어디쯤","하루종일","몰라","ㅋㅋ"));
        res_listview.setAdapter(adapter);
        res_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RestaurantInfo.class);
                intent.putExtra("name", items.get(position).res_name);
                intent.putExtra("cuisine", items.get(position).res_cuisine);
                intent.putExtra("start", items.get(position).res_start);
                intent.putExtra("end", items.get(position).res_end);
                intent.putExtra("Delivery_Fee", items.get(position).res_fee);
                intent.putExtra("Delivery_Min", items.get(position).res_min);
                intent.putExtra("img_large", items.get(position).res_imgurl);
                intent.putExtra("location", items.get(position).res_location);
                intent.putExtra("phone_num", items.get(position).res_phone_num);
                intent.putExtra("res_id",items.get(position).res_id);
                intent.putExtra("score", items.get(position).score);
                intent.putExtra("username", nickName);

                startActivityForResult(intent, CALL_REQUEST);
            }
        });
        lastItemVisibleFlag = false;

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.first:
                if(clicked==0){
                    category[0].setImageResource(R.drawable.pill_shape);
                    clicked = -1;
                    category_name = "-1";
                }
                else {
                    category[0].setImageResource(R.drawable.pill_shape_selected);
                    if(clicked!=-1)category[clicked].setImageResource(R.drawable.pill_shape);
                    clicked = 0;
                    category_name = String.valueOf(clicked);
                }
                break;
            case R.id.first_2:
                if(clicked==1){
                    category[1].setImageResource(R.drawable.pill_shape);
                    clicked = -1;
                    category_name = "-1";
                }
                else {
                    category[1].setImageResource(R.drawable.pill_shape_selected);
                    if(clicked!=-1)category[clicked].setImageResource(R.drawable.pill_shape);
                    clicked = 1;
                    category_name = String.valueOf(clicked);
                }
                break;
            case R.id.first_3:
                if(clicked==2){
                    category[2].setImageResource(R.drawable.pill_shape);
                    clicked = -1;
                    category_name = "-1";
                }
                else {
                    category[2].setImageResource(R.drawable.pill_shape_selected);
                    if(clicked!=-1)category[clicked].setImageResource(R.drawable.pill_shape);
                    clicked = 2;
                    category_name = String.valueOf(clicked);
                }
                break;
            case R.id.first_4:
                if(clicked==3){
                    category[3].setImageResource(R.drawable.pill_shape);
                    clicked = -1;
                    category_name = "-1";
                }
                else {
                    category[3].setImageResource(R.drawable.pill_shape_selected);
                    if(clicked!=-1)category[clicked].setImageResource(R.drawable.pill_shape);
                    clicked = 3;
                    category_name = String.valueOf(clicked);
                }
                break;
            case R.id.second:
                if(clicked==4){
                    category[4].setImageResource(R.drawable.pill_shape);
                    clicked = -1;
                    category_name = "-1";
                }
                else {
                    category[4].setImageResource(R.drawable.pill_shape_selected);
                    if(clicked!=-1) category[clicked].setImageResource(R.drawable.pill_shape);
                    clicked = 4;
                    category_name = String.valueOf(clicked);

                }
                break;
            case R.id.second_2:
                if(clicked==5){
                    category[5].setImageResource(R.drawable.pill_shape);
                    clicked = -1;
                    category_name = "-1";
                }
                else {
                    category[5].setImageResource(R.drawable.pill_shape_selected);
                    if(clicked!=-1)category[clicked].setImageResource(R.drawable.pill_shape);
                    clicked = 5;
                    category_name = String.valueOf(clicked);

                }
                break;
            case R.id.second_3:
                if(clicked==6){
                    category[6].setImageResource(R.drawable.pill_shape);
                    clicked = -1;
                    category_name = "-1";
                }
                else {
                    category[6].setImageResource(R.drawable.pill_shape_selected);
                    if(clicked!=-1)category[clicked].setImageResource(R.drawable.pill_shape);
                    clicked = 6;
                    category_name = String.valueOf(clicked);

                }
                break;
            case R.id.second_4:
                if(clicked==7){
                    category[7].setImageResource(R.drawable.pill_shape);
                    clicked = -1;
                    category_name = "-1";
                }
                else {
                    category[7].setImageResource(R.drawable.pill_shape_selected);
                    if(clicked!=-1)category[clicked].setImageResource(R.drawable.pill_shape);
                    clicked = 7;
                    category_name = String.valueOf(clicked);

                }
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public double calDistance(double lat1, double lon1, double lat2, double lon2){

        double theta, dist;
        theta = lon1 - lon2;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
        dist = dist * 1000.0;      // 단위  km 에서 m 로 변환

        return dist;
    }
    // 주어진 도(degree) 값을 라디언으로 변환
    private double deg2rad(double deg){
        return (double)(deg * Math.PI / (double)180d);
    }

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    private double rad2deg(double rad){
        return (double)(rad * (double)180d / Math.PI);
    }
    public void readProfile() {
        KakaoTalkService.requestProfile(new MyTalkHttpResponseHandler<KakaoTalkProfile>() {
            @Override
            public void onHttpSuccess(final KakaoTalkProfile talkProfile) {
                nickName = talkProfile.getNickName();
                profileImageURL = talkProfile.getProfileImageURL();
                thumbnailURL = talkProfile.getThumbnailURL();
                //countryISO = talkProfile.getCountryISO();
                // display
                Log.d("OPEND", "onHttpSuccess " + nickName);
                res_listview.setEnabled(true);

            }
        });

    }



    private abstract class MyTalkHttpResponseHandler<T> extends KakaoTalkHttpResponseHandler<T> {
        @Override
        public void onHttpSessionClosedFailure(final APIErrorResult errorResult) {
            //redirectLoginActivity();
        }

        @Override
        public void onNotKakaoTalkUser(){
            Toast.makeText(getApplicationContext(), "not a KakaoTalk user", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(final APIErrorResult errorResult) {
            Toast.makeText(getApplicationContext(), "failed : " + errorResult, Toast.LENGTH_SHORT).show();
        }
    }
    public class getSpecificInfo extends AsyncTask<String,Void,String> {
        String sResult="error";
        @Override
        protected String doInBackground(String... info) {
            URL url = null;
            try {
                url = new URL("http://uni07.unist.ac.kr/~cs20121092/html/search_category_delivery.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                String post_value = "category=" + info[0] +"&"
                        +"delivery=" + info[1] + "&"
                        +"desc=" + info[2];
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

            try{
                jArray = new JSONArray(jsonall);
                JSONObject json_data = null;

                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    name = json_data.getString("Rest_Name");
                    img_large = json_data.getString("C_URL");
                    cuisine = json_data.getString("Category");
                    location = json_data.getString("Location");
                    phone_num = json_data.getString("Number");
                    start = json_data.getString("Hours_Start");
                    end = json_data.getString("Hours_End");
                    Delivery_Fee = json_data.getString("Delivery_Fee");
                    Delivery_Min = json_data.getString("Delivery_Min");
                    res_id = json_data.getString("Rest_ID");
                    score = json_data.getString("Score");
                    items.add(new ResListItem(img_large, name, cuisine, location, phone_num, start, end, Delivery_Fee, Delivery_Min,res_id,score));
                    Log.e("PROFILE",":"+i);

                }
            }catch(Exception e){
                e.printStackTrace();
            }
            res_listview.setAdapter(adapter);





        }
    }
    public class getResInfo extends AsyncTask<String,Void,String> {
        String sResult="error";
        @Override
        protected String doInBackground(String... info) {
            URL url = null;
            try {
                url = new URL("http://uni07.unist.ac.kr/~cs20121092/html/get_ResInfo.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                String post_value = "";

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

            try{
                jArray = new JSONArray(jsonall);
                JSONObject json_data = null;

                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    name = json_data.getString("Rest_Name");
                    img_large = json_data.getString("C_URL");
                    cuisine = json_data.getString("Category");
                    location = json_data.getString("Location");
                    phone_num = json_data.getString("Number");
                    start = json_data.getString("Hours_Start");
                    end = json_data.getString("Hours_End");
                    Delivery_Fee = json_data.getString("Delivery_Fee");
                    Delivery_Min = json_data.getString("Delivery_Min");
                    res_id = json_data.getString("Rest_ID");
                    score = json_data.getString("Score");
                    items.add(new ResListItem(img_large, name, cuisine, location, phone_num, start, end, Delivery_Fee, Delivery_Min,res_id,score));
                    Log.e("PROFILE",":"+i);

                }
            }catch(Exception e){
                e.printStackTrace();
            }
            res_listview.setAdapter(adapter);





        }
    }
    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {

            @Override
            public void onSuccess(final UserProfile userProfile) {
                Log.d("SUCCESS", "UserProfile : " + userProfile);
                userProfile.saveUserToCache();
                nickName = userProfile.getNickname();
                profileImageURL = userProfile.getProfileImagePath();
                thumbnailURL = userProfile.getThumbnailImagePath();
            }

            @Override
            public void onNotSignedUp() {

            }

            @Override
            public void onSessionClosedFailure(final APIErrorResult errorResult) {

                redirectLoginActivity();
            }

            @Override
            public void onFailure(final APIErrorResult errorResult) {
                if (errorResult.getErrorCodeInt() == -777) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }
        });
    }

    protected void redirectLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            manager = new DBManager_reserv(context, "reserv_info.db", null, 1);
            Log.e("CHECK", "onReceive");
            manager.delete("delete from RESERV_INFO");
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        Log.e("CHECK", "main onResume");

        getApplicationContext().registerReceiver(mReceiver, new IntentFilter("cus"));

    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e("CHECK", "main onPause");
        getApplicationContext().unregisterReceiver(mReceiver);
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
