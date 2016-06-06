package com.unist.db.geteat;

/**
 * Created by Jeonghyun on 2015. 10. 30..
 */

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Mypage_tab4 extends BaseFragment_myPage {

    static final String TAG = "tag.myPage_tab4";
    private ScrollView mScrollView;
    private ArrayList<String> party_names;
    private ArrayList<String> leader_names;
    private String mParty_name;
    private String mLeader_name;
    private PartyListAdapter adapter;
    private ArrayList<PartyListItem> parties;
    private LinearLayout add_new_party;
    private ListView pList;
    private LinearLayout finding_parties;
    DBManager_myinfo myinfo;

    public static Mypage_tab4 newInstance(){
        final Mypage_tab4 fragment = new Mypage_tab4();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.my_partylist, parent, false);
    //    mScrollView = findView(view, R.id.scroll_view_my_tab04);
        myinfo =  new DBManager_myinfo(getActivity(),"MY_INFO",null,1);
        pList = findView(view,R.id.partyList);
        finding_parties = findView(view,R.id.new_party_add);
        parties = new ArrayList<PartyListItem>();
        adapter = new PartyListAdapter((Context)getActivity(),R.layout.my_partyitem,parties);
        //new getMyParties().execute(myinfo.returnUser_ID());
        new getMyParties().execute("A00001");
        return view;
    }

    public class getMyParties extends AsyncTask<String,Void,String> {
        String sResult="error";
        @Override
        protected String doInBackground(String... info) {
            URL url = null;
            try {
                url = new URL("http://uni07.unist.ac.kr/~cs20121092/html/search_party.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                String post_value = "u_id=" + info[0];
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
                    mParty_name = json_data.getString("Party_Name");
                    mLeader_name = json_data.getString("Name");

                    //items.add(new ResListItem(img_large, name, cuisine, location, phone_num, start, end, Delivery_Fee, Delivery_Min));
                    Log.e("PROFILE",":"+i);
                    Log.d("MY_FRIEND", "PARTY_NAME = " + mParty_name + " LEADER_NAME = " + mLeader_name);

                    parties.add(new PartyListItem(mParty_name, mLeader_name));
                }
                pList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }catch(Exception e){
                e.printStackTrace();

            }

        }
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

