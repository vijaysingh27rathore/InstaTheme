package com.ranaus.instatheme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_LoginIn:

                if (edtLoginEmail.getText().toString().equals("") || edtLoginPasswd.getText().toString().equals(""))
                {
                    FancyToast.makeText(Login.this,
                                    "Email or Password is missing!!!",FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,true).show();
                }
                else {

                    ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage(edtLoginEmail.getText().toString() + " Login User!!!");
                    progressDialog.show();
                    ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPasswd.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {
                                FancyToast.makeText(Login.this, user.getUsername() +
                                                " is logged-in successfully!!!", FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS, true).show();
                                transitionToSocialMediaActivity();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }

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

    public void transitionToSocialMediaActivity()
    {
        Intent intent = new Intent(Login.this,SocialMediaActivity.class);
        startActivity(intent);
    }
}
