package com.example.prjmobiletcc;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.function.DoubleToLongFunction;

public class Criacntnrresumo extends LinearLayout {
    public Criacntnrresumo(Context context) {
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
        TextView txtResumo = new TextView(context);
        txtResumo.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(50)));
        txtResumo.setGravity(Gravity.BOTTOM | Gravity.LEFT);
        txtResumo.setText("Resumo");
        this.addView(txtResumo);

        // LinearLayout para o resumo
        LinearLayout lytResumo = new LinearLayout(context);
        lytResumo.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        lytResumo.setGravity(Gravity.CENTER);
        lytResumo.setOrientation(LinearLayout.VERTICAL);
        lytResumo.setPadding(dpToPx(20), dpToPx(20),
                dpToPx(20), dpToPx(20));
        lytResumo.setBackgroundResource(R.drawable.borda10);
        this.addView(lytResumo);



        adicionainfo(lytResumo,context);

    }

    private void adicionainfo(LinearLayout lyt, Context context){

        int num1 = 0;
        BigDecimal somatotalprodutos = BigDecimal.ZERO;
        BigDecimal sumatotaldesp = BigDecimal.ZERO;
        SharedPreferences sharedPreferences = context.getSharedPreferences("CarrinhoPrefs", Context.MODE_PRIVATE);
        String jsonCarrinho = sharedPreferences.getString("carrinho", "[]");
        try {
            JSONArray jsonArray = new JSONArray(jsonCarrinho);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject produto = jsonArray.getJSONObject(i);
                String idProduto = produto.getString("id_produto");
                int quantidade = produto.getInt("quantidade");
                Cnxbd bdcnx = new Cnxbd();
                bdcnx.entBanco(context);
                bdcnx.RS = bdcnx.stmt.executeQuery("SELECT * FROM Produto WHERE Id_Produto = " + idProduto);
                if (bdcnx.RS.next()){
                    BigDecimal valorProduto = new BigDecimal(bdcnx.RS.getString("Valor_Produto"));
                    BigDecimal valorTotalProduto = valorProduto.multiply(new BigDecimal(quantidade));
                    somatotalprodutos = somatotalprodutos.add(valorTotalProduto);



                    String valorProdutoStr = bdcnx.RS.getString("Valor_Produto");
                    String descontoProdutoStr = bdcnx.RS.getString("Desconto_Produto");
                    Valores vrls = new Valores();
                    String valorprodutodesconto = vrls.calculades(valorProdutoStr, descontoProdutoStr);
                    BigDecimal valordes = new BigDecimal(valorprodutodesconto.replace(",", ".")); // Convertendo para BigDecimal
                    BigDecimal totades = valordes.multiply(new BigDecimal(quantidade));
                    sumatotaldesp = sumatotaldesp.add(totades);
                }
                //lytcntnrcarrinnho.addView(criacntnrcarrinho);
                // Exibe os dados no console
                System.out.println("carrega");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Valores vlrs = new Valores();
        BigDecimal frete = new BigDecimal(vlrs.frete); // Defina o valor do frete aqui

        // Calcular total do pedido (subtotal + frete)
        BigDecimal totalPedido = somatotalprodutos.add(frete);


        BigDecimal valorParcelado = somatotalprodutos.divide(new BigDecimal(10), 2, BigDecimal.ROUND_HALF_UP);
        // Adiciona os itens de resumo
        adicionarLinhaResumo(context, lyt, "Subtotal:", "R$"+somatotalprodutos.toString().replace(".",","));
        adicionarLinhaDivisoria(context, lyt);
        adicionarLinhaResumo(context, lyt, "Em até 10x sem juros no cartão:", "R$"+valorParcelado.toString().replace(".",","));
        adicionarLinhaDivisoria(context, lyt);
        adicionarLinhaResumo(context, lyt, "Frete:", "R$"+frete.toString().replace(".",","));
        adicionarLinhaDivisoria(context, lyt);

        // Espaçamento
        Space space = new Space(context);
        space.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(25)));
        //lyt.addView(space);

        // Adicionar opções de pagamento
        //adicionarLinhaResumo(context, lyt, "Com 15% de desconto", "TextView");
        //adicionarLinhaDivisoria(context, lyt);
        adicionarLinhaResumo(context, lyt, "Valor Total", "R$" + totalPedido.toString().replace(".",","));
    }

    private void adicionarLinhaResumo(Context context, LinearLayout parent, String label, String value) {
        LinearLayout linha = new LinearLayout(context);
        linha.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(40)));
        linha.setOrientation(LinearLayout.HORIZONTAL);

        TextView txtLabel = new TextView(context);
        LinearLayout.LayoutParams paramsLabel = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        txtLabel.setLayoutParams(paramsLabel);
        txtLabel.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        txtLabel.setText(label);
        linha.addView(txtLabel);

        TextView txtValue = new TextView(context);
        txtValue.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        txtValue.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        txtValue.setText(value);
        linha.addView(txtValue);

        parent.addView(linha);
    }

    private void adicionarLinhaDivisoria(Context context, LinearLayout parent) {
        TextView linhaDivisoria = new TextView(context);
        linhaDivisoria.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(1)));
        linhaDivisoria.setBackgroundResource(R.color.black50);
        parent.addView(linhaDivisoria);
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}
