package com.example.haitr.deliapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.haitr.deliapp.Activity.IndividualChatActivity;
import com.example.haitr.deliapp.Class.MessageChat;
import com.example.haitr.deliapp.R;

import java.util.ArrayList;

/**
 * Created by haitr on 12/29/2016.
 */
public class ShowChatAdapter extends BaseAdapter {
    private ArrayList<MessageChat> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public ShowChatAdapter(Context aContext, ArrayList<MessageChat> listData) {
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
            view = layoutInflater.inflate(R.layout.custom_show_chat_view, null);
            viewHolder = new ViewHolder();
            viewHolder.txtNameMess = (TextView) view.findViewById(R.id.text_name_chat);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.txtNameMess.setText(listData.get(i).getsName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), IndividualChatActivity.class);
                intent.putExtra("Name", listData.get(i).getsName());
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
}
