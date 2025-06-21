package com.example.prjmobiletcc;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Criacntnrpesquisa extends LinearLayout {

    ImageView imageView;
    TextView textView;
    public Criacntnrpesquisa(Context context){
        super(context);
        init(context);
    }
    public void init(Context context){
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(50)));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(dpToPx(20), dpToPx(5), dpToPx(20), dpToPx(5));

        // ImageView
        imageView = new ImageView(context);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(dpToPx(40), LinearLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(imageParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(R.drawable.logo_app); // Defina aqui sua imagem
        linearLayout.addView(imageView);

        // TextView
        textView = new TextView(context);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        textView.setLayoutParams(textParams);
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        textView.setPadding(dpToPx(20), 0, 0, 0);
        textView.setText("Nome produtoNome produtoNome produtoNome produtoNome produto");
        linearLayout.addView(textView);
        this.addView(linearLayout);
    }
    public void inserirdados( String nome, String imgurl){
        Cnxbd bdcnx = new Cnxbd();
        textView.setText(nome);
        new CarregaImagem(imageView).execute(bdcnx.urlimgsrv+imgurl);
        System.out.println("Criado");
    }
    private int dpToPx(int dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }
}
