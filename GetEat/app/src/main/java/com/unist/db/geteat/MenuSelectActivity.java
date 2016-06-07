package com.unist.db.geteat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unist.db.geteat.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MenuSelectActivity extends AppCompatActivity {

    ArrayList<MenuItem> menu;
    ArrayList<String> category;
    ArrayList<String> menu_some;
    ArrayList<String> number;
    ArrayList<OrderListItem> orders;
    ListView order_list;
    Spinner sp1;
    Spinner sp2;
    Spinner sp3;
    ArrayAdapter<String> sp1_adapter;
    ArrayAdapter<String> sp2_adapter;
    ArrayAdapter<String> sp3_adapter;
    ArrayAdapter<String> menu_adapter;
    OrderListAdapter order_adapter;
    String selected_category;
    String selected_menu="양념치";
    String selected_number="5";
    ArrayList<String> s_number;
    ArrayList<String> s_menu;
    RelativeLayout add_btn;

    String resname;
    String res_id;
    String sum_string;
    int sum=0;
    int price;
    TextView menu_price;

    LinearLayout find_party;

    boolean flag1=false;
    boolean flag2=false;
    boolean flag3=false;
    boolean flag4=false;

    String phone_num;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_select);
        Intent intent = getIntent();
        resname =intent.getExtras().getString("resname");
        res_id = intent.getExtras().getString("res_id");
        phone_num = intent.getExtras().getString("phone_num");
        category = new ArrayList<String>();
        menu = new ArrayList<MenuItem>();
        menu_some = new ArrayList<String>();
        number = new ArrayList<String>();
        orders = new ArrayList<OrderListItem>();
        s_menu = new ArrayList<String>();
        s_number = new ArrayList<String>();
        category.add("카테고리");
        menu_some.add("메뉴");
        number.add("수량");
        for(int i=0;i<9;i++) number.add(Integer.toString(i+1));

        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp2 = (Spinner) findViewById(R.id.spinner2);
        sp3 = (Spinner) findViewById(R.id.spinner3);
        order_list = (ListView) findViewById(R.id.order_list);
        add_btn = (RelativeLayout) findViewById(R.id.add_btn);
        menu_price = (TextView) findViewById(R.id.menu_price);
        find_party = (LinearLayout) findViewById(R.id.find_party);




        sp1_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category);
        sp2_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,menu_some);
        sp3_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,number);
        order_adapter = new OrderListAdapter(this,R.layout.order_list_item,orders);
        sp1.setAdapter(sp1_adapter);
        sp2.setAdapter(sp2_adapter);
        sp3.setAdapter(sp3_adapter);
        order_list.setAdapter(order_adapter);


        try {
            new getResMenu().execute("").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) flag1=true;
                else            flag3=false;

                TextView tmp = (TextView) view;
                selected_category = (String) tmp.getText();
                menu_some.clear();
                menu_some.add("메뉴");
                for (int i = 0; i < menu.size(); i++) {
                    if (selected_category.equals(menu.get(i).menu_category)) {
                        menu_some.add(menu.get(i).menu_name);
                    }
                }
                sp2_adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) flag2=true;
                else            flag2=false;

                TextView tmp = (TextView) view;
                selected_menu = (String) tmp.getText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) flag3=true;
                else            flag3=false;
                TextView tmp = (TextView) view;
                selected_number = (String) tmp.getText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag1 && flag2 && flag3) {
                    orders.add(new OrderListItem(selected_menu, selected_number));
                    order_adapter.notifyDataSetChanged();
                    for (int i = 0; i < menu.size(); i++) {
                        if (selected_menu.equals(menu.get(i).menu_name))
                            price = Integer.parseInt(menu.get(i).menu_price);
                    }
                    sum += price * Integer.parseInt(selected_number);
                    sum_string = Integer.toString(sum);
                    menu_price.setText(sum_string);
                    flag4 = true;
                }
                else Toast.makeText(MenuSelectActivity.this,"잘못된 선택입니다",Toast.LENGTH_SHORT).show();
            }
        });
        find_party.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag4) {
                    Intent intent = new Intent(getApplicationContext(), FindingPartyActivity.class);
                    intent.putExtra("resname", resname);
                    intent.putExtra("price", sum_string);
                    for(int i = 0; i<orders.size(); i++){
                        s_menu.add(orders.get(i).menu_name);
                        s_number.add(orders.get(i).menu_number);
                    }
                    intent.putStringArrayListExtra("s_menu", s_menu);
                    intent.putStringArrayListExtra("s_number",s_number);
                    intent.putExtra("phone_num", phone_num);
                    startActivity(intent);
                }
                else Toast.makeText(MenuSelectActivity.this,"선택을 완료해 주십시오",Toast.LENGTH_SHORT).show();
            }
        });


    }
    public class getResMenu extends AsyncTask<String,Void,String> {
        String sResult="error";
        String name;
        String menu_category;
        String price;
        @Override
        protected String doInBackground(String... info) {
            URL url = null;
            try {
                url = new URL("http://uni07.unist.ac.kr/~cs20121092/html/get_ResMenu.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                String post_value = "rest_id="+res_id;

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
            Log.e("resname", resname);
            Log.e("menu RESULT", sResult);
            return sResult;
        }
        @Override
        protected void onPostExecute(String result){

            String jsonall = result;
            JSONArray jArray = null;

            try{
                jArray = new JSONArray(jsonall);
                JSONObject json_data = null;

                for (int i = 0; i < jArray.length(); i++) {
                    boolean flag = true;
                    json_data = jArray.getJSONObject(i);
                    name = json_data.getString("Menu_Name");
                    menu_category = json_data.getString("Category");
                    price = json_data.getString("Price");
                    for(int j=0;j<category.size();j++){
                        if(category.get(j).equals(menu_category)) flag = false;
                    }
                    if(flag) {
                        Log.e("COFIRM:", menu_category);
                        category.add(menu_category);
                    }
                    menu.add(new MenuItem(menu_category,name,price));

                    Log.e("PROFILE",":"+i);

                }
            }catch(Exception e){
                e.printStackTrace();
            }
            //res_listview.setAdapter(adapter);
            sp1_adapter.notifyDataSetChanged();




        }
    }
}


