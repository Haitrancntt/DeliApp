package com.example.haitr.deliapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haitr.deliapp.Class.AddContactClass;
import com.example.haitr.deliapp.Class.UserSession;
import com.example.haitr.deliapp.Fragment.ContactFragment;
import com.example.haitr.deliapp.R;

import java.util.HashMap;

public class AddContactActivity extends Activity {
    private EditText edNameContact, edEmailContact;
    private String sContactName,
            sContactEmail;
    UserSession session;
    private String sUserName;
    private static final String REGISTER_URL = "http://deliapp.890m.com/addcontact.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        LoadUI();
    }

    //LOAD UI
    public void LoadUI() {
        edNameContact = (EditText) findViewById(R.id.edit_Add_Name);
        edEmailContact = (EditText) findViewById(R.id.edit_Add_Email);
    }

    //Action
    public void btn_AddContact(View view) {
        session = new UserSession(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        sUserName = user.get(UserSession.KEY_NAME);
        sContactName = edNameContact.getText().toString().trim().toLowerCase();
        sContactEmail = edEmailContact.getText().toString().trim().toLowerCase();
        AddContact(sUserName, sContactName, sContactEmail);
        Intent intent_contact = new Intent(AddContactActivity.this, ContactFragment.class);
        startActivity(intent_contact);
    }

    //Add contact
    private void AddContact(String username, String contact_name, String contact_email) {
        class AddUserContact extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            AddContactClass ad = new AddContactClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddContactActivity.this, "Please Wait", null, true, true);
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
                data.put("username", params[0]);
                data.put("contact_name", params[1]);
                data.put("contact_email", params[2]);

                String result = ad.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }

        AddUserContact add = new AddUserContact();
        add.execute(username, contact_name, contact_email);
    }
}
