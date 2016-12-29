package com.example.haitr.deliapp.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.haitr.deliapp.Activity.AddContactActivity;
import com.example.haitr.deliapp.Adapter.ContactAdapter;
import com.example.haitr.deliapp.Class.Contact;
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
public class ContactFragment extends Fragment {
    String myJSON;
    private Button btn_Add;
    private String sUsername;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_NAME = "contact_name";
    private static final String TAG_EMAIL = "contact_email";
    ContactAdapter contactAdapter;
    ListView listView;
    ArrayList<Contact> myList = new ArrayList<Contact>();
    UserSession session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact, container, false);
        session = new UserSession(getActivity());
        hasOptionsMenu();
        HashMap<String, String> user = session.getUserDetails();
        sUsername = user.get(UserSession.KEY_NAME);

        contactAdapter = new ContactAdapter(getActivity(), myList);
        listView = (ListView) v.findViewById(R.id.list_contact);
        listView.setAdapter(contactAdapter);
        GetContact(sUsername);

        btn_Add = (Button) v.findViewById(R.id.image_button_add);
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_add = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent_add);
            }
        });
        return v;
    }

    private void GetContact(final String username) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... strings) {
                String uname = strings[0];
                DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httpPost = new HttpPost("http://deliapp.890m.com/getcontact.php");
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", uname));
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
            JSONArray contacts = object.getJSONArray(TAG_RESULTS);
            Log.d("JSON data getDataInList", object.toString());
            myList.clear();
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject jsonObject = contacts.getJSONObject(i);
                // get data
                String sName = jsonObject.getString(TAG_NAME);
                String sEmail = jsonObject.getString(TAG_EMAIL);
                Contact contact = new Contact();
                contact.setsName(sName);
                contact.setsEmail(sEmail);
                myList.add(contact);
            }
            contactAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
