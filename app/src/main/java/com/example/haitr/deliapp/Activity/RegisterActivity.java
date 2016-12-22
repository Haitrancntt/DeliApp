package com.example.haitr.deliapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haitr.deliapp.R;
import com.example.haitr.deliapp.Class.RegisterUserClass;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText edUser, edPass, edName, edEmail;
    private static final String REGISTER_URL = "http://deliapp.890m.com/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        LoadUi();
    }

    public void LoadUi() {
        edUser = (EditText) findViewById(R.id.edit_Regis_User);
        edPass = (EditText) findViewById(R.id.edit_Regis_Pass);
        edName = (EditText) findViewById(R.id.edit_Regis_Name);
        edEmail = (EditText) findViewById(R.id.edit_Regis_Email);
    }

    public void btn_Register(View view) {
        Intent intent_register = new Intent(RegisterActivity.this, LoginActivity.class);
        registerUser();
        startActivity(intent_register);

    }

    private void registerUser() {
        String name = edName.getText().toString().trim().toLowerCase();
        String username = edUser.getText().toString().trim().toLowerCase();
        String password = edPass.getText().toString().trim().toLowerCase();
        String email = edEmail.getText().toString().trim().toLowerCase();

        register(name, username, password, email);
    }

    private void register(String name, String username, String password, String email) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Please Wait", null, true, true);
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
                data.put("username", params[1]);
                data.put("password", params[2]);
                data.put("email", params[3]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name, username, password, email);
    }
}
