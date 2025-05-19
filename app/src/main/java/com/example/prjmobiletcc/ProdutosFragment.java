package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ProdutosFragment extends Fragment {
    View ver;
    LinearLayout lytordem,lyttamanho,lytcor,lyttecido,fltsordem,fltstamanho,fltscor,fltstecido;
    ImageView icupordem,icuptamanho,icupcor,icuptecido,icdownordem,icdowntamanho,icdowncor,icdowntecido;
    public ProdutosFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_produtos, container, false);
        lytordem = ver.findViewById(R.id.lytOrdem);
        lyttamanho = ver.findViewById(R.id.lytTamanhos);
        lytcor = ver.findViewById(R.id.lytCores);
        lyttecido = ver.findViewById(R.id.lytTecidos);
        fltsordem = ver.findViewById(R.id.lytFltordem);
        fltstamanho = ver.findViewById(R.id.lytFlttamanho);
        fltscor = ver.findViewById(R.id.lytFltcores);
        fltstecido = ver.findViewById(R.id.lytFlttecidos);
        icupordem = ver.findViewById(R.id.iconUPordem);
        icuptamanho = ver.findViewById(R.id.iconUPtamanho);
        icupcor = ver.findViewById(R.id.iconUPcor);
        icuptecido = ver.findViewById(R.id.iconUPtecido);
        icdownordem = ver.findViewById(R.id.iconDOWNordem);
        icdowntamanho = ver.findViewById(R.id.iconDOWNtamanho);
        icdowncor = ver.findViewById(R.id.iconDOWNcor);
        icdowntecido = ver.findViewById(R.id.iconDOWNtecido);
        lytordem.setOnClickListener(v->abreordem());
        lyttamanho.setOnClickListener(v->abretamanho());
        lytcor.setOnClickListener(v->abrecores());
        lyttecido.setOnClickListener(v->abretecidos());
        return ver;
    }
    private void abreordem(){
        fltsordem.setVisibility(fltsordem.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icupordem.setVisibility(icupordem.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icdownordem.setVisibility(icdownordem.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

    }
    private void abretamanho(){
        fltstamanho.setVisibility(fltstamanho.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icuptamanho.setVisibility(icuptamanho.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icdowntamanho.setVisibility(icdowntamanho.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
    private void abrecores(){
        fltscor.setVisibility(fltscor.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icupcor.setVisibility(icupcor.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icdowncor.setVisibility(icdowncor.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
    private void abretecidos(){
        fltstecido.setVisibility(fltstecido.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icuptecido.setVisibility(icuptecido.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icdowntecido.setVisibility(icdowntecido.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
}