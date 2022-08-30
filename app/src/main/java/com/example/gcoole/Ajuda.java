package com.example.gcoole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.CRUD.CadastroProdutor;

public class Ajuda extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ajuda);

        Button buttonVoltar = (Button) findViewById(R.id.idBotaoVoltar);
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Ajuda.this, TelaInicial.class));
            }
        });
    }

}
