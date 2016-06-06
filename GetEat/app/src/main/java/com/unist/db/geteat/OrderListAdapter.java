package com.unist.db.geteat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mintaewon on 2016. 6. 6..
 */
public class OrderListAdapter extends ArrayAdapter<OrderListItem> {
    private Context context;
    private ArrayList<OrderListItem> items;
    int layoutResId;

    public OrderListAdapter(Context context, int textViewResourceId, ArrayList<OrderListItem> items) {
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
            holder.menu_name = (TextView) v.findViewById(R.id.menu_name);
            holder.menu_number = (TextView) v.findViewById(R.id.menu_number);

            v.setTag(holder);

        }
        else{
            holder = (CusListHolder)v.getTag();
        }

        OrderListItem order_item = items.get(position);


        if(order_item!=null){
            holder.menu_name.setText(order_item.menu_name);
            holder.menu_number.setText(order_item.menu_number);


        }
        //setGlobalFont(parent);

        return v;
    }
    static class CusListHolder{
        TextView menu_name;
        TextView menu_number;

    }
}
