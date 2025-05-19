package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CheckpagamentoFragment extends Fragment {

    public CheckpagamentoFragment() {
        // Required empty public constructor
    }
    View ver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ver = inflater.inflate(R.layout.fragment_checkpagamento, container, false);
        Button btnckrevisao = ver.findViewById(R.id.btnCkrevisao);
        btnckrevisao.setOnClickListener(v -> {
            CheckrevisaoFragment fragmRevisao = new CheckrevisaoFragment();
            FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
            mudaFragm.replace(R.id.contFrmnts, fragmRevisao);
            mudaFragm.addToBackStack(null);
            mudaFragm.commit();
        });

        return ver;
    }
}