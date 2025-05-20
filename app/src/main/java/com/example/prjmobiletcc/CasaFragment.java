package com.example.prjmobiletcc;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CasaFragment extends Fragment {

    public CasaFragment() {
        // Required empty public constructor
    }
    View ver;
    ImageView imgv;
    Cnxbd bdcnx = new Cnxbd();
    String url = "http://"+bdcnx.iP+"/a1/git%20hub/Tcc-Web/assets/img/mdl1home.jpg";
    LinearLayout conteinerProduto, contprodutos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ver = inflater.inflate(R.layout.fragment_casa, container, false);
        imgv = ver.findViewById(R.id.imgvCasa1);
        conteinerProduto = ver.findViewById(R.id.cntnrProduto);
        contprodutos = ver.findViewById(R.id.layoutContcasa);


        conteinerProduto.setOnClickListener(view -> trocatelafrag());
        criacontainer();
        crrgImg();
        return ver;
    }
    private void criacontainer(){
        //Criar e adicionar a view do produto
        Criacntnr produtoView = new Criacntnr(requireContext());
        produtoView.valores("product-1.png","Produto 1 exemplo","40,00","20,20","22");
        produtoView.setOnClickListener(view -> trocatelafrag());
        contprodutos.addView(produtoView);
    }
    private void crrgImg(){
        imgv.setImageDrawable(null);
        new CarregaImagem(imgv).execute(url);
    }
    public void trocatelafrag(){
        Valores vl = new Valores();
        DetalhesFragment detalhesFragm = new DetalhesFragment();
        FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFragm.replace(R.id.contFrmnts, detalhesFragm);
        mudaFragm.addToBackStack(null);
        mudaFragm.commit();
        Toast.makeText(requireContext(), "id e "+vl.idProduto,Toast.LENGTH_SHORT).show();
    }
}