package com.unist.db.geteat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.unist.db.geteat.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FindingPartyActivity extends AppCompatActivity {
    TextView call_btn;
    String resname;
    String price;
    String phone_num;
    private String l_name;
    private String p_name;
    private String mParty_name;
    private String mLeader_name;
    String queries;
    private String others;
    private ListView pList;
    private PartyListAdapter adapter;
    private ArrayList<PartyListItem> parties;
    private ArrayList<String> s_menu;
    private ArrayList<String> s_number;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_with_finding);
        Intent intent = getIntent();
        resname = intent.getExtras().getString("resname");
        price = intent.getExtras().getString("price");
        phone_num = intent.getExtras().getString("phone_num");
        s_menu = intent.getExtras().getStringArrayList("s_menu");
        s_number = intent.getExtras().getStringArrayList("s_number");

        pList = (ListView) findViewById(R.id.party_list_in_finding);
        parties = new ArrayList<PartyListItem>();
        adapter = new PartyListAdapter(getApplicationContext(),R.layout.my_partyitem,parties);
        pList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        new getMyParties().execute("A00001");

        final EditText edit = new EditText(this);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(FindingPartyActivity.this);
        dialog.setTitle("주문 완료하셨습니까?");

        dialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBManager_history history = new DBManager_history(getApplicationContext(), "history.db", null, 1);
                DBManager_reserv manager_reserv = new DBManager_reserv(getApplicationContext(),"reserv_info.db",null,1);

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                manager_reserv.insert("insert into RESERV_INFO values(null,'"+resname+"','"+price+"','"+dateFormat.format(calendar.getTime())+"')");
                history.insert("insert into HISTORY values(null,'" + resname + "','" + price + "','" + dateFormat.format(calendar.getTime()) + "')");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                others = "m_id="+s_menu.get(0);
                for(int i = 1; i< s_menu.size(); i++){
                    others += "&m_id=";
                    others += s_menu.get(i);
                }
                for(int i = 0; i< s_menu.size(); i++){
                    others += "&quantity=";
                    others += s_number.get(i);
                }
                new add_order().execute(p_name,l_name,resname,Integer.toString(Integer.parseInt(price)/parties.size()),others);
                startActivity(intent);
                FindingPartyActivity.this.finish();
            }
        });
        dialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                FindingPartyActivity.this.finish();

            }
        });
        pList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pList.setItemChecked(position, true);
                //view.setBackgroundColor(getResources().getColor(R.color.color_6));
                adapter.notifyDataSetChanged();
                l_name =parties.get(position).leader_name;
                p_name =parties.get(position).party_name;

            }

        });
        call_btn = (TextView) findViewById(R.id.call_btn);
        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone_num)));
                dialog.show();
            }
        });

    }
    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
    public class add_order extends AsyncTask<String,Void,String> {
        String sResult="error";



        @Override
        protected String doInBackground(String... info) {
            URL url = null;
            try {
                String n_url = "http://uni07.unist.ac.kr/~cs20121092/html/add_order.php?";
                String post_value = "p_name=" + info[0] + "&" +"lu_id=" +info[1]+ "&" +"r_name=" +info[2] + "&" +"price=" +info[3] + "&"+info[4];
                Log.d("POST_VALUE", post_value);
                url = new URL(n_url+post_value);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
/*
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                osw.write(post_value);
                osw.flush();
*/
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
}
