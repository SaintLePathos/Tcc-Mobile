package com.example.prjmobiletcc;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.SQLException;

public class Criacntnr extends LinearLayout {

    Cnxbd bdcnx = new Cnxbd();
    Valores vlrs = new Valores();
    private TextView nome, valordes, valor, txt1;
    private LinearLayout cntnrTxt;
    private ImageView img;
    public Criacntnr(Context context) {
        super(context);
        init(context);
    }
    private void init(Context context) {
        LinearLayout.LayoutParams ln= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(160));
        ln.setMargins(0,0,0,dpToPx(10));
        setLayoutParams(ln);

        setOrientation(LinearLayout.HORIZONTAL);
        setPadding(dpToPx(20), dpToPx(10), dpToPx(20), dpToPx(10));

        setBackgroundResource(R.drawable.borda10);

        img = new ImageView(context);
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(dpToPx(100), LinearLayout.LayoutParams.MATCH_PARENT);
        imgParams.setMargins(0,0,dpToPx(10),0);
        img.setLayoutParams(imgParams);
        img.setImageResource(R.drawable.mdl2);

        cntnrTxt = new LinearLayout(context);
        cntnrTxt.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        cntnrTxt.setOrientation(LinearLayout.VERTICAL);
        cntnrTxt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

        nome  = new TextView(context);
        nome.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(60)));
        nome.setGravity(Gravity.CENTER_VERTICAL);
        nome.setText(R.string.exemplotexto);
        nome.setEllipsize(TextUtils.TruncateAt.END);
        nome.setMaxLines(3);
        nome.setTextSize(16);

        valordes = new TextView(context);
        valordes.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,1));
        valordes.setGravity(Gravity.CENTER_VERTICAL);
        valordes.setText("de R$ 400,00 por:");
        valordes.setTextColor(ContextCompat.getColor(context, R.color.vermelhodinheiro));
        valordes.setTextSize(10);

        valor = new TextView(context);
        valor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
        valor.setGravity(Gravity.CENTER_VERTICAL);
        valor.setText("R$ 200,00 a VISTA");
        valor.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        valor.setTextColor(ContextCompat.getColor(context, R.color.verdedinheiro));
        valor.setTextSize(16);

        txt1 = new TextView(context);
        txt1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,1));
        txt1.setGravity(Gravity.CENTER_VERTICAL);
        txt1.setText("em até 10x de R$20,00 sem juros no cartão");
        txt1.setTextSize(8);

        cntnrTxt.addView(nome);
        cntnrTxt.addView(valordes);
        cntnrTxt.addView(valor);
        cntnrTxt.addView(txt1);

        addView(img);
        addView(cntnrTxt);
    }
    public void valores(String idproduto, String nomeP, String valordesP, String valorP,Context context){
        int quantidadedevezesnocartao = 10;
        String valordesconto = vlrs.calculades(valorP,valordesP);
        try {
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(context);
            bdcnx.RS = bdcnx.stmt.executeQuery("SELECT Url_ImgProduto FROM Imagem_Produto WHERE Id_Produto = " + idproduto + " ORDER BY Ordem_ImgProduto ASC");
            if (bdcnx.RS.next()){
                String imgurl = bdcnx.RS.getString("Url_ImgProduto");
                new CarregaImagem(img).execute(bdcnx.urlimgsrv+imgurl);
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }
        nome.setText(nomeP);
        valor.setText("R$ "+ valorP.replace(".",",") +" a VISTA");//vista NO PIX

        valordes.setPaintFlags(valordes.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        valordes.setText("de R$ "+valordesconto+" por:");

        String valorvzacartao = vlrs.calculacartao(valorP,quantidadedevezesnocartao);
        txt1.setText("em até " + quantidadedevezesnocartao + "x de " + valorvzacartao + " sem juros no cartão");
    }
    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
