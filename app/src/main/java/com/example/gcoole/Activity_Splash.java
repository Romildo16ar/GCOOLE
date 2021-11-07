package com.example.gcoole;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.Sicronizacao;

import java.util.List;

public class Activity_Splash extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Dao dao = new Dao(Activity_Splash.this);
                List<Produtor> produtorList = dao.selecionarProdutor();
                List<Sicronizacao> sicronizacaoList = dao.selecionarSicronizacao();
                if(produtorList.size() == 0 && sicronizacaoList.size() == 0){
                    Intent intent = new Intent(Activity_Splash.this, TelaInicial.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(Activity_Splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        }, 2000);
    }
}
