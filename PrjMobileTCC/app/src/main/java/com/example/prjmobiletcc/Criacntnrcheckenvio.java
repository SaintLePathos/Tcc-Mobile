package com.example.prjmobiletcc;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Criacntnrcheckenvio extends LinearLayout {
    public Criacntnrcheckenvio(Context context){
        super(context);
        init(context);
    }

    private void init(Context context) {
        // Configuração do layout principal
        this.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        this.setOrientation(LinearLayout.VERTICAL);

        // TextView para o título
        TextView txtEndereco = new TextView(context);
        txtEndereco.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(50)));
        txtEndereco.setGravity(Gravity.BOTTOM | Gravity.LEFT);
        txtEndereco.setText("Forma de Envio");
        this.addView(txtEndereco);

        // LinearLayout para o endereço
        LinearLayout lytEndereco = new LinearLayout(context);
        lytEndereco.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(120)));
        lytEndereco.setGravity(Gravity.CENTER);
        lytEndereco.setOrientation(LinearLayout.VERTICAL);
        lytEndereco.setPadding(dpToPx(20), dpToPx(20),
                dpToPx(20), dpToPx(20));
        lytEndereco.setBackgroundResource(R.drawable.borda10);
        this.addView(lytEndereco);

        // LinearLayout interno para ícone e textos
        LinearLayout lytInterno = new LinearLayout(context);
        lytInterno.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        lytInterno.setGravity(Gravity.CENTER);
        lytEndereco.addView(lytInterno);

        // ImageView
        ImageView imgIcon = new ImageView(context);
        imgIcon.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(40), LinearLayout.LayoutParams.MATCH_PARENT));
        imgIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgIcon.setImageResource(R.drawable.ic_baseline_local_shipping_24);
        lytInterno.addView(imgIcon);

        // TextViews internos
        TextView txt1 = new TextView(context);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, dpToPx(40), 1);
        params1.setMargins(dpToPx(10), 0, 0, 0);
        txt1.setLayoutParams(params1);
        txt1.setGravity(Gravity.CENTER_VERTICAL);
        txt1.setText("Correios");
        lytInterno.addView(txt1);

        // TextView inferior
        TextView txtInferior = new TextView(context);
        txtInferior.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(40)));
        txtInferior.setGravity(Gravity.CENTER_VERTICAL);
        txtInferior.setText("Frete: R$XX,XX");
        lytEndereco.addView(txtInferior);
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
