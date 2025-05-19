package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DescricaoDetalhesFragment extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaBundle){
        View view = inflater.inflate(R.layout.fragment_detalhes_descricao, container, false);
        return view;
    }
}
