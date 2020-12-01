package com.example.instagramclone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class SharePictureTab extends Fragment {

    public SharePictureTab() {
        // Required empty public constructor
    }

    public static SharePictureTab newInstance(String param1, String param2) {
        SharePictureTab fragment = new SharePictureTab();
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
        View view = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        return view;
    }
}