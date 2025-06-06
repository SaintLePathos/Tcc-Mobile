package com.example.prjmobiletcc;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class Criacntnrcarrinho extends LinearLayout {


    private TextView nome,preco,quant;
    public ImageButton aumentar, diminuir, apagar;
    private ImageView imgv;
    public Criacntnrcarrinho(Context context){
        super(context);
        init(context);
    }
    private void init(Context context) {

        LayoutParams lytparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(160));
        lytparams.setMargins(0,0,0,dpToPx(10));
        setLayoutParams(lytparams);
        setBackgroundResource(R.drawable.borda10);
        setOrientation(LinearLayout.HORIZONTAL);
        setPadding(dpToPx(20), dpToPx(10), dpToPx(20), dpToPx(10));

        // Layout esquerdo
        LinearLayout lytesqueda = new LinearLayout(context);
        LinearLayout.LayoutParams lytesparams = new LinearLayout.LayoutParams(dpToPx(300), LayoutParams.MATCH_PARENT, 1);
        lytesparams.setMargins(0,0,dpToPx(5),0);
        lytesqueda.setLayoutParams(lytesparams);
        lytesqueda.setGravity(Gravity.CENTER);
        lytesqueda.setOrientation(LinearLayout.VERTICAL);

        // ImageView
        imgv = new ImageView(context);
        LinearLayout.LayoutParams imgvparams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(80), 1);
        imgvparams.setMargins(0,0,0,dpToPx(10));
        imgv.setLayoutParams(imgvparams);
        imgv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgv.setImageResource(R.drawable.mdl2);

        lytesqueda.addView(imgv);

        // Layout para os botões
        LinearLayout lytbtns = new LinearLayout(context);
        LinearLayout.LayoutParams lytbtnsparams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(37));
        lytbtns.setLayoutParams(lytbtnsparams);
        lytbtns.setGravity(Gravity.CENTER);
        lytbtns.setOrientation(LinearLayout.HORIZONTAL);

        // Botão de remover
        diminuir = new ImageButton(context);
        LinearLayout.LayoutParams aumentarparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1);
        aumentarparams.setMargins(dpToPx(5),dpToPx(5),dpToPx(5),dpToPx(5));
        diminuir.setLayoutParams(aumentarparams);
        diminuir.setBackgroundResource(R.drawable.bordabtn);
        diminuir.setImageResource(R.drawable.ic_baseline_remove_24);
        lytbtns.addView(diminuir);

        // Texto de quantidade
        quant = new TextView(context);
        LinearLayout.LayoutParams quantparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1 );
        quantparams.setMargins(dpToPx(5),dpToPx(5),dpToPx(5),dpToPx(5));
        quant.setLayoutParams(quantparams);
        quant.setGravity(Gravity.CENTER);
        quant.setText("10");
        quant.setTextSize(20);
        lytbtns.addView(quant);

        // Botão de adicionar
        aumentar = new ImageButton(context);
        LinearLayout.LayoutParams diminuitparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1 );
        diminuitparams.setMargins(dpToPx(5),dpToPx(5),dpToPx(5),dpToPx(5));
        aumentar.setLayoutParams(diminuitparams);
        aumentar.setBackgroundResource(R.drawable.bordabtn);
        aumentar.setImageResource(R.drawable.ic_baseline_add_24);
        lytbtns.addView(aumentar);

        lytesqueda.addView(lytbtns);
        addView(lytesqueda);

        // Layout direito
        LinearLayout rightLayout = new LinearLayout(context);
        rightLayout.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(407), LinearLayout.LayoutParams.MATCH_PARENT, 1));
        rightLayout.setGravity(Gravity.CENTER);
        rightLayout.setOrientation(LinearLayout.VERTICAL);

        // Layout texto e botão de exclusão
        LinearLayout textButtonLayout = new LinearLayout(context);
        textButtonLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(70)));
        textButtonLayout.setGravity(Gravity.CENTER_VERTICAL);
        textButtonLayout.setOrientation(LinearLayout.HORIZONTAL);

        nome = new TextView(context);
        LinearLayout.LayoutParams nomeparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        nomeparams.setMargins(0,0,dpToPx(10),0);
        nome.setLayoutParams(nomeparams);
        nome.setEllipsize(TextUtils.TruncateAt.END);
        nome.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        nome.setMaxLines(3);
        nome.setText(getResources().getString(R.string.textsaturado));
        textButtonLayout.addView(nome);

        apagar = new ImageButton(context);
        apagar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        apagar.setBackgroundColor(Color.TRANSPARENT);
        apagar.setImageResource(R.drawable.ic_baseline_delete_36);
        textButtonLayout.addView(apagar);

        rightLayout.addView(textButtonLayout);

        // Texto de preço
        preco = new TextView(context);
        preco.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        preco.setGravity(Gravity.BOTTOM | Gravity.END);
        preco.setText("2x R$10,00\nTotal : R$22,00");
        rightLayout.addView(preco);

        addView(rightLayout);


    }
    public void dadoscarrinho(String id,int quantidade,Context context){
        try {
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(context);
            bdcnx.RS = bdcnx.stmt.executeQuery("SELECT * FROM Produto WHERE Id_Produto = "+id);
            if (bdcnx.RS.next()){

                String nome_Produto = bdcnx.RS.getString("Nome_Produto");
                String tamanho_Produto = bdcnx.RS.getString("Tamanho_Produto");
                String tecido_Produto = bdcnx.RS.getString("Tecido_Produto");
                String cor_Produto = bdcnx.RS.getString("Cor_Produto");
                String quantidade_produto = bdcnx.RS.getString("Quantidade_Produto");
                Double valor_produto = Double.parseDouble(bdcnx.RS.getString("Valor_Produto").toString());
                if ("0".equals(quantidade_produto)) {
                    throw new SQLException("Produto sem estoque para" + nome_Produto);
                }
                String valortotal = String.format("%.2f",(valor_produto * quantidade)).replace(".",",");
                String valorunitario = String.format("%.2f",valor_produto).replace(".",",");
                preco.setText(quantidade + "x R$" + valorunitario + "\nTotal : R$" + valortotal);
                nome.setText(nome_Produto + ", " + tamanho_Produto + ", " + cor_Produto + ", " + tecido_Produto);
                quant.setText(Integer.toString(quantidade));
                apagar.setOnClickListener(view -> apagarprod(id,context));
                aumentar.setOnClickListener(view -> aumentar(id,quantidade_produto,context));
                diminuir.setOnClickListener(view -> diminuir(id,context));
                try {
                    bdcnx.entBanco(context);
                    bdcnx.RS = bdcnx.stmt.executeQuery("SELECT Url_ImgProduto FROM Imagem_Produto WHERE Id_Produto = " + id + " ORDER BY Ordem_ImgProduto ASC");
                    if (bdcnx.RS.next()){
                        String imgurl = bdcnx.RS.getString("Url_ImgProduto");
                        new CarregaImagem(imgv).execute(bdcnx.urlimgsrv+imgurl);
                    }
                }catch (SQLException ex){
                    System.out.println(ex);
                }
            }
        }catch (SQLException ex){
            Carrinho car = new Carrinho();
            car.removerProduto(id,context);
            removeAllViews();
        }
    }
    private void apagarprod(String idpr, Context context){
        Carrinho car = new Carrinho();
        car.removerProduto(idpr, context);
        setVisibility(GONE);
    }
    private void diminuir(String idproduto, Context context){
        String quantidade = quant.getText().toString();
        int qt = Integer.parseInt(quantidade);
        if(qt > 1){
            Carrinho car = new Carrinho();
            int num = qt - 1;

            car.adicionarOuAtualizarProduto(idproduto,num,context);
            quant.setText(num+"");
            car.consultarProdutos(context);

        }
    }
    private void aumentar(String idproduto,String quantidadeproduto, Context context){
        int produtoquant = Integer.parseInt(quantidadeproduto);
        String quantidade = quant.getText().toString();
        int qt = Integer.parseInt(quantidade);
        if(qt < produtoquant){
            Carrinho car = new Carrinho();
            int num = qt + 1;
            quant.setText(num+"");
            car.adicionarOuAtualizarProduto(idproduto,num,context);
            car.consultarProdutos(context);

        }
    }
    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
