package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CheckenderecoFragment extends Fragment {

    public CheckenderecoFragment() {
        // Required empty public constructor
    }
    View ver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ver = inflater.inflate(R.layout.fragment_checkendereco, container, false);

        Button btnCheckpagamento = ver.findViewById(R.id.btnCkpagamento);
        btnCheckpagamento.setOnClickListener(v ->{
            CheckpagamentoFragment fragckpag = new CheckpagamentoFragment();
            FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
            mudaFragm.replace(R.id.contFrmnts, fragckpag);
            mudaFragm.addToBackStack(null);
            mudaFragm.commit();
        });

        return ver;
    }

}