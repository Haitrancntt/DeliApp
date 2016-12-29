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

import com.example.haitr.deliapp.Adapter.IndiMessageAdapter;
import com.example.haitr.deliapp.Class.IndiMess;
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

public class IndividualChatActivity extends Activity {
    private TextView txtName;
    private String sName;
    private ListView list_chat;
    private EditText edit_chat;
    private String sChat;
    private IndiMessageAdapter messageAdapter;
    String myJSON;
    ArrayList<IndiMess> myList = new ArrayList<IndiMess>();
    UserSession session;
    String sUsername;
    private static final String TAG_NAME = "user_id";
    private static final String TAG_MESSAGE = "Message";
    private static final String TAG_RESULTS = "result";
    private static final String TAG_USER = "Name_Send";
    private static final String REGISTER_URL = "http://deliapp.890m.com/send_message_indi.php";
    Handler mHandler;
    Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_chat);
        LoadUI();
    }

    private void LoadUI() {
        edit_chat = (EditText) findViewById(R.id.edit_Chat_Indi);
        txtName = (TextView) findViewById(R.id.text_name);
        list_chat = (ListView) findViewById(R.id.list_indichat);
        Intent intent = getIntent();
        sName = intent.getStringExtra("Name");
        txtName.setText(sName);
        messageAdapter = new IndiMessageAdapter(this, myList);
        session = new UserSession(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        sUsername = user.get(UserSession.KEY_NAME);
        list_chat.setAdapter(messageAdapter);
        GetMessage(sName, sUsername);
    }


    private void GetMessage(final String namesend, final String namerec) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... strings) {
                String uname = strings[0];
                String unamerec = strings[1];
                DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httpPost = new HttpPost("http://deliapp.890m.com/show_message.php");
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("Name_Send", uname));
                nameValuePairs.add(new BasicNameValuePair("Name_Receive", unamerec));
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
        g.execute(namesend, namerec);
    }

    //Set Data List
    private void getDataInList() {

        try {
            JSONObject object = new JSONObject(myJSON);
            JSONArray chatmess = object.getJSONArray(TAG_RESULTS);
            Log.d("JSON data getDataInList", object.toString());
            myList.clear();
            for (int i = 0; i < chatmess.length(); i++) {
                JSONObject jsonObject = chatmess.getJSONObject(i);
                // get data

                String sMessage = jsonObject.getString(TAG_MESSAGE);
                String sName = jsonObject.getString(TAG_USER);
                IndiMess indiMess = new IndiMess();
                indiMess.setsNamesend(sName);
                indiMess.setsMessage(sMessage);
                myList.add(indiMess);
            }
            messageAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void btn_Send(View view) {
        sChat = edit_chat.getText().toString().trim();

        SendMess(sUsername, sName, sChat);
        edit_chat.setText("");
    }

    // function Send message
    private void SendMess(String namesend, String namerec, String message) {
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
                data.put("Name_Send", params[0]);
                data.put("Name_Receive", params[1]);
                data.put("Message", params[2]);


                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(namesend, namerec, message);
    }
}
