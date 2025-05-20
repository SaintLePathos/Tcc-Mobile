package com.example.prjmobiletcc;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Criacntnr extends LinearLayout {

    Cnxbd bdcnx = new Cnxbd();
    String url = "http://"+bdcnx.iP+"/a1/git%20hub/Tcc-Web/assets/img/";
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
        nome.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(50)));
        nome.setGravity(Gravity.CENTER_VERTICAL);
        nome.setText(R.string.exemplotexto);
        nome.setEllipsize(TextUtils.TruncateAt.END);
        nome.setMaxLines(3);
        nome.setTextSize(12);

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
    public void valores(String imagemP, String nomeP, String valordesP, String valorP, String idP){
        new CarregaImagem(img).execute(url+imagemP);
        nome.setText(nomeP);
        valor.setText("R$ "+ valorP +" a VISTA");
        valordes.setText("de R$ "+valordesP+" por:");
    }
    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
