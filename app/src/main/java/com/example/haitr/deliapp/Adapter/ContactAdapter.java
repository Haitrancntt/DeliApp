package com.example.haitr.deliapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haitr.deliapp.Class.Contact;
import com.example.haitr.deliapp.R;

import java.util.ArrayList;

/**
 * Created by haitr on 12/14/2016.
 */
public class ContactAdapter extends BaseAdapter {
    private ArrayList<Contact> listData;
    private LayoutInflater layoutInflater;

    public ContactAdapter(Context aContext, ArrayList<Contact> listData) {
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
        ViewHolder viewHolder;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.custom_view, null);
            viewHolder = new ViewHolder();
            viewHolder.txtName = (TextView) view.findViewById(R.id.text_name);
            viewHolder.txtEmail = (TextView) view.findViewById(R.id.text_email);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.icon_call);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.txtName.setText(listData.get(i).getsName());
        viewHolder.txtEmail.setText(listData.get(i).getsEmail());
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Posittion", listData.get(i).getsName());
            }
        });
        return view;
    }
}
