package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CheckrevisaoFragment extends Fragment {

    public CheckrevisaoFragment() {
        // Required empty public constructor
    }
    View ver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ver = inflater.inflate(R.layout.fragment_checkrevisao, container, false);
        return ver;
    }
}