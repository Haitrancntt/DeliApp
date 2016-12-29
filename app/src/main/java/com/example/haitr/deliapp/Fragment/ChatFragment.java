package com.example.haitr.deliapp.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.haitr.deliapp.Activity.AddRoomActivity;
import com.example.haitr.deliapp.Adapter.RoomAdapter;
import com.example.haitr.deliapp.Class.Room;
import com.example.haitr.deliapp.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    private ListView listRoom;
    ArrayList<Room> myList = new ArrayList<Room>();
    String myJSON;
    RoomAdapter roomAdapter;
    private static final String TAG_NAME = "name";
    private static final String TAG_RESULTS = "result";

    private static final String TAG_ROOM = "chat_room_id";
    private Button btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        listRoom = (ListView) v.findViewById(R.id.list_room);
        roomAdapter = new RoomAdapter(getActivity(), myList);

        listRoom.setAdapter(roomAdapter);
        GetRoom();
        listRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), GroupChatActivity.class);
//                intent.putExtra("id", i);
//                startActivity(intent);
            }
        });
        btnAdd = (Button) v.findViewById(R.id.button_add_room);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddRoomActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void GetRoom() {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... strings) {

                DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httpPost = new HttpPost("http://deliapp.890m.com/get_room.php");
                // httpPost.setHeader("Content-type", "application/json");
                InputStream inputStream = null;
                String result = null;
                try {

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder builder = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line + "\n");
                    }
                    result = builder.toString();

                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {

                myJSON = result;
                getDataInList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    //Set Data List
    private void getDataInList() {

        try {
            JSONObject object = new JSONObject(myJSON);
            JSONArray rooms = object.getJSONArray(TAG_RESULTS);
            Log.d("JSON data getDataInList", object.toString());
            myList.clear();
            for (int i = 0; i < rooms.length(); i++) {
                JSONObject jsonObject = rooms.getJSONObject(i);
                // get data
                String sName = jsonObject.getString(TAG_NAME);
                String sId = jsonObject.getString(TAG_ROOM);
                Room room = new Room();
                room.setsName(sName);
                room.setsID(sId);
                myList.add(room);
            }
            roomAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
