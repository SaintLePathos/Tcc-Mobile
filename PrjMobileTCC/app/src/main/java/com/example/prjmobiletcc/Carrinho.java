package com.example.prjmobiletcc;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

public class Carrinho {
    public void adicionarOuAtualizarProduto(String idProduto, int quantidade, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CarrinhoPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Recuperar JSON existente
        String jsonCarrinho = sharedPreferences.getString("carrinho", "[]");
        JSONArray jsonArray;

        try {
            jsonArray = new JSONArray(jsonCarrinho);
        } catch (Exception e) {
            jsonArray = new JSONArray();
        }

        boolean produtoExiste = false;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject produto = jsonArray.getJSONObject(i);
                if (produto.getString("id_produto").equals(idProduto)) {
                    int quantidadeAtual = produto.getInt("quantidade");
                    produto.put("quantidade", /*quantidadeAtual +*/ quantidade);
                    produtoExiste = true;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!produtoExiste) {
            JSONObject novoProduto = new JSONObject();
            try {
                novoProduto.put("id_produto", idProduto);
                novoProduto.put("quantidade", quantidade);
                jsonArray.put(novoProduto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Salvar JSON atualizado
        editor.putString("carrinho", jsonArray.toString());
        editor.apply();
    }
    public boolean carrinhoEstaVazio(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CarrinhoPrefs", Context.MODE_PRIVATE);
        String jsonCarrinho = sharedPreferences.getString("carrinho", "[]"); // Se não existir, retorna um array vazio

        try {
            JSONArray jsonArray = new JSONArray(jsonCarrinho);
            return jsonArray.length() == 0; // Se não houver itens, o carrinho está vazio
        } catch (Exception e) {
            e.printStackTrace();
            return true; // Se houver erro, assume-se que está vazio
        }
    }
    public void consultarProdutos(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CarrinhoPrefs", Context.MODE_PRIVATE);
        String jsonCarrinho = sharedPreferences.getString("carrinho", "[]"); // Se não existir, retorna um array vazio

        try {
            JSONArray jsonArray = new JSONArray(jsonCarrinho);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject produto = jsonArray.getJSONObject(i);
                String idProduto = produto.getString("id_produto");
                int quantidade = produto.getInt("quantidade");

                // Exibir os dados (pode ser adaptado para um RecyclerView ou outra interface gráfica)
                System.out.println("Produto: " + idProduto + " | Quantidade: " + quantidade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void removerProduto(String idProduto, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CarrinhoPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String jsonCarrinho = sharedPreferences.getString("carrinho", "[]");
        JSONArray jsonArray;

        try {
            jsonArray = new JSONArray(jsonCarrinho);
        } catch (Exception e) {
            jsonArray = new JSONArray();
        }

        // Criar um novo JSONArray sem o item a ser removido
        JSONArray novoJsonArray = new JSONArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject produto = jsonArray.getJSONObject(i);
                if (!produto.getString("id_produto").equals(idProduto)) {
                    novoJsonArray.put(produto); // Mantém apenas os produtos diferentes do que será removido
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        // Atualizar o SharedPreferences com o novo JSON sem o item removido
        editor.putString("carrinho", novoJsonArray.toString());
        editor.apply();
    }
    public void limparCarrinho(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CarrinhoPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("carrinho"); // Remove todos os produtos
        editor.apply();
    }


}
