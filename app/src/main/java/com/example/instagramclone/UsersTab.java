package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


public class UsersTab extends Fragment  implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;

    public UsersTab() {
        // Required empty public constructor
    }

    public static UsersTab newInstance(String param1, String param2) {
        UsersTab fragment = new UsersTab();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);
        listView = view.findViewById(R.id.listView);
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList);

        listView.setOnItemClickListener(UsersTab.this);
        listView.setOnItemLongClickListener(UsersTab.this);

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Fetching users list from the server...");
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    if(users.size() > 0 ) {
                        for (ParseUser user : users) {
                            arrayList.add(user.getUsername());
                        }
                        listView.setAdapter(arrayAdapter);
                    } else {
                        FancyToast.makeText(getContext(),"Could not find any other user...",
                                FancyToast.LENGTH_SHORT, FancyToast.WARNING,false).show();
                    }
                } else {
                    FancyToast.makeText(getContext(),e.getMessage(),
                            FancyToast.LENGTH_SHORT, FancyToast.ERROR,false).show();
                }
                progressDialog.dismiss();
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(),UsersPosts.class);
        intent.putExtra("username", arrayList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("username", arrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user != null && e == null) {
                    PrettyDialog prettyDialog = new PrettyDialog(getContext());
                    prettyDialog.setTitle(user.getUsername() + "'s Info")
                            .setMessage(getUserProfileValue(user,"profileBio") + "\n" +
                                    getUserProfileValue(user,"profileProfession") + "\n" +
                                    getUserProfileValue(user, "profileHobbies") + "\n" +
                                    getUserProfileValue(user,"profileFavSport"))
                            .setIcon(R.drawable.person)
                            .addButton("OK",                    //button text
                                    R.color.pdlg_color_white,       // button text color
                                    R.color.pdlg_color_green,       // button background color
                                    new PrettyDialogCallback() {    //Button OnClick listener
                                        @Override
                                        public void onClick() {
                                            prettyDialog.dismiss();
                                        }
                                    })
                            .show();

                    //                    FancyToast.makeText(getContext(),user.getUsername(),
//                            FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,false).show();
                } else {
                    if (e != null) {
                        FancyToast.makeText(getContext(),"Unknown erro:" + e.getMessage(),
                                FancyToast.LENGTH_SHORT, FancyToast.ERROR,false).show();

                    } else {
                        FancyToast.makeText(getContext(),"For unknown reasons, Could not find the user data ...",
                                FancyToast.LENGTH_SHORT, FancyToast.WARNING,false).show();

                    }
                }
            }

        });
        return true;
    }
    private String getUserProfileValue(ParseUser user, String field) {
        String userProperty = "";
        if (user.get(field) != null ) {
            userProperty = user.get(field).toString();
        }
        return userProperty;
    }
}