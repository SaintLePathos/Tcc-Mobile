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

import java.sql.SQLException;


public class CasaFragment extends Fragment {

    public CasaFragment() {
        // Required empty public constructor
    }
    View ver;
    ImageView imgv;
    Cnxbd bdcnx = new Cnxbd();
    String url = "http://"+bdcnx.iP+"/a1/git%20hub/Tcc-Web/assets/img/slide-1.png";
    LinearLayout conteinerProduto, contprodutos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ver = inflater.inflate(R.layout.fragment_casa, container, false);
        imgv = ver.findViewById(R.id.imgvCasa1);
        conteinerProduto = ver.findViewById(R.id.layoutContcasa);
        contprodutos = ver.findViewById(R.id.layoutContcasa);

        criacontainer();
        crrgImg();
        return ver;
    }
    private void criacontainer(){
        contprodutos.removeAllViews();
        String sql = "SELECT * FROM Produto";
        try{
            bdcnx.entBanco(requireContext());
            bdcnx.RS = bdcnx.stmt.executeQuery(sql);
            while(bdcnx.RS.next()){
                String id_Produto,
                        nome_Produto,
                        valor_Produto,
                        desconto_Produto,
                        tamanho_Produto,
                        tecido_Produto,
                        cor_Produto;
                Criacntnr cntnrProduto = new Criacntnr(requireContext());
                id_Produto = bdcnx.RS.getString("Id_Produto");
                nome_Produto = bdcnx.RS.getString("Nome_Produto");
                tamanho_Produto = bdcnx.RS.getString("Tamanho_Produto");
                tecido_Produto = bdcnx.RS.getString("Tecido_Produto");
                cor_Produto = bdcnx.RS.getString("Cor_Produto");
                desconto_Produto = bdcnx.RS.getString("Desconto_Produto");
                valor_Produto = bdcnx.RS.getString("Valor_Produto");
                cntnrProduto.valores(id_Produto,nome_Produto+", "+tamanho_Produto+", "+cor_Produto+", "+tecido_Produto,desconto_Produto,valor_Produto,requireContext());
                cntnrProduto.setOnClickListener(v->trocateladetalhes(id_Produto));
                contprodutos.addView(cntnrProduto);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
    private void trocateladetalhes(String idproduto){
        Valores vlrs = new Valores();
        vlrs.idproduto = idproduto;
        DetalhesFragment detalhesFragm = new DetalhesFragment();
        FragmentTransaction mudaFramg = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFramg.replace(R.id.contFrmnts, detalhesFragm);
        mudaFramg.addToBackStack(null);
        mudaFramg.commit();
        Toast.makeText(requireContext(), vlrs.idproduto, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(requireContext(), "id e "+vl.idproduto,Toast.LENGTH_SHORT).show();
    }
}