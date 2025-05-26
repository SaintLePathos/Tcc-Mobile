package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PedidoRastreioFragment extends BottomSheetDialogFragment {
    TextView txtvnumeroped, txtdtenvio;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaBundle) {
        View ver = inflater.inflate(R.layout.fragment_pedido_rastreio, container, false);
        txtdtenvio = ver.findViewById(R.id.txtvRastreiodtenvio);
        txtvnumeroped = ver.findViewById(R.id.txtvRastreionumeroped);

        Valores vlrs = new Valores();

        txtvnumeroped.setText("Rastreio do Pedido Nº "+vlrs.idpedido);
        txtdtenvio.setText(vlrs.dtenviopedido);

        return ver;
    }
}