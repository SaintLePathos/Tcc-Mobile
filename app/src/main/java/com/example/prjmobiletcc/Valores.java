package com.example.prjmobiletcc;

import android.content.Context;
import android.util.TypedValue;

public class Valores {

    static String idProduto;
    static String descricaoProduto;

    public String calculades(String valorP,String desP){
        String resultado;
        double desconto = Double.parseDouble(desP);
        double valor = Double.parseDouble(valorP);
        double num1 = valor/(1-(desconto/100));
        resultado = String.format("%.2f",num1).replace(".",",");
        return resultado;
    }
    public String calculacartao(String valor,int vezescartao){
        String retorno;
        double num1 = Double.parseDouble(valor);
        num1 = num1 / vezescartao;
        retorno = String.format("%.2f", num1).replace(".", ",");
        return retorno;
    }

    public int converterintdp(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

}
