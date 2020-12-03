package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmailLogin, edtPasswordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        edtPasswordLogin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(findViewById(R.id.btnLogin));
                }
                return false;
            }
        });

        setTitle("Log In");
        if(ParseUser.getCurrentUser() !=null) {
//            ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }
        findViewById(R.id.btnLogin).setOnClickListener(this);

        findViewById(R.id.btnSignUpLogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                ProgressDialog progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Login " + edtEmailLogin.getText().toString());
                progressDialog.show();
                ParseUser.logInInBackground(edtEmailLogin.getText().toString(), edtPasswordLogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user != null && e == null){
                            FancyToast.makeText(getApplicationContext(),user.get("username") + " is logged in successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                            transitionToSocialMediaActivity();
                        } else {
                            FancyToast.makeText(getApplicationContext(),e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                        progressDialog.dismiss();
                    }
                });
                break;
            case R.id.btnSignUpLogin:
                finish();
                break;
        }
    }
    public void rootLayoutTapped(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(Login.this, SociaMediaActivity.class);
        startActivity(intent);
        finish();
    }
}