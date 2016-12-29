package com.example.haitr.deliapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.haitr.deliapp.Activity.IndividualChatActivity;
import com.example.haitr.deliapp.Class.IndiMess;
import com.example.haitr.deliapp.R;

import java.util.ArrayList;

/**
 * Created by haitr on 12/29/2016.
 */
public class IndiMessageAdapter extends BaseAdapter {
    private ArrayList<IndiMess> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public IndiMessageAdapter(Context aContext, ArrayList<IndiMess> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.show_message_view, null);
            viewHolder = new ViewHolder();
            viewHolder.txtNameSend = (TextView) view.findViewById(R.id.textSend);
            viewHolder.txtMessage = (TextView) view.findViewById(R.id.textMessage);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.txtNameSend.setText(listData.get(i).getsNamesend());
        viewHolder.txtMessage.setText(listData.get(i).getsMessage());

        return view;
    }

}
