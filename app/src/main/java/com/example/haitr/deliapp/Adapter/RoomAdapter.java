package com.example.haitr.deliapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haitr.deliapp.Activity.GroupChatActivity;
import com.example.haitr.deliapp.Class.Room;
import com.example.haitr.deliapp.R;

import java.util.ArrayList;

/**
 * Created by haitr on 12/26/2016.
 */
public class RoomAdapter extends BaseAdapter {
    private ArrayList<Room> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public RoomAdapter(Context aContext, ArrayList<Room> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    public RoomAdapter(Context context) {
        context = this.context;
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
            view = layoutInflater.inflate(R.layout.room_view, null);
            viewHolder = new ViewHolder();
            viewHolder.txtRoomName = (TextView) view.findViewById(R.id.text_room);
            viewHolder.Id = (TextView) view.findViewById(R.id.txt_id);
            viewHolder.imageRoom = (ImageView) view.findViewById(R.id.image_room);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.txtRoomName.setText(listData.get(i).getsName());
        viewHolder.Id.setText(listData.get(i).getsID());
//        viewHolder.imageRoom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), GroupChatActivity.class);
//                intent.putExtra("name", viewHolder.txtRoomName.getText());
//                intent.putExtra("id", viewHolder.Id.getText());
//
//                view.getContext().startActivity(intent);
//            }
//        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GroupChatActivity.class);
                intent.putExtra("name", viewHolder.txtRoomName.getText());
                intent.putExtra("id", viewHolder.Id.getText());

                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
}
