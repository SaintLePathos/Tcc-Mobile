package com.example.prjmobiletcc;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.sql.SQLException;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imgv = findViewById(R.id.imgViewSplash); // Seu ImageView
        // Criando animação de "quicar"
        ObjectAnimator bounceAnim = ObjectAnimator.ofFloat(imgv, "translationY", 0f, -50f);
        bounceAnim.setDuration(500); // Tempo de cada quique
        bounceAnim.setRepeatMode(ValueAnimator.REVERSE); // Faz o movimento de ida e volta
        bounceAnim.setRepeatCount(ValueAnimator.INFINITE); // Repete infinitamente
        bounceAnim.start();

        Cnxbd bdcnx = new Cnxbd();

        bdcnx.entBanco(this);
        try{
            bdcnx.RS = bdcnx.stmt.executeQuery("SELECT * FROM Produto");
            while (bdcnx.RS.next()){
                String id_Produto,
                        id_Fornecedor,
                        nome_Produto,
                        img_Produto,
                        descricao_Produto,
                        valor_Produto,
                        peso_Produto,
                        desconto_Produto,
                        tamanho_Produto,
                        quantidade_Produto,
                        tecido_Produto,
                        cor_Produto;
                id_Produto = bdcnx.RS.getString("Nome_Produto");

                System.out.println("Registro :" + id_Produto);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, 2000); // 1000 = 1 segundos
    }
}