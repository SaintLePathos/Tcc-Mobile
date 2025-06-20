package com.example.prjmobiletcc;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class Criacntnrcheckend extends LinearLayout {
    public Criacntnrcheckend(Context context){
        super(context);
        init(context);
    }
    TextView txtInferior,txt1,txt2;
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
        txtEndereco.setTextColor(Color.BLACK);
        txtEndereco.setTypeface(null, Typeface.BOLD);
        txtEndereco.setTextSize(18);
        txtEndereco.setText("Endereço de entrega");
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
        imgIcon.setImageResource(R.drawable.ic_baseline_home_24);
        lytInterno.addView(imgIcon);

        // TextViews internos
        txt1 = new TextView(context);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, dpToPx(40), 1);
        params1.setMargins(dpToPx(10), 0, 0, 0);
        txt1.setLayoutParams(params1);
        txt1.setGravity(Gravity.CENTER_VERTICAL);
        txt1.setText("criado");
        lytInterno.addView(txt1);

        txt2 = new TextView(context);
        txt2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, dpToPx(40), 1));
        txt2.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        txt2.setText("Alterar");
        txt2.setPaintFlags(txt2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txt2.setTextColor(Color.RED);
        lytInterno.addView(txt2);

        // TextView inferior
        txtInferior = new TextView(context);
        txtInferior.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(40)));
        txtInferior.setGravity(Gravity.CENTER_VERTICAL);
        txtInferior.setText("TextView");
        lytEndereco.addView(txtInferior);

        preenchedados(context);
    }
    private void preenchedados(Context context){
        try {
            String idendereco;
            Guardalogin grdlogin = new Guardalogin(context);
            Valores vlrs = new Valores();
            if(grdlogin.existeEnderecoPadrao()){
                idendereco = grdlogin.getEnderecopadrao().toString();
                try {
                    Cnxbd bdcnx = new Cnxbd();
                    bdcnx.entBanco(context);
                    bdcnx.RS = bdcnx.stmt.executeQuery("SELECT * FROM Endereco_Cliente WHERE Visivel = 1 AND Id_Endereco_Cliente = " + idendereco);
                    if (bdcnx.RS.next()){
                        String rua = bdcnx.RS.getString("Rua_Cliente");
                        String num = bdcnx.RS.getString("Numero_Cliente");
                        String cep = bdcnx.RS.getString("CEP_Cliente");
                        txt1.setText(rua + ", " + num);
                        txtInferior.setText(vlrs.formatarCep(cep));
                    }
                }catch (SQLException ex){
                    System.out.println("erro na consulta end padrao"+ex);
                }
            }else{
                Toast.makeText(context,"Nenhum endereço definido como padrão", Toast.LENGTH_SHORT).show();
                txt1.setText("Sem endereço");
                txtInferior.setText("Endereço padrão não definido");
            }
        }catch (Exception e){
            Toast.makeText(context,"Nenhum endereço definido como padrão", Toast.LENGTH_SHORT).show();
            txt1.setText("Sem endereço");
            txtInferior.setText("Endereço padrão não definido");
        }
    }
    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
