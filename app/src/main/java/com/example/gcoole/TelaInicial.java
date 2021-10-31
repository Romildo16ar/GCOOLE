package com.example.gcoole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.CRUD.CadastroProdutor;
import com.example.gcoole.Listviews.ListviewProdutor;

public class TelaInicial extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.tela_inicial);

        Button buttonGestor = (Button) findViewById(R.id.idBotaoGestor);
        buttonGestor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(TelaInicial.this, CadastroProdutor.class));
            }
        });

        Button buttonProdutor = (Button) findViewById(R.id.idBotaoProdutor);
        buttonProdutor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

    }





}
