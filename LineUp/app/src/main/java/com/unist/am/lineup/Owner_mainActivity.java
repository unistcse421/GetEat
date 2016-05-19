package com.unist.am.lineup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by mintaewon on 2015. 10. 30..
 */
public class Owner_mainActivity extends Activity {

    private Context mcontext;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_main);
        backPressCloseHandler = new BackPressCloseHandler(this);

        mcontext = this;
        Button reservation;
        Button table;

        reservation = (Button) findViewById(R.id.reservation);
        table = (Button) findViewById(R.id.table);

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ONLY FOR TEST AND DEBUGGING, TO BE DELETED
                Intent reservation_intent = new Intent(mcontext, OwnerActivity.class);
                startActivity(reservation_intent);
            }
        });
/*
        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent table_intent = new Intent(mcontext, TableActivity.class);
                startActivity(table_intent);
            }
        });
*/

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}