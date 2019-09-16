package com.ranaus.instatheme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   private Button btnMainLogin,btnMainSignUp;
   private EditText edtMainUsername, edtMainPasswd,edtMainMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");

        btnMainLogin = (Button) findViewById(R.id.btn_LoginUp);
        btnMainSignUp = (Button) findViewById(R.id.btn_SignUpUp);

        edtMainMail = (EditText) findViewById(R.id.SignUp_email);
        edtMainUsername = (EditText) findViewById(R.id.SignUp_username);
        edtMainPasswd = (EditText) findViewById(R.id.SignUp_password);

        btnMainLogin.setOnClickListener(this);
        btnMainSignUp.setOnClickListener(this);
        if (ParseUser.getCurrentUser() != null)
        {
           // ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }

        edtMainPasswd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                {
                    onClick(btnMainSignUp);
                }

                return false;
            }
        });
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

                if (edtMainMail.getText().toString().equals("") || edtMainUsername.getText().toString().equals("") ||
                edtMainPasswd.getText().toString().equals(""))
                {
                    FancyToast.makeText(MainActivity.this,
                                    " Email , username or password is missing ",FancyToast.LENGTH_LONG,
                            FancyToast.INFO,true).show();
                }

                else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setUsername(edtMainUsername.getText().toString());
                    appUser.setPassword(edtMainPasswd.getText().toString());
                    appUser.setEmail(edtMainMail.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up " + edtMainUsername.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(MainActivity.this, appUser.getUsername() +
                                                " Signed Up successfully", FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS, true).show();
                                transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(MainActivity.this, "Error !!!",
                                        FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }

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
        Intent intent = new Intent(MainActivity.this,SocialMediaActivity.class);
        startActivity(intent);
    }
}
