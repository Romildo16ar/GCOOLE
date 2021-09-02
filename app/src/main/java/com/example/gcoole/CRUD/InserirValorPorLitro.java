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

import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.Listview_Valor_Por_Litro;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Modelo.ValorPorLitro;
import com.example.gcoole.R;
import com.example.gcoole.Ultil.MaskEditUtil;

public class InserirValorPorLitro extends AppCompatActivity implements View.OnClickListener {

    private String[]mes = {"Meses","Janeiro", "Fevereiro", "Março" , "Abril", "Maio", "Junho", "Julho", "Agosto",
            "Setembro", "Outubro", "Novembro", "Dezembro"} ;
    private Spinner spinnerMes;
    private ArrayAdapter<String> arrayAdapterMes;

    private EditText editTextvalorPorLitro;
    private EditText editTextano;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_inserir_valor_por_litro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        editTextvalorPorLitro = findViewById(R.id.idValorPorLitroInserir);
        editTextvalorPorLitro.addTextChangedListener(MaskEditUtil.mask(editTextvalorPorLitro, MaskEditUtil.FORMAT_VALOR));
        spinnerMes = (Spinner) findViewById(R.id.idSpinnerMesInserirValor);
        editTextano = findViewById(R.id.idAnoInsirirValor);
        editTextano.addTextChangedListener(MaskEditUtil.mask(editTextano, MaskEditUtil.FORMAT_ANO));


        arrayAdapterMes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mes);
        spinnerMes.setAdapter(arrayAdapterMes);

        Button btInserir = findViewById(R.id.idBtInserirValorPorLitro);
        btInserir.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(InserirValorPorLitro.this, Listview_Valor_Por_Litro.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        ValorPorLitro valorPorLitro = new ValorPorLitro();
        Dao bd = new Dao(this);

        int p = spinnerMes.getSelectedItemPosition();

        if(editTextvalorPorLitro.getText().toString().isEmpty()){
            editTextvalorPorLitro.setError("Campo Obrigatório!");
            editTextvalorPorLitro.requestFocus();
        }else if(editTextano.getText().toString().isEmpty()){
            editTextano.setError("Campo Obrigatório!");
            editTextano.requestFocus();
        }else if(p == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecione o Mês!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(InserirValorPorLitro.this,  "", Toast.LENGTH_SHORT);


                }
            });
            builder.show();
        }else{
            valorPorLitro.setValor(Float.parseFloat(editTextvalorPorLitro.getText().toString()));
            valorPorLitro.setAno(Integer.parseInt(editTextano.getText().toString()));
            valorPorLitro.setMes(p);

            try {

                bd.inserirValorPorLitro(valorPorLitro);

            }catch (Exception e){
                Log.e("Erro", "Erro ao Cadastrar");
            }

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
            builder.setTitle("Valor salvo com Sucesso!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(InserirValorPorLitro.this,  "", Toast.LENGTH_SHORT);
                    editTextvalorPorLitro.setText("");
                    editTextvalorPorLitro.requestFocus();
                    editTextano.setText("");

                }
            });
            builder.show();
            return;



        }


    }
}
