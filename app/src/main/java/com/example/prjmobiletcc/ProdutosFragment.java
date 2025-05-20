package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosFragment extends Fragment {
    View ver;
    LinearLayout lytordem,lyttamanho,lytcor,lyttecido,fltsordem,fltstamanho,fltscor,fltstecido,lytcntnrProdutos;
    ImageView icupordem,icuptamanho,icupcor,icuptecido,icdownordem,icdowntamanho,icdowncor,icdowntecido;
    RadioGroup rdogpo;
    Button btnfiltrar;
    TextView txvordem,txvtamanho,txvcor,txvtecido;
    Cnxbd bdcnx = new Cnxbd();
    Valores vlrs = new Valores();
    int numregistros, contadorTamanhos = 0,contadorCores = 0,contadorTecidos = 0;
    String rdoresul;
    List<String> listatamanho = new ArrayList<>();
    List<String> listacor = new ArrayList<>();
    List<String> listatecido = new ArrayList<>();
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
        lytcntnrProdutos = ver.findViewById(R.id.lytProdutoscntnrprodutos);
        rdogpo = ver.findViewById(R.id.rdoGrpordem);
        btnfiltrar = ver.findViewById(R.id.btnFiltrar);
        txvordem = ver.findViewById(R.id.txvOrdem);
        txvtamanho = ver.findViewById(R.id.txvTamanho);
        txvcor = ver.findViewById(R.id.txvCor);
        txvtecido = ver.findViewById(R.id.txvTecido);


        lytordem.setOnClickListener(v->abrirefechar(fltsordem,icupordem,icdownordem));
        lyttamanho.setOnClickListener(v->abrirefechar(fltstamanho,icuptamanho,icdowntamanho));
        lytcor.setOnClickListener(v->abrirefechar(fltscor,icupcor,icdowncor));
        lyttecido.setOnClickListener(v->abrirefechar(fltstecido,icuptecido,icdowntecido));
        btnfiltrar.setOnClickListener(v->criacomandosql());
        rdogpo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pegavlrRdo();
            }
        });

        carregafiltros("Tamanho_Produto", fltstamanho);
        carregafiltros("Cor_Produto", fltscor);
        carregafiltros("Tecido_Produto", fltstecido);

        carregamentoprodutos("SELECT * FROM Produto");

        return ver;
    }
    private void criacomandosql(){
        pegavlrCkb(fltstamanho, listatamanho);
        pegavlrCkb(fltscor, listacor);
        pegavlrCkb(fltstecido, listatecido);
        pegavlrRdo();
        String sql = "SELECT * FROM Produto ORDER BY";
        if (!listatamanho.isEmpty() || !listacor.isEmpty() || !listatecido.isEmpty()){
            sql += " CASE WHEN 1=1";
            if (!listatecido.isEmpty()) {
                sql += " AND Tecido_Produto IN (" + formatarListaParaSQL(listatecido) + ")";
            }
            if (!listatamanho.isEmpty()) {
                sql += " AND Tamanho_Produto IN (" + formatarListaParaSQL(listatamanho) + ")";
            }
            if (!listacor.isEmpty()) {
                sql += " AND Cor_Produto IN (" + formatarListaParaSQL(listacor) + ")";
            }
            sql += " THEN 1 ELSE 2 END, " + rdoresul + ";";
        }else {
            sql += rdoresul+";";
        }
        carregamentoprodutos(sql);
    }
    private String formatarListaParaSQL(List<String> lista) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            sb.append("'").append(lista.get(i)).append("'");
            if (i < lista.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    private void pegavlrRdo(){
        int selectedId = rdogpo.getCheckedRadioButtonId(); // Obtém o ID do botão selecionado
        if (selectedId != -1) { // Verifica se algum botão está marcado
            RadioButton selectedRadioButton = ver.findViewById(selectedId);
            String selectedText = selectedRadioButton.getText().toString(); // Obtém o texto do botão
            txvordem.setText("Ordem ("+selectedText+")");
            switch (selectedText){
                case "Novidades":
                    rdoresul = " Id_Produto DESC";
                    break;
                case "Desconto":
                    rdoresul = " Desconto_Produto DESC";
                    break;
                case "Maior Preço":
                    rdoresul = " Valor_Produto DESC";
                    break;
                case "Menor Preço":
                    rdoresul = " Valor_Produto ASC";
                    break;
                case "A a Z":
                    rdoresul = " Nome_Produto ASC";
                    break;
                case "Z a A":
                    rdoresul = " Nome_Produto DESC";
                    break;
                default:
                    rdoresul = " Id_Produto DESC";
                    break;
            }
        }
    }
    private void pegavlrCkb(LinearLayout lyt, List<String> list){
        for (int i = 0; i < lyt.getChildCount(); i++) {
            View view = lyt.getChildAt(i);
            if (view instanceof CheckBox && ((CheckBox) view).isChecked()) {
                list.add(((CheckBox) view).getText().toString());
            }
        }
    }
    private void carregamentoprodutos(String sql){
        lytcntnrProdutos.removeAllViews();
        try{
            bdcnx.entBanco(requireContext());
            bdcnx.RS = bdcnx.stmt.executeQuery(sql);
            bdcnx.RS.last();
            numregistros = bdcnx.RS.getRow();
            bdcnx.RS.beforeFirst();
            System.out.println("Total de registros: " + numregistros);
            while(bdcnx.RS.next()){
                String id_Produto,
                        nome_Produto,
                        img_Produto,
                        valor_Produto,
                        desconto_Produto,
                        tamanho_Produto,
                        tecido_Produto,
                        cor_Produto;
                Criacntnr cntnrProduto = new Criacntnr(requireContext());
                id_Produto = bdcnx.RS.getString("Id_Produto");
                img_Produto = bdcnx.RS.getString("Img_Produto");
                nome_Produto = bdcnx.RS.getString("Nome_Produto");
                tamanho_Produto = bdcnx.RS.getString("Tamanho_Produto");
                tecido_Produto = bdcnx.RS.getString("Tecido_Produto");
                cor_Produto = bdcnx.RS.getString("Cor_Produto");
                desconto_Produto = bdcnx.RS.getString("Desconto_Produto");
                valor_Produto = bdcnx.RS.getString("Valor_Produto");
                String valordesconto = vlrs.calculades(valor_Produto,desconto_Produto);
                System.out.println(nome_Produto+"");
                cntnrProduto.valores(img_Produto,nome_Produto+","+tamanho_Produto+","+cor_Produto+","+tecido_Produto,valordesconto,valor_Produto,id_Produto);
                cntnrProduto.setOnClickListener(v->trocateladetalhes(id_Produto));
                lytcntnrProdutos.addView(cntnrProduto);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    private void carregafiltros(String atributoTbl, LinearLayout lyt){
        try{
            lyt.removeAllViews();
            bdcnx.entBanco(requireContext());
            bdcnx.RS = bdcnx.stmt.executeQuery("SELECT "+atributoTbl+", COUNT(*) AS Quantidade_Registros FROM Produto GROUP BY "+atributoTbl+" ORDER BY Quantidade_Registros DESC;");
            while(bdcnx.RS.next()){
                String txt1;
                txt1 = bdcnx.RS.getString(atributoTbl);
                CheckBox ckb = new CheckBox(requireContext());
                ckb.setText(txt1);
                ckb.setButtonTintList(getResources().getColorStateList(R.color.cinzatema));
                ckb.setOnCheckedChangeListener((buttonView, isChecked)->ckcvalores(isChecked,atributoTbl));
                lyt.addView(ckb);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    private void trocateladetalhes(String idproduto){

        vlrs.idProduto = idproduto;
        DetalhesFragment detalhesFragm = new DetalhesFragment();
        FragmentTransaction mudaFramg = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFramg.replace(R.id.contFrmnts, detalhesFragm);
        mudaFramg.addToBackStack(null);
        mudaFramg.commit();
        Toast.makeText(requireContext(), vlrs.idProduto, Toast.LENGTH_SHORT).show();
    }
    private void abrirefechar(LinearLayout lyt, ImageView icUP, ImageView icDOWN){
        lyt.setVisibility(lyt.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icUP.setVisibility(icUP.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        icDOWN.setVisibility(icDOWN.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
    private void ckcvalores(boolean ischeck,String ckbgrp){
        if (ischeck) {
            if (ckbgrp == "Tamanho_Produto"){
                contadorTamanhos = contadorTamanhos + 1;
                txvtamanho.setText("Tamanhos ("+contadorTamanhos+")");
            }
            if (ckbgrp == "Cor_Produto"){
                contadorCores = contadorCores + 1;
                txvcor.setText("Cores ("+contadorCores+")");
            }
            if (ckbgrp == "Tecido_Produto"){
                contadorTecidos = contadorTecidos + 1;
                txvtecido.setText("Tecidos ("+contadorTecidos+")");
            }
        } else {
            if (ckbgrp == "Tamanho_Produto"){
                contadorTamanhos = contadorTamanhos - 1;
                txvtamanho.setText("Tamanhos ("+contadorTamanhos+")");
            }
            if (ckbgrp == "Cor_Produto"){
                contadorCores = contadorCores - 1;
                txvcor.setText("Cores ("+contadorCores+")");
            }
            if (ckbgrp == "Tecido_Produto"){
                contadorTecidos = contadorTecidos - 1;
                txvtecido.setText("Tecidos ("+contadorTecidos+")");
            }
        }
    }
}