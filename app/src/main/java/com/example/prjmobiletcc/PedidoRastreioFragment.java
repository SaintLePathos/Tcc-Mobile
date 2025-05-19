package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PedidoRastreioFragment extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaBundle) {
        View ver = inflater.inflate(R.layout.fragment_pedido_rastreio, container, false);
        return ver;
    }
}