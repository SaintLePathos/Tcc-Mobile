package com.example.prjmobiletcc;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prjmobiletcc.DescricaoDetalhesFragment;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DetalhesFragment extends Fragment {

    public DetalhesFragment() {
        // Required empty public constructor
    }
    View ver;
    Button btnslidedireita,btnslideesquerda,btnadcionanocar,btnmostradescricao;
    ImageView imgvslide,imgvcx1,imgvcx2,imgvcx3,imgvcx4;
    TextView txtnome, txtdisponibi,
            txtvalorvista,txtvalorcartao,txtvalordes,txtpagvista,txtpagcartao,
            txtdesconto,txtvendido,txtestoque;
    Guardalogin grdlogin;

    Valores vlrs = new Valores();
    Carrinho carrinho = new Carrinho();
    LinearLayout lytimg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ver = inflater.inflate(R.layout.fragment_detalhes, container,false);
        imgvslide = ver.findViewById(R.id.imgvSlide);
        btnslidedireita = ver.findViewById(R.id.btnSlidedireita);
        btnslideesquerda = ver.findViewById(R.id.btnSlideesquerda);
        imgvcx1 = ver.findViewById(R.id.imgvCaixa1);
        imgvcx2 = ver.findViewById(R.id.imgvCaixa2);
        imgvcx3 = ver.findViewById(R.id.imgvCaixa3);
        imgvcx4 = ver.findViewById(R.id.imgvCaixa4);
        txtnome = ver.findViewById(R.id.txtvNome);
        txtdisponibi = ver.findViewById(R.id.txtvDisponibilidade);
        txtvalordes = ver.findViewById(R.id.txtvCnt1valordes);
        txtvalorvista = ver.findViewById(R.id.txtvCnt1valor1);
        txtvalorcartao = ver.findViewById(R.id.txtvCnt1valor2);
        txtpagvista = ver.findViewById(R.id.txtvCnt1desconto);
        txtpagcartao = ver.findViewById(R.id.txtvCnt1quantcartao);
        txtdesconto = ver.findViewById(R.id.txtvQuantdesconto);
        txtvendido = ver.findViewById(R.id.txtvQuantvendido);
        txtestoque = ver.findViewById(R.id.txtvQuantdisponivel);
        btnadcionanocar = ver.findViewById(R.id.btnAdicionacarrinho);
        lytimg = ver.findViewById(R.id.lytCntnrbcimg);
        grdlogin = new Guardalogin(requireContext());


        btnmostradescricao = ver.findViewById(R.id.btnDescricao);

        btnmostradescricao.setOnClickListener(v ->verdescricao());
        btnadcionanocar.setOnClickListener(v->adicionarcar());

        carregamento();
        return ver;
    }
    private void adicionarcar(){
        try {
            carrinho.adicionarOuAtualizarProduto(vlrs.idproduto.toString(), 1, requireContext());
            carrinho.consultarProdutos(requireContext());
            Toast.makeText(requireContext(),"Produto adicionado ao carrinho",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(requireContext(),"Erro ao adicionar ao carrinho",Toast.LENGTH_SHORT).show();
        }
    }
    private void verdescricao(){
        carrinho.limparCarrinho(requireContext());
        DescricaoDetalhesFragment bottomSheetDialog = new DescricaoDetalhesFragment();
        bottomSheetDialog.show(getChildFragmentManager(), "BottomSheetDialog");
    }

    private void carregamento(){
        try{
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(requireContext());
            bdcnx.RS = bdcnx.stmt.executeQuery("SELECT " +
                    "p.Id_Produto," +
                    "p.Nome_Produto," +
                    "p.Descricao_Produto," +
                    "p.Valor_Produto," +
                    "p.Desconto_Produto," +
                    "p.Tamanho_Produto," +
                    "p.Quantidade_Produto," +
                    "p.Tecido_Produto," +
                    "p.Cor_Produto," +
                    " COALESCE(COUNT(pp.Id_Pedido), 0) AS Quantidade_Total_Produto_Pedido " +
                    "FROM Produto p " +
                    "LEFT JOIN Produto_Pedido pp ON pp.Id_Produto = p.Id_Produto " +
                    "WHERE p.Id_Produto = "+vlrs.idproduto+" " +
                    "GROUP BY p.Id_Produto, p.Id_Fornecedor, p.Nome_Produto, p.Descricao_Produto, " +
                    "p.Valor_Produto, p.Desconto_Produto, p.Tamanho_Produto, p.Quantidade_Produto, " +
                    "p.Tecido_Produto, p.Cor_Produto;");
            if(bdcnx.RS.next()){
                String nome_Produto,

                        descricao_Produto,
                        valor_Produto,
                        desconto_Produto,
                        tamanho_Produto,
                        quantidade_Produto,
                        tecido_Produto,
                        cor_Produto,
                        verda_Produto;
                nome_Produto = bdcnx.RS.getString("Nome_Produto");
                descricao_Produto = bdcnx.RS.getString("Descricao_Produto");
                valor_Produto = bdcnx.RS.getString("Valor_Produto");
                desconto_Produto = bdcnx.RS.getString("Desconto_Produto");
                tamanho_Produto = bdcnx.RS.getString("Tamanho_Produto");
                quantidade_Produto = bdcnx.RS.getString("Quantidade_Produto");
                tecido_Produto = bdcnx.RS.getString("Tecido_Produto");
                cor_Produto = bdcnx.RS.getString("Cor_Produto");
                verda_Produto = bdcnx.RS.getString("Quantidade_Total_Produto_Pedido");
                String valordesconto = vlrs.calculades(valor_Produto,desconto_Produto);
                txtnome.setText(nome_Produto+", "+tecido_Produto+", "+cor_Produto+", "+tamanho_Produto);
                if (quantidade_Produto.equals("0")){
                    txtdisponibi.setText("PRODUTO ESGOTADO");
                    txtdisponibi.setTextColor(ContextCompat.getColor(requireContext(), R.color.vermelhodinheiro));
                    btnadcionanocar.setVisibility(View.GONE);
                }else{
                    txtdisponibi.setText("PRODUTO DISPONIVEL");
                }
                txtvalordes.setPaintFlags(txtvalordes.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                txtvalordes.setText("de R$" + valordesconto + " para");
                txtpagvista.setText("No pix com " + desconto_Produto + "% de desconto");
                int vezesnocartao = 10;
                txtpagcartao.setText("em ate " + 10 + "x vezes sem juros no cartão");
                txtvalorvista.setText("R$" + valor_Produto);
                String valorcartao = vlrs.calculacartao(valor_Produto,vezesnocartao);
                txtvalorcartao.setText("R$" + valorcartao);
                txtdesconto.setText(desconto_Produto+"%");
                txtvendido.setText(verda_Produto);
                txtestoque.setText(quantidade_Produto);


                vlrs.descricaoProduto = descricao_Produto;
                carregaimg();
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
    private void carregaimg(){
        lytimg.removeAllViews();
        try {
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(requireContext());
            bdcnx.RS = bdcnx.stmt.executeQuery("SELECT Url_ImgProduto FROM Imagem_Produto WHERE Id_Produto = " + vlrs.idproduto + " ORDER BY Ordem_ImgProduto ASC");
            while (bdcnx.RS.next()) {
                String imgurl = bdcnx.RS.getString("Url_ImgProduto");
                    criacntnr(bdcnx.urlimgsrv + imgurl);

            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        try {
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(requireContext());
            bdcnx.RS = bdcnx.stmt.executeQuery("SELECT Url_ImgProduto FROM Imagem_Produto WHERE Id_Produto = " + vlrs.idproduto + " ORDER BY Ordem_ImgProduto ASC");


            if (bdcnx.RS.next()) {
                String endeimg = bdcnx.RS.getString("Url_ImgProduto");
                new CarregaImagem(imgvslide).execute(bdcnx.urlimgsrv+endeimg);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }
    }
    private void criacntnr(String imgende){
        // Criando um CardView
        CardView cardView = new CardView(requireContext());
        cardView.setLayoutParams(new LinearLayout.LayoutParams(
                dpToPx(75), // Largura convertida para pixels
                dpToPx(75)  // Altura convertida para pixels
        ));
        cardView.setCardElevation(4);
        cardView.setRadius(dpToPx(15));

        // Definindo margens
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardView.getLayoutParams();
                params.setMargins(dpToPx(5), 0, dpToPx(5), 0);
                params.weight = 1;
                cardView.setLayoutParams(params);

        // Criando ImageView dentro do CardView
                ImageView imageView = new ImageView(requireContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                new CarregaImagem(imageView).execute(imgende);

        // Adicionando ImageView ao CardView
                cardView.addView(imageView);
                cardView.setOnClickListener(view -> {
                    new CarregaImagem(imgvslide).execute(imgende);
                });

                lytimg.addView(cardView);

    }
    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


}