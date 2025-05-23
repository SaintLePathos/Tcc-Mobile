package com.example.prjmobiletcc;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.method.SingleLineTransformationMethod;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Criacntnrenderecos extends LinearLayout {
    LinearLayout lytbarra, lytdetalhes, lytmaisdetalhes, lytmenosdetalhes;

    ImageButton icmenu;
    TextView txtvtextoenderc, txtvdetalhesendec;
    public Criacntnrenderecos(Context context){
        super(context);
        init(context);
    }
    private void init(Context context){
        LinearLayout.LayoutParams lytparams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lytparams.setMargins(0,0,0,dpToPx(30));
        setLayoutParams(lytparams);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        setPadding(dpToPx(20),dpToPx(20),dpToPx(20),dpToPx(20));
        setBackground(ContextCompat.getDrawable(context, R.drawable.borda10));

        // Criando sub-layout (barra superior)
        lytbarra = new LinearLayout(context);
        lytbarra.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        lytbarra.setGravity(Gravity.CENTER);
        lytbarra.setPadding(0, 0, 0, dpToPx(10));

        ImageView iccasa = new ImageView(context);
        iccasa.setLayoutParams(new LayoutParams(dpToPx(40), LayoutParams.MATCH_PARENT));
        iccasa.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iccasa.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_home_24));

        txtvtextoenderc = new TextView(context);
        LayoutParams txtvtextoendercparams = new LayoutParams(LayoutParams.WRAP_CONTENT, dpToPx(40), 1);
        txtvtextoendercparams.setMargins(dpToPx(10),0,0,0);
        txtvtextoenderc.setLayoutParams(txtvtextoendercparams);
        txtvtextoenderc.setGravity(Gravity.CENTER_VERTICAL);
        txtvtextoenderc.setText("Rua ,Num");

        icmenu = new ImageButton(context);
        icmenu.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        icmenu.setScaleType(ImageView.ScaleType.FIT_CENTER);
        icmenu.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_more_vert_24));
        icmenu.setBackgroundTintList(null);
        icmenu.setOnClickListener(v->{});

        lytbarra.addView(iccasa);
        lytbarra.addView(txtvtextoenderc);
        lytbarra.addView(icmenu);

        // Criando detalhes de endereço
        lytdetalhes = new LinearLayout(context);
        lytdetalhes.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        lytdetalhes.setOrientation(VERTICAL);
        lytdetalhes.setVisibility(GONE);

        txtvdetalhesendec = new TextView(context);
        LinearLayout.LayoutParams lytadressparams = new LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(100));
        lytadressparams.setMargins(0,0,0,dpToPx(10));
        txtvdetalhesendec.setLayoutParams(lytadressparams);
        txtvdetalhesendec.setGravity(Gravity.CENTER_VERTICAL);
        txtvdetalhesendec.setText("Rua Exemplo, XX, Bairro \n 00000-000 \n Cidade, UF \n Tel: (99) 99999-9999");

        lytmenosdetalhes = new LinearLayout(context);
        lytmenosdetalhes.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        lytmenosdetalhes.setGravity(Gravity.CENTER_VERTICAL);
        lytmenosdetalhes.setOrientation(HORIZONTAL);

        TextView lessText = new TextView(context);
        lessText.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        lessText.setText("Menos detalhes");
        lessText.setTextColor(ContextCompat.getColor(context, R.color.black50));

        ImageView lessIcon = new ImageView(context);
        lessIcon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        lessIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_keyboard_arrow_up_24));
        lessIcon.setColorFilter(ContextCompat.getColor(context, R.color.black50));


        lytmenosdetalhes.addView(lessText);
        lytmenosdetalhes.addView(lessIcon);
        lytmenosdetalhes.setOnClickListener(v->escondetalhes());


        lytdetalhes.addView(txtvdetalhesendec);
        lytdetalhes.addView(lytmenosdetalhes);

        // Criando botão "Mais detalhes"
        lytmaisdetalhes = new LinearLayout(context);
        lytmaisdetalhes.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        lytmaisdetalhes.setGravity(Gravity.CENTER_VERTICAL);
        lytmaisdetalhes.setOrientation(HORIZONTAL);
        lytmaisdetalhes.setVisibility(VISIBLE);
        lytmaisdetalhes.setOnClickListener(v->mostradetalhes());

        TextView moreText = new TextView(context);
        moreText.setText("Mais detalhes");
        moreText.setTextColor(ContextCompat.getColor(context, R.color.black50));

        ImageView moreIcon = new ImageView(context);
        moreIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_keyboard_arrow_down_24));
        moreIcon.setColorFilter(ContextCompat.getColor(context, R.color.black50));

        lytmaisdetalhes.addView(moreText);
        lytmaisdetalhes.addView(moreIcon);

        // Adicionando tudo ao layout principal
        addView(lytbarra);
        addView(lytdetalhes);
        addView(lytmaisdetalhes);
    }
    private void escondetalhes(){
        lytdetalhes.setVisibility(GONE);
        lytmaisdetalhes.setVisibility(VISIBLE);
    }
    private void mostradetalhes(){
        lytdetalhes.setVisibility(VISIBLE);
        lytmaisdetalhes.setVisibility(GONE);
    }
    private int dpToPx(int dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }
}
