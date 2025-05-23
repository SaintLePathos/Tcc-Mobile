package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UsuarioenderecoFragment extends Fragment {

    LinearLayout lytcntnrenderecos,btnmostrasenderecos, btnmenosdetalhesend, btnmaisdetalhesend, cntnrdetalhes;
    TextView txtvnomerua, txtvdetalhesend;
    ImageView icenddown,icendup;
    View ver;
    Criacntnrenderecos criacntnrenderecos;
    public UsuarioenderecoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_usuarioendereco, container, false);
        lytcntnrenderecos = ver.findViewById(R.id.lytCntnrenderecos);
        btnmostrasenderecos = ver.findViewById(R.id.lytMaisenderec);
        icenddown = ver.findViewById(R.id.icEndDOWN);
        icendup = ver.findViewById(R.id.icEndUP);
        btnmaisdetalhesend = ver.findViewById(R.id.lytMaisdetalhesenderec);
        btnmenosdetalhesend = ver.findViewById(R.id.lytMenosdetalhesenderec);
        txtvnomerua = ver.findViewById(R.id.txtvNomerua);
        txtvdetalhesend = ver.findViewById(R.id.txtvDetalhesendereco);
        cntnrdetalhes = ver.findViewById(R.id.lytDetalhesenderec);

        btnmaisdetalhesend.setOnClickListener(v->ocdetalhes(cntnrdetalhes,btnmaisdetalhesend));
        btnmenosdetalhesend.setOnClickListener(v->ocdetalhes(cntnrdetalhes,btnmaisdetalhesend));

        btnmostrasenderecos.setOnClickListener(v->abrirefechar(lytcntnrenderecos,icendup,icenddown));

        criacntnrenderecos = new Criacntnrenderecos(requireContext());
        lytcntnrenderecos.addView(criacntnrenderecos);

        return ver;
    }
    private void ocdetalhes(LinearLayout lyt1, LinearLayout lyt2){
        lyt1.setVisibility(lyt1.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        lyt2.setVisibility(lyt2.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
    private void abrirefechar(LinearLayout lyt, ImageView icUP, ImageView icDOWN){
        lyt.setVisibility(lyt.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icUP.setVisibility(icUP.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icDOWN.setVisibility(icDOWN.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }


}