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
public class PartyListAdapter  extends ArrayAdapter<PartyListItem> {
    private Context context;
    private ArrayList<PartyListItem> items;
    int layoutResId;

    public PartyListAdapter(Context context, int textViewResourceId, ArrayList<PartyListItem> items) {
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
            holder.party_name = (TextView) v.findViewById(R.id.party_name);
            holder.leader_name = (TextView) v.findViewById(R.id.leader_name);

            v.setTag(holder);

        }
        else{
            holder = (CusListHolder)v.getTag();
        }

        PartyListItem party_item = items.get(position);


        if(party_item!=null){
            holder.party_name.setText(party_item.party_name);
            holder.leader_name.setText(party_item.leader_name);


        }
        //setGlobalFont(parent);

        return v;
    }
    static class CusListHolder{
        TextView party_name;
        TextView leader_name;
    }
}
