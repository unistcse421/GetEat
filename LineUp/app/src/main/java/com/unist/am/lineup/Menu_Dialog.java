package com.unist.am.lineup;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Donghyun Na on 2015-11-02.
 */
public class Menu_Dialog extends Dialog implements View.OnTouchListener {

    LinearLayout Cancel;
    ArrayList<Res_menu_item> items;
    int price;
    String res_name;
    String name;
    ListView mlist;
    Res_menu_adapter adapter;
    Context mcontext;

    public Menu_Dialog(Context context, String resname) {
        super(context);
        mcontext = context;
        res_name = resname;
    }

    @Override
    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.res_menu);

        Cancel = (LinearLayout) findViewById(R.id.cancel);
        mlist = (ListView) findViewById(R.id.list);
        items = new ArrayList<Res_menu_item>();
        adapter = new Res_menu_adapter(mcontext,R.layout.res_menu_item,items);
        mlist.setEnabled(false);
        Log.e("resname",res_name);
        new HttpPostRequest().execute("");
        Cancel.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v == Cancel) {
            dismiss();
        }
        return false;
    }
    public class HttpPostRequest extends AsyncTask<String,Void,String> {
        String sResult="error";
        @Override
        protected String doInBackground(String... info) {
            URL url = null;
            try {
                url = new URL("http://52.69.163.43/queuing/get_all_rest_menu.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.e("Http connection", "완료");
                conn.setRequestMethod("POST");
                String post_value = "";
                String body = "resname=" + res_name;

                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                osw.write(body);
                osw.flush();
                Log.e("Http connection2","완료");
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                Log.e("InputStreamReader","완료");

                String str;
                while ((str = reader.readLine()) != null) {
                    builder.append(str);
                }
                sResult = builder.toString();
                Log.e(sResult,"완료");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(e.toString(),sResult);
            }


            return sResult;
        }
        @Override
        protected void onPostExecute(String result){
            Log.e("RESULT", result);
            String jsonall = result;
            JSONArray jArray = null;

            try{
                jArray = new JSONArray(jsonall);
                JSONObject json_data = null;

                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    name = json_data.getString("food_name");
                    price = json_data.getInt("price");

                    items.add(new Res_menu_item(name,price));
                    Log.e("PROFILE",":"+i);

                }
            }catch(Exception e){
                e.printStackTrace();
            }
            mlist.setAdapter(adapter);





        }
    }
}
