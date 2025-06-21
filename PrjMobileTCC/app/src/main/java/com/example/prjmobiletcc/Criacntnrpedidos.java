package com.example.prjmobiletcc;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Criacntnrpedidos extends LinearLayout {
    LinearLayout lytdetalhes, lytmaisdetalhes, lytBotoes;
    Button btnVerPedido, btnRastrearPedido;
    TextView txtNumeroPedido,textodata,textopagamento,textostatus,textovalortotal;

    public Criacntnrpedidos(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LinearLayout.LayoutParams lytparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lytparams.setMargins(0,0,0,dpToPx(30));
        setLayoutParams(lytparams);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        setPadding(dpToPx(20), dpToPx(20), dpToPx(20), dpToPx(20));
        setBackground(ContextCompat.getDrawable(context, R.drawable.borda10));

        // Barra superior
        LinearLayout lytBarra = new LinearLayout(context);
        lytBarra.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        lytBarra.setGravity(Gravity.CENTER);

        ImageView icPedido = new ImageView(context);
        icPedido.setLayoutParams(new LayoutParams(dpToPx(40), LayoutParams.MATCH_PARENT));
        icPedido.setPadding(dpToPx(3),dpToPx(3),dpToPx(3),dpToPx(3));
        icPedido.setScaleType(ImageView.ScaleType.FIT_CENTER);
        icPedido.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_content_paste_24));

        txtNumeroPedido = new TextView(context);
        LinearLayout.LayoutParams txtnumeropedidoparams = new LayoutParams(LayoutParams.WRAP_CONTENT, dpToPx(40), 1);
        txtnumeropedidoparams.setMargins(dpToPx(10),0,0,0);
        txtNumeroPedido.setLayoutParams(txtnumeropedidoparams);
        txtNumeroPedido.setGravity(Gravity.CENTER_VERTICAL);
        txtNumeroPedido.setText("Nº Pedido: XXXXXXXXXXXXXXX");

        lytBarra.addView(icPedido);
        lytBarra.addView(txtNumeroPedido);

        //inicio
        LinearLayout lytdata = new LinearLayout(context);
        LinearLayout.LayoutParams lytdataparams = new LayoutParams(LayoutParams.MATCH_PARENT,dpToPx(40));
        lytdata.setLayoutParams(lytdataparams);
        lytdata.setGravity(Gravity.CENTER);
        lytdata.setOrientation(HORIZONTAL);

        TextView txtdata = new TextView(context);
        LayoutParams txtdataparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        txtdata.setLayoutParams(txtdataparams);
        txtdata.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        txtdata.setText("Data: ");

        textodata = new TextView(context);
        LayoutParams textodataparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT,1);
        textodata.setLayoutParams(textodataparams);
        textodata.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        textodata.setText("XX/XX/XXXX");

        lytdata.addView(txtdata);
        lytdata.addView(textodata);

        //fim

        // Seção de detalhes
        lytdetalhes = new LinearLayout(context);
        LayoutParams lytdetalhesparams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lytdetalhesparams.setMargins(0,0,0,dpToPx(20));
        lytdetalhes.setLayoutParams(lytdetalhesparams);
        lytdetalhes.setOrientation(VERTICAL);
        lytdetalhes.setVisibility(GONE);


        LinearLayout lytpagamento = new LinearLayout(context);
        LayoutParams lytpagamentoparams = new LayoutParams(LayoutParams.MATCH_PARENT,dpToPx(40));
        lytpagamento.setLayoutParams(lytpagamentoparams);
        lytpagamento.setGravity(Gravity.CENTER);
        lytpagamento.setOrientation(HORIZONTAL);

        TextView txtpagamento = new TextView(context);
        LayoutParams txtpagamentoparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        txtpagamento.setLayoutParams(txtpagamentoparams);
        txtpagamento.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        txtpagamento.setText("Pagamento: ");

        textopagamento = new TextView(context);
        LayoutParams textopagamentoparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT, 1);
        textopagamento.setLayoutParams(textopagamentoparams);
        textopagamento.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        textopagamento.setText("tipo de pagamento");

        lytpagamento.addView(txtpagamento);
        lytpagamento.addView(textopagamento);

        //-----------

        LinearLayout lytstatus = new LinearLayout(context);
        LayoutParams lytstatusparams = new LayoutParams(LayoutParams.MATCH_PARENT,dpToPx(40));
        lytstatus.setLayoutParams(lytstatusparams);
        lytstatus.setGravity(Gravity.CENTER);
        lytstatus.setOrientation(HORIZONTAL);

        TextView txtstatus = new TextView(context);
        LayoutParams txtstatusparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        txtstatus.setLayoutParams(txtstatusparams);
        txtstatus.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        txtstatus.setText("Status: ");

        textostatus = new TextView(context);
        LayoutParams textostatusparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT, 1);
        textostatus.setLayoutParams(textostatusparams);
        textostatus.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        textostatus.setText("status preparando ou enviado");

        lytstatus.addView(txtstatus);
        lytstatus.addView(textostatus);

        //-------------------

        LinearLayout lytvalortotal = new LinearLayout(context);
        LayoutParams lytvalortotalparams = new LayoutParams(LayoutParams.MATCH_PARENT,dpToPx(40));
        lytvalortotal.setLayoutParams(lytvalortotalparams);
        lytvalortotal.setGravity(Gravity.CENTER);
        lytvalortotal.setOrientation(HORIZONTAL);

        TextView txtvalortotal = new TextView(context);
        LayoutParams txtvalortotalparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        txtvalortotal.setLayoutParams(txtvalortotalparams);
        txtvalortotal.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        txtvalortotal.setText("Valor total: ");

        textovalortotal = new TextView(context);
        LayoutParams textovalortotalparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT, 1);
        textovalortotal.setLayoutParams(textovalortotalparams);
        textovalortotal.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        textovalortotal.setText("precototal do pedido");

        lytvalortotal.addView(txtvalortotal);
        lytvalortotal.addView(textovalortotal);

        //-----
        //Mostrar e ocultar detalhes

        LinearLayout lytmenosdetalhes = new LinearLayout(context);
        LayoutParams lytmenosdetalhesparams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lytmenosdetalhesparams.setMargins(0,dpToPx(20),0,0);
        lytmenosdetalhes.setLayoutParams(lytmenosdetalhesparams);
        lytmenosdetalhes.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        lytmenosdetalhes.setOrientation(HORIZONTAL);

        TextView txtvmenosdetalhes = new TextView(context);
        LayoutParams txtvmenosdetalhesparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        txtvmenosdetalhes.setLayoutParams(txtvmenosdetalhesparams);
        txtvmenosdetalhes.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        txtvmenosdetalhes.setText("Menos detalhes");
        txtvmenosdetalhes.setTextColor(ContextCompat.getColorStateList(context, R.color.black50));

        ImageView icmenosdetalhes = new ImageView(context);
        icmenosdetalhes.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        icmenosdetalhes.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_keyboard_arrow_up_24));
        icmenosdetalhes.setImageTintList(ContextCompat.getColorStateList(context, R.color.black50));

        lytmenosdetalhes.addView(txtvmenosdetalhes);
        lytmenosdetalhes.addView(icmenosdetalhes);

        //crialinha(context, lytdetalhes);
        //lytdetalhes.addView(lytpagamento);
        crialinha(context, lytdetalhes);
        lytdetalhes.addView(lytstatus);
        crialinha(context, lytdetalhes);
        lytdetalhes.addView(lytvalortotal);
        crialinha(context, lytdetalhes);
        lytdetalhes.addView(lytmenosdetalhes);



        //mostrar mais

        lytmaisdetalhes = new LinearLayout(context);
        LayoutParams lytmaisdetalhesparams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lytmaisdetalhesparams.setMargins(0,dpToPx(10),0,dpToPx(20));
        lytmaisdetalhes.setLayoutParams(lytmaisdetalhesparams);
        lytmaisdetalhes.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        lytmaisdetalhes.setOrientation(HORIZONTAL);
        lytmaisdetalhes.setVisibility(VISIBLE);

        TextView txtvmaisdetalhes = new TextView(context);
        LayoutParams txtvmaisdetalhesparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        txtvmaisdetalhes.setLayoutParams(txtvmaisdetalhesparams);
        txtvmaisdetalhes.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        txtvmaisdetalhes.setText("Mais detalhes");
        txtvmaisdetalhes.setTextColor(ContextCompat.getColorStateList(context, R.color.black50));

        ImageView icmaisdetalhes = new ImageView(context);
        icmaisdetalhes.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        icmaisdetalhes.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_keyboard_arrow_down_24));
        icmaisdetalhes.setImageTintList(ContextCompat.getColorStateList(context, R.color.black50));

        lytmaisdetalhes.addView(txtvmaisdetalhes);
        lytmaisdetalhes.addView(icmaisdetalhes);

        // Botões de ação
        lytBotoes = new LinearLayout(context);
        LayoutParams lytbotoesparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lytbotoesparams.setMargins(0,dpToPx(10),0,dpToPx(20));
        lytBotoes.setLayoutParams(lytbotoesparams);

        lytBotoes.setGravity(Gravity.CENTER);
        lytBotoes.setOrientation(HORIZONTAL);

        btnVerPedido = new Button(context);
        LayoutParams btnverpedidoparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT, 1);
        btnverpedidoparams.setMargins(dpToPx(10),0,dpToPx(10),0);
        btnVerPedido.setLayoutParams(btnverpedidoparams);
        btnVerPedido.setText("Ver Pedido");
        btnVerPedido.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.cinzatema));
        btnVerPedido.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
        btnVerPedido.setAllCaps(false);

        btnRastrearPedido = new Button(context);
        LayoutParams btnrastrearpedidoparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT, 1);
        btnverpedidoparams.setMargins(dpToPx(10),0,dpToPx(10),0);
        btnRastrearPedido.setLayoutParams(btnrastrearpedidoparams);
        btnRastrearPedido.setText("Rastrear Pedido");
        btnRastrearPedido.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.cinzatema));
        btnRastrearPedido.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
        btnRastrearPedido.setAllCaps(false);


        lytBotoes.addView(btnVerPedido);


        lytmenosdetalhes.setOnClickListener(v->escondetalhes());
        lytmaisdetalhes.setOnClickListener(v->mostradetalhes());

        addView(lytBarra);

        addView(lytdata);

        addView(lytdetalhes);

        addView(lytmaisdetalhes);

        addView(lytBotoes);


    }
    private void crialinha(Context context, LinearLayout lyt){
        TextView linha = new TextView(context);
        linha.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(1)));
        linha.setBackground(ContextCompat.getDrawable(context, R.color.black25));
        lyt.addView(linha);
    }
    public void inserirdados(String numeroPedido, String data, String status, String valor) {
        txtNumeroPedido.setText("Nº Pedido: "+numeroPedido);
        textodata.setText(formatarData(data));
        textostatus.setText(status);
        textovalortotal.setText("R$ "+valor.replace(".",","));
    }

    private int dpToPx(int dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }

    private void escondetalhes(){
        lytdetalhes.setVisibility(GONE);
        lytmaisdetalhes.setVisibility(VISIBLE);
    }
    private void mostradetalhes(){
        lytdetalhes.setVisibility(VISIBLE);
        lytmaisdetalhes.setVisibility(GONE);
    }
    private static String formatarData(String data) {
        return data.replace("-", "/");
    }
}
