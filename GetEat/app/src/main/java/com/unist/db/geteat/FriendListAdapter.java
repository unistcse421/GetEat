package com.unist.db.geteat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Created by Mark on 2016-06-06.
 */
public class FriendListAdapter  extends ArrayAdapter<FrientListItem> {
    private Context context;
    private ArrayList<FrientListItem> items;
    int layoutResId;

    public FriendListAdapter(Context context, int textViewResourceId, ArrayList<FrientListItem> items) {
        super(context,textViewResourceId,items);
        this.layoutResId = textViewResourceId;
        this.context=context;
        this.items = items;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        ViewGroup vg = (ViewGroup) convertView;
        ViewGroup root = (ViewGroup) parent.findViewById(android.R.id.content);
        CusListHolder holder = null;
        if(v==null){
            LayoutInflater vi =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(layoutResId,parent,false);
            holder = new CusListHolder();
            holder.name = (TextView) v.findViewById(R.id.friend_name);
            holder.bank = (TextView) v.findViewById(R.id.bank);
            holder.account = (TextView) v.findViewById(R.id.account);
            holder.debt = (TextView) v.findViewById(R.id.debt);

            v.setTag(holder);

        }
        else{
            holder = (CusListHolder)v.getTag();
        }

        FrientListItem friend_item = items.get(position);


        if(friend_item!=null){
            holder.name.setText(friend_item.name);
            holder.bank.setText(friend_item.bank);
            holder.account.setText(friend_item.account);
            holder.debt.setText(friend_item.debt);

        }
        //setGlobalFont(parent);

        return v;
    }
    static class CusListHolder{
        TextView name;
        TextView bank;
        TextView account;
        TextView debt;

    }
}
