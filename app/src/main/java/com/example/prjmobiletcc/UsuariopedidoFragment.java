package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class UsuariopedidoFragment extends Fragment {

    public UsuariopedidoFragment() {
        // Required empty public constructor
    }
    View ver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_usuariopedido, container, false);
        Button btnrastrearpedido = ver.findViewById(R.id.btnRastrearpedido);
        btnrastrearpedido.setOnClickListener(v ->{
            PedidoRastreioFragment fragmpedidorastreio = new PedidoRastreioFragment();
            fragmpedidorastreio.show(getChildFragmentManager(), "BottomSheetDialog");
        });
        return ver;
    }
}