package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity {
    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower, edtAllBoxers;
    private Button btnGetAllData;
    private TextView txtGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        edtAllBoxers = findViewById(R.id.edtAllBoxers);
        edtAllBoxers.setVisibility(View.INVISIBLE);
        txtGetData = findViewById(R.id.txtGetData);
        btnGetAllData = findViewById(R.id.btnGetAllData);

        txtGetData.setOnClickListener(v -> {
            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
            parseQuery.getInBackground("MN4BPoLAeF", new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if(object !=null && e == null) {
                        txtGetData.setText(object.get("name").toString() + " - PunchPower: " +object.get("punchPower"));
                    } else if (e !=null ){
                        FancyToast.makeText(getApplicationContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    } else {
                        FancyToast.makeText(getApplicationContext(), "Get empty result from server!", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }
            });
        });

        btnGetAllData.setOnClickListener(v -> {
            ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
            queryAll.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e == null) {
                        if(objects.size() > 0) {
                            FancyToast.makeText(getApplicationContext(), "Success", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            StringBuilder bt = new StringBuilder();
                            for (ParseObject kickBoxer : objects) {
                                bt.append(kickBoxer.get("name").toString() + ": PunchPower: " +kickBoxer.get("punchPower") + " PunchSpeed: " +kickBoxer.get("punchSpeed") + "\n");
                                //Log.i("KickBoxer",kickBoxer.get("name").toString() + " - PunchPower: " +kickBoxer.get("punchPower"));
                            }
                            edtAllBoxers.setText(bt.toString());
                            edtAllBoxers.setVisibility(View.VISIBLE);
                        } else {
                            FancyToast.makeText(getApplicationContext(), "Could not find any KickBoxer", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                    } else {
                        FancyToast.makeText(getApplicationContext(), "Error: " + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }

                }
            });
        });
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
}