package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRole;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity {
    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
    }

    public void saveKickBoxer(View v) {
        try {
            ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", edtName.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(edtPunchSpeed.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(edtPunchPower.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(edtKickSpeed.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(edtKickPower.getText().toString()));
            kickBoxer.saveEventually(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        //Toast.makeText(getApplicationContext(), kickBoxer.get("name") + " is saved successfully", Toast.LENGTH_SHORT).show();
                        FancyToast.makeText(getApplicationContext(), kickBoxer.get("name") + " is saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    } else {
                        //Toast.makeText(getApplicationContext(), "KickBoxer object is not saved: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        FancyToast.makeText(getApplicationContext(), "KickBoxer object is not saved: " + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(getApplicationContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
    }
    public void helloWorldTapped(View view) {
/*        ParseObject boxer = new ParseObject("Boxer");
        boxer.put("punch_name", "HaHa!");
        boxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Toast.makeText(getApplicationContext(), "boxer object is saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "boxer object is not saved: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        ParseObject kickBoxer = new ParseObject("KickBoxer");
        kickBoxer.put("name", "John");
        kickBoxer.put("punchSpeed", 1000);
        kickBoxer.put("punchPower", 2000);
        kickBoxer.put("kickSpeed",3000);
        kickBoxer.put("kickPower", 4000);
        kickBoxer.saveEventually(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Toast.makeText(getApplicationContext(), "KickBoxer object is saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "KickBoxer object is not saved: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}