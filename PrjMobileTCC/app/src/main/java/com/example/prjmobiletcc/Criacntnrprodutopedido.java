package com.example.prjmobiletcc;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLException;

public class Criacntnrprodutopedido extends LinearLayout {
    public Criacntnrprodutopedido(Context context){
        super(context);
        init(context);
    }
    LinearLayout verticalLayout;
    FrameLayout frameLayout;
    TextView valueTextViewquant,valueTextViewvalorunit,valueTextViewvalortotal,textViewTitle;
    ImageView imageView;
    public void init(Context context){
        // Configuração do LinearLayout principal

        LayoutParams lytparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(context, 150));
        lytparams.setMargins(0,0,0,dpToPx(context,10));
        this.setLayoutParams(lytparams);

        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setPadding(dpToPx(context, 20), dpToPx(context, 10), dpToPx(context, 20), dpToPx(context, 10));

        // Define o fundo do layout
        Drawable background = ContextCompat.getDrawable(context, R.drawable.borda10);
        this.setBackground(background);

        // Criando a ImageView
        imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(context, 100), LinearLayout.LayoutParams.MATCH_PARENT));
        imageView.setImageResource(R.drawable.mdl2);
        this.addView(imageView);

        // Criando o FrameLayout
        frameLayout = new FrameLayout(context);
        LinearLayout.LayoutParams frameParams = new LinearLayout.LayoutParams(dpToPx(context, 456), LinearLayout.LayoutParams.MATCH_PARENT, 1);
        frameParams.setMargins(dpToPx(context, 10), 0, 0, 0);
        frameLayout.setLayoutParams(frameParams);

        // Criando o TextView título
        textViewTitle = new TextView(context);
        textViewTitle.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dpToPx(context, 50)));
        textViewTitle.setGravity(Gravity.CENTER_VERTICAL);
        textViewTitle.setText(R.string.exemplotexto);
        textViewTitle.setTextSize(14);
        frameLayout.addView(textViewTitle);

        // Criando o layout vertical
        verticalLayout = new LinearLayout(context);
        verticalLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));
        verticalLayout.setOrientation(LinearLayout.VERTICAL);

        // Adicionando os TextViews
        LinearLayout horizontalLayoutquant = new LinearLayout(context);
        horizontalLayoutquant.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        horizontalLayoutquant.setOrientation(LinearLayout.HORIZONTAL);

        TextView labelTextViewquant = new TextView(context);
        labelTextViewquant.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        labelTextViewquant.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        labelTextViewquant.setText("Quantidade:");
        labelTextViewquant.setTextSize(14);
        horizontalLayoutquant.addView(labelTextViewquant);

        valueTextViewquant = new TextView(context);
        LinearLayout.LayoutParams valueTextViewquantparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        valueTextViewquant.setLayoutParams(valueTextViewquantparams);
        valueTextViewquant.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        valueTextViewquant.setText("exerf");
        valueTextViewquant.setTextSize(14);
        horizontalLayoutquant.addView(valueTextViewquant);

        verticalLayout.addView(horizontalLayoutquant);

        // Adicionando os TextViews
        LinearLayout horizontalLayoutvalorunit = new LinearLayout(context);
        horizontalLayoutvalorunit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        horizontalLayoutvalorunit.setOrientation(LinearLayout.HORIZONTAL);

        TextView labelTextViewvalorunit = new TextView(context);
        labelTextViewvalorunit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        labelTextViewvalorunit.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        labelTextViewvalorunit.setText("Valor unitário:");
        labelTextViewvalorunit.setTextSize(14);
        horizontalLayoutvalorunit.addView(labelTextViewvalorunit);

        valueTextViewvalorunit = new TextView(context);
        LinearLayout.LayoutParams valueTextViewvalorunitparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        valueTextViewvalorunit.setLayoutParams(valueTextViewvalorunitparams);
        valueTextViewvalorunit.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        valueTextViewvalorunit.setText("exerf");
        valueTextViewvalorunit.setTextSize(14);
        horizontalLayoutvalorunit.addView(valueTextViewvalorunit);

        verticalLayout.addView(horizontalLayoutvalorunit);

        // Adicionando os TextViews
        LinearLayout horizontalLayoutvalortotal = new LinearLayout(context);
        horizontalLayoutvalortotal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        horizontalLayoutvalortotal.setOrientation(LinearLayout.HORIZONTAL);

        TextView labelTextViewvalortotal = new TextView(context);
        labelTextViewvalortotal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        labelTextViewvalortotal.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        labelTextViewvalortotal.setText("Valor Total:");
        labelTextViewvalortotal.setTextSize(14);
        horizontalLayoutvalortotal.addView(labelTextViewvalortotal);

        valueTextViewvalortotal = new TextView(context);
        LinearLayout.LayoutParams valueTextViewvalortotalparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        valueTextViewvalortotal.setLayoutParams(valueTextViewvalortotalparams);
        valueTextViewvalortotal.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        valueTextViewvalortotal.setText("exerf");
        valueTextViewvalortotal.setTextSize(14);
        horizontalLayoutvalortotal.addView(valueTextViewvalortotal);

        verticalLayout.addView(horizontalLayoutvalortotal);


        frameLayout.addView(verticalLayout);
        this.addView(frameLayout);
    }
    public void inserirdds(String nome, String quantidade, String valorunit, String valortotal, String idproduto, Context context){
        try {
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(context);
            bdcnx.RS = bdcnx.stmt.executeQuery("SELECT Url_ImgProduto FROM Imagem_Produto WHERE Id_Produto = " + idproduto + " ORDER BY Ordem_ImgProduto ASC");
            if (bdcnx.RS.next()){
                String imgurl = bdcnx.RS.getString("Url_ImgProduto");
                new CarregaImagem(imageView).execute( bdcnx.urlimgsrv+imgurl);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }

        textViewTitle.setText(nome);
        if (quantidade.equals("1")){
            valueTextViewquant.setText(quantidade+" Unidade");
        }else{
            valueTextViewquant.setText(quantidade+" Unidades");
        }
        valueTextViewvalorunit.setText("R$"+valorunit);
        valueTextViewvalortotal.setText("R$"+valortotal);
    }

    // Método auxiliar para converter dp para pixels
    private int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
