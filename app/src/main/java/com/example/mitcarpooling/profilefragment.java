package com.example.mitcarpooling;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class profilefragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profilefragment, container, false);
    }
}

 /*   public void startuserprofileActivity() {
        Intent intent = new Intent(getActivity(), userprofile.class);
        startActivity(intent);
    }
} */