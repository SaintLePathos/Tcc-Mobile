package com.example.prjmobiletcc;

import android.content.Context;
import android.content.SharedPreferences;

public class Guardalogin {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    //7dias * 24horas * 60minutos * 60seguntos * 1000milisegundos = 7 dias em milisegundos
    public Guardalogin(Context context) {
        sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void salvarLogin(String id, String email, String senha) {
        long tempoexpiracao = System.currentTimeMillis() + (/*24 *  */60 * 60 * 1000);
        editor.putString("id_cliente", id);
        editor.putString("email_cliente", email);
        editor.putString("senha_cliente", senha);
        editor.putLong("expiracao", tempoexpiracao);
        editor.apply();
    }
    public boolean loginexpiracao(){
        long tmpexpira = sharedPreferences.getLong("expiracao", 0);

        if (System.currentTimeMillis() >= tmpexpira) {
            limparLogin(); // Apaga os dados quando expirar
            return false;
        }
        return true;
    }
    public String getId() { return  sharedPreferences.getString("id_cliente", null); }

    public String getEmail() {
        return sharedPreferences.getString("email_cliente", null);
    }

    public String getSenha() {
        return sharedPreferences.getString("senha_cliente", null);
    }

    public void limparLogin() {
        editor.clear();
        editor.apply();
    }
}
