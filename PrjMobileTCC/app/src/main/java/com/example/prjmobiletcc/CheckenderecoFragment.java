package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class CheckenderecoFragment extends Fragment {

    public CheckenderecoFragment() {
        // Required empty public constructor
    }
    View ver;
    Button btnCheckpagamento;
    LinearLayout lytcntnrend;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_checkendereco, container, false);
        btnCheckpagamento = ver.findViewById(R.id.btnCkpagamento);
        lytcntnrend = ver.findViewById(R.id.lytCktendereco);

        btnCheckpagamento.setOnClickListener(v ->mudatela());
        carregamento();

        return ver;
    }
    private void carregamento(){

    }
    private void mudatela(){
        CheckpagamentoFragment fragckpag = new CheckpagamentoFragment();
        FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFragm.replace(R.id.contFrmnts, fragckpag);
        mudaFragm.addToBackStack(null);
        mudaFragm.commit();
    }

}