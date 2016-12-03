package com.example.haitr.deliapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.haitr.deliapp.R;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnRegis;
    private EditText edName, edPass;

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
    }

    public void btn_Click(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                if (edName.getText().toString().equals("haitran") && edPass.getText().toString().equals("123")) {

                }
                break;
            case R.id.button_Regis:
                Intent intent_Regis = new Intent(this, RegisterActivity.class);
                startActivity(intent_Regis);
                break;
        }
    }
}
