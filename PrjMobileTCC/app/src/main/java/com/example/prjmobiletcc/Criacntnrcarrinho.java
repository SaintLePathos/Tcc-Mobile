package com.example.prjmobiletcc;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Criacntnrcarrinho extends LinearLayout {


    private TextView nome,preco,quant;
    private ImageButton aumentar, diminuir, apagar;
    private ImageView imgv;
    public Criacntnrcarrinho(Context context){
        super(context);
        init(context);
    }
    private void init(Context context) {
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(160)));
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
        aumentar = new ImageButton(context);
        LinearLayout.LayoutParams aumentarparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1);
        aumentarparams.setMargins(dpToPx(5),dpToPx(5),dpToPx(5),dpToPx(5));
        aumentar.setLayoutParams(aumentarparams);
        aumentar.setBackgroundResource(R.drawable.bordabtn);
        aumentar.setImageResource(R.drawable.ic_baseline_remove_24);
        lytbtns.addView(aumentar);

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
        diminuir = new ImageButton(context);
        LinearLayout.LayoutParams diminuitparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1 );
        diminuitparams.setMargins(dpToPx(5),dpToPx(5),dpToPx(5),dpToPx(5));
        diminuir.setLayoutParams(diminuitparams);
        diminuir.setBackgroundResource(R.drawable.bordabtn);
        diminuir.setImageResource(R.drawable.ic_baseline_add_24);
        lytbtns.addView(diminuir);

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

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
