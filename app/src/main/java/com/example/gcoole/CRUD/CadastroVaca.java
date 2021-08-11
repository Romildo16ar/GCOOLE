package com.example.gcoole.CRUD;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.ListviewProdutor;
import com.example.gcoole.Listviews.ListviewsVaca;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.R;

public class CadastroVaca extends AppCompatActivity implements View.OnClickListener {

    private EditText nomeVaca;
    private EditText numVaca;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_casdastro_vaca);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        nomeVaca = findViewById(R.id.idNomeVaca);
        numVaca = findViewById(R.id.idNumeroVaca);

        Button btSlavar = findViewById(R.id.idBTSalvaVaca);
        btSlavar.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(CadastroVaca.this, ListviewsVaca.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Vaca vaca = new Vaca();
        Dao bd = new Dao(this);

        if(nomeVaca.getText().toString().isEmpty()){
            nomeVaca.setError("Campo Obrigatório!");
            nomeVaca.requestFocus();
        }else if(numVaca.getText().toString().isEmpty()){
            numVaca.setError("Campo Obrigatório!");
            numVaca.requestFocus();
        }else {

            vaca.setNome(nomeVaca.getText().toString());
            vaca.setNumVaca(Integer.parseInt(numVaca.getText().toString()));

            try {
                bd.insertVaca(vaca);
            }catch (Exception e){
                Log.e("Erro", "Erro ao Cadastrar");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Vaca Cadastarda com Sucesso!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(CadastroVaca.this,  "", Toast.LENGTH_SHORT);
                    nomeVaca.setText("");
                    nomeVaca.requestFocus();
                    numVaca.setText("");

                }
            });
            builder.show();
            return;


        }

    }
}
