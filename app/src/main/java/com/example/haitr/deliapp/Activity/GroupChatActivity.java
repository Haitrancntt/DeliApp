package com.example.haitr.deliapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haitr.deliapp.Adapter.MessageAdapter;
import com.example.haitr.deliapp.Class.Message;
import com.example.haitr.deliapp.Class.SendMessageClass;
import com.example.haitr.deliapp.Class.UserSession;
import com.example.haitr.deliapp.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupChatActivity extends Activity {
    private TextView txtroomName;
    private ListView list_chat;
    private EditText edit_chat;
    private String sRoomName;
    private String sId;
    private MessageAdapter messageAdapter;
    String myJSON;
    ArrayList<Message> myList = new ArrayList<Message>();
    UserSession session;
    String sUsername;

    private static final String TAG_MESSAGE = "message";
    private static final String TAG_RESULTS = "result";
    private static final String TAG_USER = "user_name";
    private static final String REGISTER_URL = "http://deliapp.890m.com/send_message.php";
    Handler mHandler;
    Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        txtroomName = (TextView) findViewById(R.id.txt_roomname);
        list_chat = (ListView) findViewById(R.id.list_group);
        edit_chat = (EditText) findViewById(R.id.edit_chat);

        messageAdapter = new MessageAdapter(this, myList);
        session = new UserSession(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        sUsername = user.get(UserSession.KEY_NAME);
        list_chat.setAdapter(messageAdapter);

        Intent intent = getIntent();
        sRoomName = intent.getStringExtra("name");
        sId = intent.getStringExtra("id");
        txtroomName.setText(sRoomName);
        GetContact(sRoomName);


    }

    private final Runnable m_Runnable = new Runnable() {
        public void run()

        {
            GroupChatActivity.this.mHandler.postDelayed(m_Runnable, 5000);
        }

    };

    private void GetContact(final String name) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... strings) {
                String uname = strings[0];
                DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httpPost = new HttpPost("http://deliapp.890m.com/get_message.php");
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("room_name", uname));
                // httpPost.setHeader("Content-type", "application/json");
                InputStream inputStream = null;
                String result = null;
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
        g.execute(name);
    }

    //Set Data List
    private void getDataInList() {

        try {
            JSONObject object = new JSONObject(myJSON);
            JSONArray contacts = object.getJSONArray(TAG_RESULTS);
            Log.d("JSON data getDataInList", object.toString());
            myList.clear();
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject jsonObject = contacts.getJSONObject(i);
                // get data

                String sMessage = jsonObject.getString(TAG_MESSAGE);
                String sName = jsonObject.getString(TAG_USER);
                Message message = new Message();
                message.setName(sName);
                message.setMessage(sMessage);
                myList.add(message);
            }
            messageAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //action button chat
    public void button_chat(View view) {
        String sChat = edit_chat.getText().toString().trim();
        SendMess(sUsername, sChat, sRoomName);
        edit_chat.setText("");

        this.mHandler = new Handler();
        m_Runnable.run();
        messageAdapter.notifyDataSetChanged();
    }

    // function Send message
    private void SendMess(String name, String message, String room_name) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading = new ProgressDialog(getApplicationContext());
            SendMessageClass ruc = new SendMessageClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("user_name", params[0]);
                data.put("message", params[1]);
                data.put("room_name", params[2]);


                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name, message, room_name);
    }

}
