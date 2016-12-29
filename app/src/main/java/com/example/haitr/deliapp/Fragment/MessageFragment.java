package com.example.haitr.deliapp.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.haitr.deliapp.Adapter.ShowChatAdapter;
import com.example.haitr.deliapp.Class.MessageChat;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
    String myJSON;
    private String sUsername;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_NAME = "Name_Send";
    //    private static final String TAG_EMAIL = "contact_email";
    ShowChatAdapter showChatAdapter;
    ListView list_mess;
    ArrayList<MessageChat> myList = new ArrayList<MessageChat>();
    UserSession session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        // Inflate the layout for this fragment
        session = new UserSession(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        sUsername = user.get(UserSession.KEY_NAME);

        showChatAdapter = new ShowChatAdapter(getActivity(), myList);
        list_mess = (ListView) view.findViewById(R.id.list_message);
        list_mess.setAdapter(showChatAdapter);
        GetChat(sUsername);
        return view;
    }

    private void GetChat(final String username) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... strings) {
                String uname = strings[0];
                DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httpPost = new HttpPost("http://deliapp.890m.com/get_message_indi.php");
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("Name_Receive", uname));
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
        g.execute(username);
    }

    //Set Data List
    private void getDataInList() {

        try {
            JSONObject object = new JSONObject(myJSON);
            JSONArray mess = object.getJSONArray(TAG_RESULTS);
            Log.d("JSON data getDataInList", object.toString());
            myList.clear();
            for (int i = 0; i < mess.length(); i++) {
                JSONObject jsonObject = mess.getJSONObject(i);
                // get data
                String sName = jsonObject.getString(TAG_NAME);
                MessageChat messageChat = new MessageChat();
                messageChat.setsName(sName);
                myList.add(messageChat);
            }
            showChatAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
