package com.example.prjmobiletcc;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.SQLException;

public class SplashActivity extends AppCompatActivity {
    Guardalogin grdlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        grdlogin = new Guardalogin(getApplicationContext());

        ImageView imgv = findViewById(R.id.imgViewSplash); // Seu ImageView
        // Criando animação de "quicar"
        ObjectAnimator bounceAnim = ObjectAnimator.ofFloat(imgv, "translationY", 0f, -50f);
        bounceAnim.setDuration(500); // Tempo de cada quique
        bounceAnim.setRepeatMode(ValueAnimator.REVERSE); // Faz o movimento de ida e volta
        bounceAnim.setRepeatCount(ValueAnimator.INFINITE); // Repete infinitamente
        bounceAnim.start();
        tstconexao();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, 2000); // 1000 = 1 segundos
        verificalogin();
    }
    private void verificalogin(){

        if (grdlogin.loginexpiracao()){

        }
    }
    private void tstconexao(){
        try {
            Cnxbd bdcnx = new Cnxbd();
            bdcnx.entBanco(this);
        }catch (Exception ex){
            Log.e("DB_ERROR", "Erro ao conectar: " + ex.getMessage());
        }
    }
}