package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity {
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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEmailSignUp.getText().toString());
                    appUser.setUsername(edtUserNameSignUp.getText().toString());
                    appUser.setPassword(edtPasswordSignUp.getText().toString());
                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null) {
                                FancyToast.makeText(getApplicationContext(),appUser.get("username") + " sign up successfully",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            } else {
                                FancyToast.makeText(getApplicationContext(),e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    FancyToast.makeText(getApplicationContext(),e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                }
            }
        });

        btnLogin.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });
    }
}