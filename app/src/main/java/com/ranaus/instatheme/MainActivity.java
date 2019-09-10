package com.ranaus.instatheme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   private Button btnMainLogin,btnMainSignUp;
   private EditText edtMainUsername, edtMainPasswd,edtMainMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMainLogin = (Button) findViewById(R.id.btn_LoginUp);
        btnMainSignUp = (Button) findViewById(R.id.btn_SignUpUp);

        edtMainMail = (EditText) findViewById(R.id.SignUp_email);
        edtMainUsername = (EditText) findViewById(R.id.SignUp_username);
        edtMainPasswd = (EditText) findViewById(R.id.SignUp_password);

        btnMainLogin.setOnClickListener(this);
        btnMainSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {



        switch (view.getId())
        {
            case R.id.btn_LoginUp:
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                break;

            case R.id.btn_SignUpUp:
                
                break;

        }
    }
}
