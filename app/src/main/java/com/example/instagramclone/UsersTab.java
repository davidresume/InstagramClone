package com.example.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;


public class UsersTab extends Fragment {
    private ListView listView;
    private ArrayList arrayList;
    private ArrayAdapter arrayAdapter;

    public UsersTab() {
        // Required empty public constructor
    }

    public static UsersTab newInstance(String param1, String param2) {
        UsersTab fragment = new UsersTab();
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
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);
        listView = view.findViewById(R.id.listView);
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayList);


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
}