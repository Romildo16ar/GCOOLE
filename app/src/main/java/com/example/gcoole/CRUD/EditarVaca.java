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

import com.example.gcoole.Activity_Produtor;
import com.example.gcoole.Activity_Vaca;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.ListviewProdutor;
import com.example.gcoole.Listviews.ListviewsVaca;
import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.R;

public class EditarVaca extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextNomeVaca;
    private EditText editTextNumeroVaca;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_editar_vaca);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        editTextNomeVaca = (EditText) findViewById(R.id.idNomeVacaEditar);
        editTextNumeroVaca = (EditText) findViewById(R.id.idNumeroVacaEditar);

        editTextNomeVaca.setText(ListviewsVaca.vaca.getNome());
        editTextNumeroVaca.setText(String.valueOf(ListviewsVaca.vaca.getNumVaca()));

        Button btSalvar = findViewById(R.id.idBTSalvarEditar);
        btSalvar.setOnClickListener(this);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(EditarVaca.this, Activity_Vaca.class));
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

        if (editTextNomeVaca.getText().toString().isEmpty()) {
            editTextNomeVaca.setError("Campo Obrigatório!");
            editTextNomeVaca.requestFocus();
        } else if (editTextNumeroVaca.getText().toString().isEmpty()) {
            editTextNumeroVaca.setError("Campo Obrigatório!");
            editTextNumeroVaca.requestFocus();
        } else {
            vaca.setId(ListviewsVaca.vaca.getId());
            vaca.setNome(editTextNomeVaca.getText().toString());
            vaca.setNumVaca(Integer.parseInt(editTextNumeroVaca.getText().toString()));

            try {
                bd.updateVaca(vaca);
            } catch (Exception e) {
                Log.e("Erro", "Erro ao Cadastrar");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Vaca Atualizada com Sucesso!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(EditarVaca.this, "", Toast.LENGTH_SHORT);
                    startActivity(new Intent(EditarVaca.this, ListviewsVaca.class));
                    finishAffinity();

                }
            });
            builder.show();
            return;


        }
    }
}
