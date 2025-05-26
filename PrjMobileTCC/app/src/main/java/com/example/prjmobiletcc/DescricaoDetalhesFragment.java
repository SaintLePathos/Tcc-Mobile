package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DescricaoDetalhesFragment extends BottomSheetDialogFragment {
    View ver;
    TextView txtvdescricao;
    Valores vlrs = new Valores();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaBundle){
        ver = inflater.inflate(R.layout.fragment_detalhes_descricao, container, false);
        txtvdescricao = ver.findViewById(R.id.txtvDescricao);
        txtvdescricao.setText(vlrs.descricaoProduto);
        return ver;
    }
}
