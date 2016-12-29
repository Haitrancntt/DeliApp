package com.example.haitr.deliapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.haitr.deliapp.Class.Message;
import com.example.haitr.deliapp.R;

import java.util.ArrayList;

/**
 * Created by haitr on 12/26/2016.
 */
public class MessageAdapter extends BaseAdapter {
    private ArrayList<Message> listData;
    private LayoutInflater layoutInflater;

    public MessageAdapter(Context aContext, ArrayList<Message> listData) {
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
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.chat_view, null);
            viewHolder = new ViewHolder();
            viewHolder.txtChatUsename = (TextView) view.findViewById(R.id.text_chat_username);
            viewHolder.txtChatMessage = (TextView) view.findViewById(R.id.text_chat_message);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.txtChatUsename.setText(listData.get(i).getName());
        viewHolder.txtChatMessage.setText(listData.get(i).getMessage());

        return view;
    }

}
