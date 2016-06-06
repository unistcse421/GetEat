package com.unist.db.geteat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mintaewon on 2016. 6. 7..
 */
public class HistroyAdapter extends ArrayAdapter<HistoryItem> {
    private Context context;
    private ArrayList<HistoryItem> items;
    int layoutResId;

    public HistroyAdapter(Context context, int textViewResourceId, ArrayList<HistoryItem> items) {
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
            holder.history_id = (TextView) v.findViewById(R.id.history_id);
            holder.resname = (TextView) v.findViewById(R.id.resname);
            holder.price = (TextView) v.findViewById(R.id.price);
            holder.date = (TextView) v.findViewById(R.id.date);

            v.setTag(holder);

        }
        else{
            holder = (CusListHolder)v.getTag();
        }

        HistoryItem history_item = items.get(position);


        if(history_item!=null){
            holder.history_id.setText(history_item.history_id);
            holder.resname.setText(history_item.resname);
            holder.price.setText(history_item.price);
            holder.date.setText(history_item.date);

        }
        //setGlobalFont(parent);

        return v;
    }
    static class CusListHolder{
        TextView history_id;
        TextView resname;
        TextView price;
        TextView date;

    }
}
