package com.example.instagramclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ProfileTab extends Fragment {
    private EditText edtProfileName, edtProfileBio, edtProfileProfession, edtProfileHobbies, edtProfileFavSport;
    private Button btnUpdateInfo;


    public ProfileTab() {
        // Required empty public constructor
    }

    public static ProfileTab newInstance(String param1, String param2) {
        ProfileTab fragment = new ProfileTab();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileFavSport=view.findViewById(R.id.edtProfileFavoriteSports);
        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);

        ParseUser parseUser = ParseUser.getCurrentUser();

        edtProfileName.setText(getProfileField(parseUser,"profileName"));
        edtProfileBio.setText(getProfileField(parseUser,"profileBio"));
        edtProfileProfession.setText(getProfileField(parseUser, "profileProfession"));
        edtProfileHobbies.setText(getProfileField(parseUser, "profileHobbies"));
        edtProfileFavSport.setText(getProfileField(parseUser, "profileFavSport"));

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName", edtProfileName.getText().toString());
                parseUser.put("profileBio", edtProfileBio.getText().toString());
                parseUser.put("profileProfession", edtProfileProfession.getText().toString());
                parseUser.put("profileHobbies", edtProfileHobbies.getText().toString());
                parseUser.put("profileFavSport", edtProfileFavSport.getText().toString());
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            FancyToast.makeText(view.getContext(),
                                    parseUser.get("username").toString() + "'s profile is save Successfully!",
                                    FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                        } else {
                            FancyToast.makeText(view.getContext(),
                                    e.getMessage(),
                                    FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                        }
                    }
                });
            }
        });

        return view;
    }

    private String getProfileField(ParseUser user, String filed) {
        if(user.get(filed) != null) {
            return user.get(filed).toString();
        } else {
            return "";
        }
    }
}