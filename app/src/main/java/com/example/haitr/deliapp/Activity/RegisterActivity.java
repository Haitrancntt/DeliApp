package com.example.haitr.deliapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haitr.deliapp.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText edName, edPass, edReType, edEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        LoadUi();
    }

    public void LoadUi() {
        edName = (EditText) findViewById(R.id.edit_Regis_User);
        edPass = (EditText) findViewById(R.id.edit_Regis_Pass);
        edReType = (EditText) findViewById(R.id.edit_Regis_ReType);
        edEmail = (EditText) findViewById(R.id.edit_Regis_Email);
    }

    public void btn_Register(View view) {

        String[] array;
        array = edEmail.getText().toString().split("@", 9);
        if (array[0].equals("g")) {
            Intent intent_Login = new Intent(this, LoginActivity.class);
            startActivity(intent_Login);
        } else {
            Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
        }

    }
}
