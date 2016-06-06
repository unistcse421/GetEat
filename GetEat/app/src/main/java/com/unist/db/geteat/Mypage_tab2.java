package com.unist.db.geteat;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Mypage_tab2 extends BaseFragment_myPage {

    static final String TAG = "tag.myPage_tab2";
    private ScrollView mScrollView;
    private LinearLayout finding_friends;
    private String mFriend;
    private String mID;
    private String mPhone;
    private String mBank;
    private String mAccount;
    ArrayList<FrientListItem> friends;
    ArrayList<String> Afriend;
    public ListView fList;
    private EditText edit_friend;
    private FriendListAdapter adapter;
    private ArrayList<String> names;
    private ArrayList<String> debts;
    private ArrayList<String> bank;
    private ArrayList<String> account;
    DBManager_friend manager_friend;
    public static Mypage_tab2 newInstance(){
        final Mypage_tab2 fragment = new Mypage_tab2();
        return fragment;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.my_friendlist, parent, false);

        //mScrollView = findView(view, R.id.scroll_view_my_tab02);
        manager_friend = new DBManager_friend(getActivity(),"FRIEND.db",null,1);
        names = manager_friend.returnNames();
        debts = manager_friend.returnDebts();
        bank = manager_friend.returnBank();
        account = manager_friend.returnAccount();
        if(names.size()!= 0) {
            Log.d("NAME", names.get(0));
            Log.d("Debts", debts.get(0));
        }
        fList = findView(view, R.id.friend_list);
        finding_friends = findView(view, R.id.find_friends);
        friends = new ArrayList<FrientListItem>();
        adapter = new FriendListAdapter((Context)getActivity(),R.layout.myfriend_list,friends);
        finding_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View dialog = inflater.inflate(R.layout.dialog__addfriend, null);

                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

                builder.setTitle("친구 추가");
                edit_friend = findView(dialog, R.id.edit_friend);

                builder.setIcon(android.R.drawable.ic_menu_add);
                builder.setView(dialog);
                builder.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new getMyFriends().execute(edit_friend.getText().toString());
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dial=builder.create();
                dial.setCanceledOnTouchOutside(false);
                dial.show();
            }
        });
        for(int i = 0; i<names.size(); i++) {
            friends.add(new FrientListItem(names.get(i), bank.get(i), account.get(i), debts.get(i)));
        }
        fList.setAdapter(adapter);
        fList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Making Dialog
                //confirm update items
                //set notify adpater
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
            Afriend = new ArrayList<String>();

            try{
                jArray = new JSONArray(jsonall);
                JSONObject json_data = null;

                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    mFriend = json_data.getString("Name");
                    mID = json_data.getString("User_ID");
                    mPhone = json_data.getString("Phone");
                    mBank = json_data.getString("Bank");
                    mAccount = json_data.getString("Account");
                    //items.add(new ResListItem(img_large, name, cuisine, location, phone_num, start, end, Delivery_Fee, Delivery_Min));
                    Log.e("PROFILE",":"+i);
                    Log.d("MY_FRIEND", "User_ID = " + mID + " NAME = " + mFriend + " PHONE = " + mPhone + " BANK = " + mBank + " ACCOUNT = " + mAccount);
                    Afriend.add(mID);
                    Afriend.add(mFriend);
                    Afriend.add(mBank);
                    Afriend.add(mAccount);
                    Afriend.add("0");
                    manager_friend.insertAFriend(Afriend);
                    friends.add(new FrientListItem(mFriend, mBank, mAccount, "0"));
                    Afriend.clear();
                }
                fList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(),"추가하였습니다.", Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(getActivity(), "실패하였습니다.", Toast.LENGTH_SHORT).show();

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

