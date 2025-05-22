package com.example.prjmobiletcc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prjmobiletcc.DescricaoDetalhesFragment;

import java.sql.SQLException;

public class DetalhesFragment extends Fragment {

    public DetalhesFragment() {
        // Required empty public constructor
    }
    View ver;
    Button btnslidedireita,btnslideesquerda;
    ImageView imgvslide,imgvcx1,imgvcx2,imgvcx3,imgvcx4;
    TextView txtnome, txtdisponibi,
            txtvalorvista,txtvalorcartao,txtvalordes,txtpagvista,txtpagcartao,
            txtdesconto,txtvendido,txtestoque;
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

        Button btnmostradescricao = ver.findViewById(R.id.btnDescricao);

        btnmostradescricao.setOnClickListener(v ->verdescricao());

        carregamento();
        return ver;
    }
    private void verdescricao(){
        DescricaoDetalhesFragment bottomSheetDialog = new DescricaoDetalhesFragment();
        bottomSheetDialog.show(getChildFragmentManager(), "BottomSheetDialog");
    }
    Cnxbd bdcnx = new Cnxbd();
    Valores vlrs = new Valores();
    private void carregamento(){
        try{
            bdcnx.entBanco(requireContext());
            bdcnx.RS = bdcnx.stmt.executeQuery("SELECT p.*, COUNT(pp.Id_Pedido) AS Quantidade_Total_Produto_Pedido FROM Produto_Pedido pp JOIN Produto p ON pp.Id_Produto = p.Id_Produto WHERE p.Id_Produto = " + vlrs.idProduto + " GROUP BY p.Id_Produto, p.Id_Fornecedor, p.Nome_Produto, p.Img_Produto, p.Descricao_Produto, p.Valor_Produto, p.Desconto_Produto, p.Tamanho_Produto, p.Quantidade_Produto, p.Tecido_Produto, p.Cor_Produto;");
            while(bdcnx.RS.next()){
                String id_Produto,
                        nome_Produto,
                        img_Produto,
                        descricao_Produto,
                        valor_Produto,
                        desconto_Produto,
                        tamanho_Produto,
                        quantidade_Produto,
                        tecido_Produto,
                        cor_Produto,
                        verda_Produto;
                id_Produto = bdcnx.RS.getString("Id_Produto");
                nome_Produto = bdcnx.RS.getString("Nome_Produto");
                img_Produto = bdcnx.RS.getString("Img_Produto");
                descricao_Produto = bdcnx.RS.getString("Descricao_Produto");
                valor_Produto = bdcnx.RS.getString("Valor_Produto");
                desconto_Produto = bdcnx.RS.getString("Desconto_Produto");
                tamanho_Produto = bdcnx.RS.getString("Tamanho_Produto");
                quantidade_Produto = bdcnx.RS.getString("Quantidade_Produto");
                tecido_Produto = bdcnx.RS.getString("Tecido_Produto");
                cor_Produto = bdcnx.RS.getString("Cor_Produto");
                verda_Produto = bdcnx.RS.getString("Quantidade_Total_Produto_Pedido");
                String valordesconto = vlrs.calculades(valor_Produto,desconto_Produto);
                new CarregaImagem(imgvslide).execute(bdcnx.urlimgsrv+img_Produto);
                new CarregaImagem(imgvcx1).execute(bdcnx.urlimgsrv+img_Produto);
                new CarregaImagem(imgvcx2).execute(bdcnx.urlimgsrv+img_Produto);
                new CarregaImagem(imgvcx3).execute(bdcnx.urlimgsrv+img_Produto);
                new CarregaImagem(imgvcx4).execute(bdcnx.urlimgsrv+img_Produto);
                txtnome.setText(nome_Produto+", "+tecido_Produto+", "+cor_Produto+", "+tamanho_Produto);
                if (quantidade_Produto.equals("0")){
                    txtdisponibi.setText("PRODUTO ESGOTADO");
                    txtdisponibi.setTextColor(ContextCompat.getColor(requireContext(), R.color.vermelhodinheiro));
                }else{
                    txtdisponibi.setText("PRODUTO DISPONIVEL");
                }
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
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

}