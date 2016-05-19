package com.unist.am.lineup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by mintaewon on 2015. 7. 27..
 */
public class ReservDialog extends Dialog implements View.OnTouchListener {
    public EditText name,phone,number;
    public RelativeLayout Ok,Cancel;
    public boolean focused;
    private Typeface mTypeface;
    public String _name,_phone,_number;
    private ImageView select1, select2, select3, select4, select5, select6,date,family,group;
    public ReservDialog(Context context) {
        super(context);
    }
    int party_num;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_resrvation_page);
        focused = false;
        name = (EditText) findViewById(R.id.input_name);
        phone = (EditText) findViewById(R.id.input_phoneno);
        Ok = (RelativeLayout) findViewById(R.id.Ok);
        Cancel = (RelativeLayout) findViewById(R.id.Cancel);

        select1 = (ImageView) findViewById(R.id.selection_1);
        select2 = (ImageView) findViewById(R.id.selection_2);
        select3 = (ImageView) findViewById(R.id.selection_3);
        select4 = (ImageView) findViewById(R.id.selection_4);
        select5 = (ImageView) findViewById(R.id.selection_5);
        select6 = (ImageView) findViewById(R.id.selection_6);
        date    = (ImageView) findViewById(R.id.owner_date);
        family  = (ImageView) findViewById(R.id.owner_family);
        group   = (ImageView) findViewById(R.id.owner_group);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!(hasFocus || phone.isFocused())) {
                    hideKeyboard(v);
                    Log.d("FOCUS", "FOCUS = " + getCurrentFocus());
                }
            }
        });
        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!(hasFocus || phone.isFocused())) {
                    hideKeyboard(v);
                    Log.d("FOCUS", "FOCUS = " + getCurrentFocus());
                }
            }
        });
        select1.setOnClickListener(mOnClick);
        select2.setOnClickListener(mOnClick);
        select3.setOnClickListener(mOnClick);
        select4.setOnClickListener(mOnClick);
        select5.setOnClickListener(mOnClick);
        select6.setOnClickListener(mOnClick);
        date.setOnClickListener(mOnClick);
        family.setOnClickListener(mOnClick);
        group.setOnClickListener(mOnClick);

        /*
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(name.getWindowToken(),0);
        imm.hideSoftInputFromWindow(phone.getWindowToken(),0);
        imm.hideSoftInputFromWindow(number.getWindowToken(),0);
        */

            Ok.setOnTouchListener(this);
            Cancel.setOnTouchListener(this);
        }
        @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view==Ok){
            Log.e("ok", "z");
            _name=name.getText().toString();
            _phone=phone.getText().toString();
            name.setText(null);
            phone.setText(null);
            name.setHint("Input your name");
            phone.setHint("Input your Phone number");
            //name.setTypeface(mTypeface);
            //number.setTypeface(mTypeface);
            //phone.setTypeface(mTypeface);
            name.requestFocus();
            focused = true;
            cancel();
        }
        if(view == Cancel){
            Log.e("cacel", "z");
            dismiss();
        }
        return false;
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private View.OnClickListener mOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.selection_1: {
                    party_num=1;
                    select1.setSelected(true);
                    select2.setSelected(false);
                    select3.setSelected(false);
                    select4.setSelected(false);
                    select5.setSelected(false);
                    select6.setSelected(false);

                    break;
                }
                case R.id.selection_2: {
                    party_num=2;
                    select1.setSelected(false);
                    select2.setSelected(true);
                    select3.setSelected(false);
                    select4.setSelected(false);
                    select5.setSelected(false);
                    select6.setSelected(false);
                    break;
                }
                case R.id.selection_3: {
                    party_num=3;
                    select1.setSelected(false);
                    select2.setSelected(false);
                    select3.setSelected(true);
                    select4.setSelected(false);
                    select5.setSelected(false);
                    select6.setSelected(false);
                    break;
                }
                case R.id.selection_4: {
                    party_num=4;
                    select1.setSelected(false);
                    select2.setSelected(false);
                    select3.setSelected(false);
                    select4.setSelected(true);
                    select5.setSelected(false);
                    select6.setSelected(false);
                    break;
                }
                case R.id.selection_5: {
                    party_num=5;
                    select1.setSelected(false);
                    select2.setSelected(false);
                    select3.setSelected(false);
                    select4.setSelected(false);
                    select5.setSelected(true);
                    select6.setSelected(false);
                    break;
                }
                case R.id.selection_6: {
                    party_num=6;
                    select1.setSelected(false);
                    select2.setSelected(false);
                    select3.setSelected(false);
                    select4.setSelected(false);
                    select5.setSelected(false);
                    select6.setSelected(true);
                    break;
                }
                case R.id.owner_date:{
                    date.setSelected(true);
                    family.setSelected(false);
                    group.setSelected(false);
                    break;
                }
                case R.id.owner_family:{
                    date.setSelected(false);
                    family.setSelected(true);
                    group.setSelected(false);
                    break;
                }
                case R.id.owner_group:{
                    date.setSelected(false);
                    family.setSelected(false);
                    group.setSelected(true);
                    break;
                }
            }
        }
    };
}
