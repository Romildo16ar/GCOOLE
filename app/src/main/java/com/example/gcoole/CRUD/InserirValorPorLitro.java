package com.example.gcoole.CRUD;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.ValorPorLitro;
import com.example.gcoole.R;
import com.example.gcoole.Ultil.MaskEditUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.UUID;

public class InserirValorPorLitro extends AppCompatActivity implements View.OnClickListener {

    private String[]mes = {"Meses","Janeiro", "Fevereiro", "Março" , "Abril", "Maio", "Junho", "Julho", "Agosto",
            "Setembro", "Outubro", "Novembro", "Dezembro"} ;
    private Spinner spinnerMes;
    private ArrayAdapter<String> arrayAdapterMes;

    private EditText editTextvalorPorLitro;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
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
        inicializarFireBase();

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
        }else if (verificarCadastro(p ,Integer.parseInt(editTextano.getText().toString()))){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Valor para o mês referente já cadastrado!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(InserirValorPorLitro.this,  "", Toast.LENGTH_SHORT);


                }
            });
            builder.show();
        }else {
            String idOnline = UUID.randomUUID().toString();
            valorPorLitro.setValor(Float.parseFloat(editTextvalorPorLitro.getText().toString()));
            valorPorLitro.setAno(Integer.parseInt(editTextano.getText().toString()));
            valorPorLitro.setMes(p);
            valorPorLitro.setIdOnline(idOnline);

            try {

                if(isOnline()){
                    List<Produtor> listPodutor = bd.selecionarProdutor();
                    bd.inserirValorPorLitro(valorPorLitro);
                    for(int i = 0; i < listPodutor.size(); i++){
                        if(listPodutor.get(i).getTipo() == -1) {
                            databaseReference.child(listPodutor.get(i).getCodigoSocronizacao()).child("Valor_por_litro").child(valorPorLitro.getIdOnline()).setValue(valorPorLitro);
                        }
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
                }else {
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
                    builder.setTitle("Valor não foi salvo. Sem Conexão com a internet!");
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
                }



            }catch (Exception e){
                Log.e("Erro", "Erro ao Cadastrar");
            }



            return;



        }


    }

    private void inicializarFireBase(){
        FirebaseApp.initializeApp(InserirValorPorLitro.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    public boolean verificarCadastro(int mes , int ano){
        Dao bd = new Dao(this);
        List<ValorPorLitro> valorPorLitrosMensal = bd.selecionarValorProLitro();
        for(int i = 0; i < valorPorLitrosMensal.size(); i++){
            if(mes == valorPorLitrosMensal.get(i).getMes() && ano == valorPorLitrosMensal.get(i).getAno()){
                return true;
            }
        }
        return false;

    }
    public boolean isOnline(){
        try {
            ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Erro ao verificar se estava online", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
