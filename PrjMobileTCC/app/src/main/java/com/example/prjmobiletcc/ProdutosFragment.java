package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosFragment extends Fragment {
    View ver;
    LinearLayout lytordem,lyttamanho,lytcor,lyttecido,
            lytcntnrtamanho,lytcntnrcor,lytcntnrtecido,
            fltsordem,fltstamanho,fltscor,fltstecido,lytcntnrProdutos,
            fltstamanhoquantidade,fltscorquantidade,fltstecidoquantidade;
    ImageView icupordem,icuptamanho,icupcor,icuptecido,icdownordem,icdowntamanho,icdowncor,icdowntecido;
    RadioGroup rdogpo;
    Button btnfiltrar;
    TextView txvordem,txvtamanho,txvcor,txvtecido;

    int numregistros, contadorTamanhos = 0,contadorCores = 0,contadorTecidos = 0;
    String rdoresul;

    Cnxbd bdcnx = new Cnxbd();
    Valores vlrs = new Valores();

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
        lytcntnrtamanho = ver.findViewById(R.id.lytTamanhocntnr);
        lytcntnrcor = ver.findViewById(R.id.lytCorescntnr);
        lytcntnrtecido = ver.findViewById(R.id.lytTecidoscntnr);

        fltstamanho = ver.findViewById(R.id.lytFlttamanho);
        fltstamanhoquantidade = ver.findViewById(R.id.lytFlttamanhoquant);
        fltscor = ver.findViewById(R.id.lytFltcores);
        fltscorquantidade = ver.findViewById(R.id.lytFltcoresquant);
        fltstecido = ver.findViewById(R.id.lytFlttecidos);
        fltstecidoquantidade = ver.findViewById(R.id.lytFlttecidosquant);

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
        lyttamanho.setOnClickListener(v->abrirefechar(lytcntnrtamanho,icuptamanho,icdowntamanho));
        lytcor.setOnClickListener(v->abrirefechar(lytcntnrcor,icupcor,icdowncor));
        lyttecido.setOnClickListener(v->abrirefechar(lytcntnrtecido,icuptecido,icdowntecido));
        btnfiltrar.setOnClickListener(v->criacomandosql());
        rdogpo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pegavlrRdo();
            }
        });
        Guardalogin grdlogin = new Guardalogin(requireContext());

        if (!grdlogin.loginexpiracao()){
            mudatela();
        }
        carregafiltros("Tamanho_Produto", fltstamanho,fltstamanhoquantidade);
        carregafiltros("Cor_Produto", fltscor,fltscorquantidade);
        carregafiltros("Tecido_Produto", fltstecido,fltstecidoquantidade);
        criacomandosql();

        return ver;
    }
    private void mudatela(){
        LoginFragment cntFramg = new LoginFragment();
        FragmentTransaction mudaFragm = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFragm.replace(R.id.contFrmnts, cntFramg);
        mudaFragm.addToBackStack(null);
        mudaFragm.commit();
    }
    private void fecharfechar(LinearLayout lyt, ImageView icUP, ImageView icDOWN){
        lyt.setVisibility(View.GONE);
        icUP.setVisibility(View.GONE);
        icDOWN.setVisibility(View.VISIBLE);
    }
    private void criacomandosql(){
        listatamanho.clear();
        listacor.clear();
        listatecido.clear();
        pegavlrCkb(fltstamanho, listatamanho);
        pegavlrCkb(fltscor, listacor);
        pegavlrCkb(fltstecido, listatecido);
        pegavlrRdo();
        fecharfechar(fltsordem,icupordem,icdownordem);
        fecharfechar(lytcntnrtamanho,icuptamanho,icdowntamanho);
        fecharfechar(lytcntnrcor,icupcor,icdowncor);
        fecharfechar(lytcntnrtecido,icuptecido,icdowntecido);
        String sql = "SELECT * FROM Produto ORDER BY";
        if (!listatamanho.isEmpty() || !listacor.isEmpty() || !listatecido.isEmpty()){
            sql += " CASE";
            String prioridade1 = "",prioridade2 = "";
            if (!listatecido.isEmpty()) {
                prioridade1 += " AND Tecido_Produto IN (" + formatarListaParaSQL(listatecido) + ")";
                prioridade2 += " OR Tecido_Produto IN (" + formatarListaParaSQL(listatecido) + ")";
            }
            if (!listatamanho.isEmpty()) {
                prioridade1 += " AND Tamanho_Produto IN (" + formatarListaParaSQL(listatamanho) + ")";
                prioridade2 += " OR Tamanho_Produto IN (" + formatarListaParaSQL(listatamanho) + ")";
            }
            if (!listacor.isEmpty()) {
                prioridade1 += " AND Cor_Produto IN (" + formatarListaParaSQL(listacor) + ")";
                prioridade2 += " OR Cor_Produto IN (" + formatarListaParaSQL(listacor) + ")";
            }
            if (!prioridade1.isEmpty()) {
                sql += " WHEN 1=1" + prioridade1 + " THEN 1";
            }
            if (!prioridade2.isEmpty()) {
                sql += " WHEN " + prioridade2.substring(4) + " THEN 2";
            }
            sql += " ELSE 3 END, " + rdoresul + ";";
        }else {
            sql += rdoresul+";";
        }
        System.out.println(sql);
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
            txvordem.setText("Ordem (" + selectedText + ") ");
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
            while(bdcnx.RS.next()){

                Criacntnr cntnrProduto = new Criacntnr(requireContext());
                String id_Produto = bdcnx.RS.getString("Id_Produto");
                String nome_Produto = bdcnx.RS.getString("Nome_Produto");
                String tamanho_Produto = bdcnx.RS.getString("Tamanho_Produto");
                String tecido_Produto = bdcnx.RS.getString("Tecido_Produto");
                String cor_Produto = bdcnx.RS.getString("Cor_Produto");
                String desconto_Produto = bdcnx.RS.getString("Desconto_Produto");
                String valor_Produto = bdcnx.RS.getString("Valor_Produto");
                cntnrProduto.valores(id_Produto,nome_Produto+", "+tamanho_Produto+", "+cor_Produto+", "+tecido_Produto,desconto_Produto,valor_Produto,requireContext());
                cntnrProduto.setOnClickListener(v->trocateladetalhes(id_Produto));
                lytcntnrProdutos.addView(cntnrProduto);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
    private void carregafiltros(String atributoTbl, LinearLayout lyt1, LinearLayout lyt2){
        try{
            lyt1.removeAllViews();
            lyt2.removeAllViews();
            bdcnx.entBanco(requireContext());
            bdcnx.RS = bdcnx.stmt.executeQuery("SELECT "+atributoTbl+", COUNT(*) AS Quantidade_Registros FROM Produto GROUP BY "+atributoTbl+" ORDER BY "+atributoTbl+" ASC;");
            while(bdcnx.RS.next()){
                String txt1,txt2,txt3;
                txt1 = bdcnx.RS.getString(atributoTbl);
                txt2 = bdcnx.RS.getString("Quantidade_Registros");
                if(txt2.equals("1")){txt3 = " Disponivel";}else{txt3 = " Disponiveis";}
                CheckBox ckb = new CheckBox(requireContext());
                ckb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, vlrs.converterintdp(40,requireContext())));
                ckb.setText(txt1);
                ckb.setButtonTintList(getResources().getColorStateList(R.color.cinzatema));
                ckb.setOnCheckedChangeListener((buttonView, isChecked)->ckcvalores(isChecked,atributoTbl));
                lyt1.addView(ckb);
                TextView txtv = new TextView(requireContext());
                txtv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,vlrs.converterintdp(40,requireContext())));
                txtv.setGravity(Gravity.CENTER);
                txtv.setText("("+txt2+txt3+")");
                lyt2.addView(txtv);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
    private void trocateladetalhes(String idproduto){

        vlrs.idproduto = idproduto;
        DetalhesFragment detalhesFragm = new DetalhesFragment();
        FragmentTransaction mudaFramg = requireActivity().getSupportFragmentManager().beginTransaction();
        mudaFramg.replace(R.id.contFrmnts, detalhesFragm);
        mudaFramg.addToBackStack(null);
        mudaFramg.commit();
        Toast.makeText(requireContext(), vlrs.idproduto, Toast.LENGTH_SHORT).show();
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