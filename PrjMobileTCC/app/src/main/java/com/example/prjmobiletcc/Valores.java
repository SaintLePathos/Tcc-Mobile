package com.example.prjmobiletcc;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;

public class Valores {
    public static String idendereco;
    public static String detalhesendereco;
    public static String idpedido;
    public static String dtenviopedido;
    public static String idproduto;
    public static String descricaoProduto;
    public static String frete = "00.00";
    public static String pagamento = "";


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
    public String formatarCep(String cepNumerico) {
        if (cepNumerico != null && cepNumerico.length() == 8) {
            return cepNumerico.substring(0, 5) + "-" + cepNumerico.substring(5);
        }
        return cepNumerico; // ou retornar algo como "CEP inválido"
    }
    public String formatarCPF(String cpf) {
        return cpf.substring(0,3) + "." + cpf.substring(3,6) + "." + cpf.substring(6,9) + "-" + cpf.substring(9,11);
    }
    public String formatarTelefone(String telefone) {
        telefone = telefone.replaceAll("\\D", ""); // Remove caracteres não numéricos

        if (telefone.length() == 11) {
            return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 7) + "-" + telefone.substring(7);
        } else if (telefone.length() == 10) {
            return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 6) + "-" + telefone.substring(6);
        } else {
            return "Número inválido";
        }
    }
}
