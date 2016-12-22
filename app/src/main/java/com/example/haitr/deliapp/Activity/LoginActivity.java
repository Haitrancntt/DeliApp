package com.example.haitr.deliapp.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haitr.deliapp.Class.UserSession;
import com.example.haitr.deliapp.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnRegis;
    private EditText edName, edPass;
    private String USER_NAME = "_UserName";
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoadUi();
    }

    private void LoadUi() {
        btnLogin = (Button) findViewById(R.id.button_login);
        btnRegis = (Button) findViewById(R.id.button_Regis);
        edName = (EditText) findViewById(R.id.edit_Login_User);
        edPass = (EditText) findViewById(R.id.edit_Login_Pass);

        session = new UserSession(getApplicationContext());
    }

    public void btn_Click(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                String username = edName.getText().toString().toLowerCase();
                String pass = edPass.getText().toString().toLowerCase();

                Login(username, pass);
                break;
            case R.id.button_Regis:
                Intent intent_Regis = new Intent(this, RegisterActivity.class);
                startActivity(intent_Regis);
                break;
        }
    }

    private void Login(final String username, String passwod) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog dLoading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dLoading = ProgressDialog.show(LoginActivity.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String pass = params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", uname));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://deliapp.890m.com/login.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                dLoading.dismiss();
                if (s.equalsIgnoreCase("success")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    session.createUserLoginSession(username);
                   // intent.putExtra(USER_NAME, username);
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                }
            }
        }
        LoginAsync loginAsync = new LoginAsync();
        loginAsync.execute(username, passwod);
    }
}

