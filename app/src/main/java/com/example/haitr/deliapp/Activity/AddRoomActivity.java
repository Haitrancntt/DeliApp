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
import com.example.haitr.deliapp.R;

import java.util.HashMap;

public class AddRoomActivity extends Activity {
    private EditText edRoom;
    private String sName;
    private static final String REGISTER_URL = "http://deliapp.890m.com/create_room.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        edRoom = (EditText) findViewById(R.id.edit_Add_Room);
    }

    public void btn_AddRoom(View view) {
        sName = edRoom.getText().toString().trim();
        AddRoom(sName);
        Intent intent_room = new Intent(AddRoomActivity.this, MainActivity.class);
        startActivity(intent_room);
    }

    private void AddRoom(String name) {
        class AddUserContact extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            AddContactClass ad = new AddContactClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddRoomActivity.this, "Please Wait", null, true, true);
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
                data.put("name", params[0]);
                String result = ad.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }

        AddUserContact add = new AddUserContact();
        add.execute(name);
    }
}

