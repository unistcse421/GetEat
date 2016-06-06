package com.unist.db.geteat;

/**
 * Created by Jeonghyun on 2015. 10. 30..
 */

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;

public class Mypage_tab2 extends BaseFragment_myPage {

    static final String TAG = "tag.myPage_tab2";
    private ScrollView mScrollView;
    private LinearLayout finding_friends;
    private String mFriend;
    private String mPhone;

    public static Mypage_tab2 newInstance(){
        final Mypage_tab2 fragment = new Mypage_tab2();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.my_friendlist, parent, false);
        //mScrollView = findView(view, R.id.scroll_view_my_tab02);
        finding_friends = findView(view, R.id.find_friends);
        finding_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getMyFriends().execute("1671");
            }
        });
        return view;

    }
    public class getMyFriends extends AsyncTask<String,Void,String> {
        String sResult="error";
        @Override
        protected String doInBackground(String... info) {
            URL url = null;
            try {
                url = new URL("http://uni07.unist.ac.kr/~cs20121092/html/search_friend.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                String post_value = "sub_phone=" + info[0];
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
                    mFriend = json_data.getString("Name");
                    mPhone = json_data.getString("Phone");
                    //items.add(new ResListItem(img_large, name, cuisine, location, phone_num, start, end, Delivery_Fee, Delivery_Min));
                    Log.e("PROFILE",":"+i);
                    Log.d("MY_FRIEND", "NAME = "+ mFriend + " PHONE = " + mPhone);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
    @Override
    public CharSequence getTitle(Resources r) {
        return "친구찾기";
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

