package com.example.prjmobiletcc;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Guardalogin {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    //7dias * 24horas * 60minutos * 60seguntos * 1000milisegundos = 7 dias em milisegundos
    public Guardalogin(Context context) {
        sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void salvarLogin(String id, String email) {
        long tempoexpiracao = System.currentTimeMillis() + (24 * 60 * 60 * 1000);
        editor.putString("id_cliente", id);
        editor.putString("email_cliente", email);
        editor.putLong("expiracao", tempoexpiracao);
        editor.apply();
    }
    public void definirenderecopadrao(String idendereco){
        editor.putString("idenderecopadrao", idendereco);
        editor.apply();
    }
    public void apagarEnderecoPadrao() {
        editor.remove("idenderecopadrao");
        editor.apply();
    }
    private void reiniciarcontagem(){
        long tempoexpiracao = System.currentTimeMillis() + (24 * 60 * 60 * 1000);
        editor.putLong("expiracao", tempoexpiracao);
        editor.apply();
    }
    public boolean loginexpiracao(){
        long tmpexpira = sharedPreferences.getLong("expiracao", 0);
        if (System.currentTimeMillis() >= tmpexpira) {
            limparLogin(); // Apaga os dados quando expirar
            return false;
        }
        reiniciarcontagem();
        return true;
    }
    public String getEnderecopadrao() {
        return sharedPreferences.getString("idenderecopadrao", null);
    }

    public String getId() {

        return  sharedPreferences.getString("id_cliente", null);
    }

    public String getEmail() {

        return sharedPreferences.getString("email_cliente", null);
    }


    public boolean existeEnderecoPadrao() {
        return sharedPreferences.contains("idenderecopadrao");
    }

    public boolean existeLogin() {
        return sharedPreferences.contains("id_cliente") && sharedPreferences.contains("email_cliente");
    }


    public void limparLogin() {
        editor.clear();
        editor.apply();
    }
}
