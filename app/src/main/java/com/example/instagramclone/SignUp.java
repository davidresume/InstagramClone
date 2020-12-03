package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmailSignUp, edtUserNameSignUp, edtPasswordSignUp;
    private Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        edtEmailSignUp = findViewById(R.id.edtEmailSignUp);
        edtUserNameSignUp = findViewById(R.id.edtUserNameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLoginSignUp);

        setTitle("Sign Up");

        edtPasswordSignUp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnSignUp);
                }
                return false;
            }
        });

        if (ParseUser.getCurrentUser() != null) {
            if (savedInstanceState == null) {
                ParseUser.getCurrentUser().logOut();
            } else {
                transitionToSocialMediaActivity();
            }
        }

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                try {
                    final ParseUser appUser = new ParseUser();
                    String userName = edtUserNameSignUp.getText().toString();
                    String userEmail = edtEmailSignUp.getText().toString();
                    String userPassword = edtPasswordSignUp.getText().toString();
                    if (userName.equals("") || userEmail.equals("") || userPassword.equals("")) {
                        FancyToast.makeText(getApplicationContext(), "Email, Username and Password is required!",
                                FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                    } else {
                        appUser.setEmail(userEmail);
                        appUser.setUsername(userName);
                        appUser.setPassword(userPassword);

                        ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                        progressDialog.setMessage("Signing up " + userName);
                        progressDialog.show();

                        appUser.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    FancyToast.makeText(getApplicationContext(), appUser.get("username") + " sign up successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
//                                    Intent intent = new Intent(getApplicationContext(), Login.class);
//                                    startActivity(intent);
                                    transitionToSocialMediaActivity();
                                } else {
                                    FancyToast.makeText(getApplicationContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
                    }
                } catch (Exception e) {
                    FancyToast.makeText(getApplicationContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
                break;
            case R.id.btnLoginSignUp:
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
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
        Intent intent = new Intent(SignUp.this, SociaMediaActivity.class);
        startActivity(intent);
        finish();
    }
}