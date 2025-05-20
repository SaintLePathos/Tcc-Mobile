package com.example.prjmobiletcc;

public class Valores {
    static String idProduto;
    public String calculades(String valorP,String desP){
        String resultado;
        double desconto = Double.parseDouble(desP);
        double valor = Double.parseDouble(valorP);
        double num1 = valor/(1-(desconto/100));
        resultado = String.format("%.2f",num1);
        return resultado;
    }
}
