package com.example.gcoole.CRUD;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Activity_Vaca;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.Listview_Valor_Por_Litro;
import com.example.gcoole.Listviews.ListviewsVaca;
import com.example.gcoole.Modelo.ValorPorLitro;
import com.example.gcoole.R;
import com.example.gcoole.Ultil.MaskEditUtil;

public class EditarValorPorLitro extends AppCompatActivity implements View.OnClickListener {

    private String[]mes = {"Meses","Janeiro", "Fevereiro", "Março" , "Abril", "Maio", "Junho", "Julho", "Agosto",
            "Setembro", "Outubro", "Novembro", "Dezembro"} ;
    private Spinner editarSpinnerMes;
    private ArrayAdapter<String> editarArrayAdapterMes;

    private EditText editEditarTextvalorPorLitro;
    private EditText editEditarTextano;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_editar_valor_por_litro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        editEditarTextvalorPorLitro = findViewById(R.id.idEditarValorPorLitro);
        editEditarTextvalorPorLitro.addTextChangedListener(MaskEditUtil.mask(editEditarTextvalorPorLitro , MaskEditUtil.FORMAT_VALOR));
        editEditarTextano = findViewById(R.id.idEditarAno);
        editEditarTextano.addTextChangedListener(MaskEditUtil.mask(editEditarTextano, MaskEditUtil.FORMAT_ANO));
        editarSpinnerMes = (Spinner) findViewById(R.id.idSpinnerEditarMes);

        editEditarTextvalorPorLitro.setText(String.valueOf(Listview_Valor_Por_Litro.valorPorLitro.getValor()));
        editEditarTextano.setText(String.valueOf(Listview_Valor_Por_Litro.valorPorLitro.getAno()));



        editarArrayAdapterMes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mes);
        editarSpinnerMes.setAdapter(editarArrayAdapterMes);

        editarSpinnerMes.setSelection(Listview_Valor_Por_Litro.valorPorLitro.getMes());


        Button btSalvar = findViewById(R.id.idBTEditarValorPorLitro);
        btSalvar.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        ValorPorLitro valorPorLitro = new ValorPorLitro();
        Dao bd = new Dao(this);

        int p = editarSpinnerMes.getSelectedItemPosition();

        if(editEditarTextvalorPorLitro.getText().toString().isEmpty()){
            editEditarTextvalorPorLitro.setError("Campo Obrigatório!");
            editEditarTextvalorPorLitro.requestFocus();
        }else if(editEditarTextano.getText().toString().isEmpty()){
            editEditarTextano.setError("Campo Obrigatório!");
            editEditarTextano.requestFocus();
        }else if(p == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecione o Mês!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(EditarValorPorLitro.this,  "", Toast.LENGTH_SHORT);


                }
            });
            builder.show();
        }else {
            valorPorLitro.setId(Listview_Valor_Por_Litro.valorPorLitro.getId());
            valorPorLitro.setValor(Float.parseFloat(editEditarTextvalorPorLitro.getText().toString()));
            valorPorLitro.setAno(Integer.parseInt(editEditarTextano.getText().toString()));
            valorPorLitro.setMes(p);

            try {
                bd.updateValorPorLitro(valorPorLitro);

            } catch (Exception e) {
                Log.e("Erro", "Erro ao Cadastrar");
            }

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
            builder.setTitle("Valor Alterado com Sucesso!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(EditarValorPorLitro.this, "", Toast.LENGTH_SHORT);
                    startActivity(new Intent(EditarValorPorLitro.this, Listview_Valor_Por_Litro.class));
                    finishAffinity();

                }
            });
            builder.show();
            return;

        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(EditarValorPorLitro.this, Listview_Valor_Por_Litro.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
