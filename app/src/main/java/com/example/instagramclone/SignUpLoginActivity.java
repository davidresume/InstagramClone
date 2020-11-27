package com.example.instagramclone;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity {
    private EditText edtUserNameSignUp, edtPasswordSignUp, edtUserNameLogin, edtPasswordLogin;
    private Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);
        edtUserNameSignUp = findViewById(R.id.edtUserNameSignUp);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);

        edtUserNameLogin = findViewById(R.id.edtUserNameLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(v->{
            ParseUser appUser = new ParseUser();
            appUser.setUsername(edtUserNameSignUp.getText().toString());
            appUser.setPassword(edtPasswordSignUp.getText().toString());
            appUser.signUpInBackground(e -> {
                if(e == null) {
                    FancyToast.makeText(getApplicationContext(),appUser.get("username") + " sign up successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                } else {
                    FancyToast.makeText(getApplicationContext(),e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                }
            });

        });

        btnLogin.setOnClickListener(v -> {
            ParseUser.logInInBackground(edtUserNameLogin.getText().toString(), edtPasswordLogin.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user != null && e == null) {
                        FancyToast.makeText(getApplicationContext(),user.get("username") + " is logged in successfully",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                    } else {
                        FancyToast.makeText(getApplicationContext(),e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }
            });
        });
    }
}
