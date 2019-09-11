package com.ranaus.instatheme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginEmail,edtLoginPasswd;
    private Button btnLoginLogin,btnLoginSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Sign In");

        edtLoginEmail = (EditText) findViewById(R.id.SignIn_email);
        edtLoginPasswd = (EditText) findViewById(R.id.SignIn_password);
        btnLoginSignUp = (Button) findViewById(R.id.btn_SignUpIn);
        btnLoginLogin = (Button) findViewById(R.id.btn_LoginIn);

        btnLoginLogin.setOnClickListener(this);
        btnLoginSignUp.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null)
        {
            ParseUser.logOut();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_LoginIn:

                ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPasswd.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null)
                        {
                            FancyToast.makeText(Login.this,user.getUsername()+
                                            " is logged-in successfully!!!",FancyToast.LENGTH_LONG,
                                    FancyToast.SUCCESS,true).show();
                        }
                    }
                });

                break;

            case R.id.btn_SignUpIn:
                finish();

                break;
        }
    }

    public void rootLayoutTapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
