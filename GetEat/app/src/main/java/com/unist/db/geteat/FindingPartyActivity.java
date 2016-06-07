package com.unist.db.geteat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.unist.db.geteat.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FindingPartyActivity extends AppCompatActivity {
    TextView call_btn;
    String resname;
    String price;
    String phone_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_with_finding);
        Intent intent = getIntent();
        resname = intent.getExtras().getString("resname");
        price = intent.getExtras().getString("price");
        phone_num = intent.getExtras().getString("phone_num");



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
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                FindingPartyActivity.this.finish();
            }
        });
        dialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                FindingPartyActivity.this.finish();

            }
        });


        call_btn = (TextView) findViewById(R.id.call_btn);
        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:"+phone_num)));
                dialog.show();
            }
        });

    }
}
