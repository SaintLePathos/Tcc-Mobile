package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.prjmobiletcc.DescricaoDetalhesFragment;

public class DetalhesFragment extends Fragment {

    public DetalhesFragment() {
        // Required empty public constructor
    }
    View ver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_detalhes, container,false);
        // Inflate the layout for this fragment
        Button btnmostradescricao = ver.findViewById(R.id.btnDescricao);
        btnmostradescricao.setOnClickListener(v ->{
            DescricaoDetalhesFragment bottomSheetDialog = new DescricaoDetalhesFragment();
            bottomSheetDialog.show(getChildFragmentManager(), "BottomSheetDialog");
        });
        return ver;
    }

}