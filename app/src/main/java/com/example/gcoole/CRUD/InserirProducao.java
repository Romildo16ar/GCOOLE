package com.example.gcoole.CRUD;

import android.app.AlertDialog;
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
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Dao.Dao;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Ultil.MaskEditUtil;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class InserirProducao extends AppCompatActivity implements View.OnClickListener {

    private EditText quant;
    private EditText dataProducao;
    private Spinner spinnerProdutor;
    private ArrayAdapter<String> arrayAdapterProdutor;
    private Produtor []produtor;
    private String[]produtorNome;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_inserir_producao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        inicializarFireBase();


        spinnerProdutor = (Spinner) findViewById(R.id.idSpinnerProdutor);

        Dao bd = new Dao(this);

        List<Produtor> listaProdutor = bd.selecionarProdutor();

        produtor = new Produtor[listaProdutor.size()];
        produtorNome = new String[listaProdutor.size()];
        for (int i = 0; i < listaProdutor.size(); i++) {
           // Log.e("Erro", "Id Produtor " +listaProdutor.get(i).getId() + listaProdutor.get(i).getNome());
            produtor[i] = listaProdutor.get(i);
            produtorNome[i] = listaProdutor.get(i).getNome();
        }

        arrayAdapterProdutor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, produtorNome);
        spinnerProdutor.setAdapter(arrayAdapterProdutor);

        quant = (EditText) findViewById(R.id.idQuant);
        dataProducao = (EditText) findViewById(R.id.idDataProducao);

        dataProducao.addTextChangedListener(MaskEditUtil.mask(dataProducao, MaskEditUtil.FORMAT_DATE));

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formatteDate = df.format(c.getTime());
        dataProducao.setText(formatteDate);

        Button btInserir = (Button) findViewById(R.id.idBTInserirProducao);

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
                startActivity(new Intent(InserirProducao.this, MainActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Dao bd = new Dao(this);
        Producao producao = new Producao();
        int p = spinnerProdutor.getSelectedItemPosition();
        int pro;
        if (produtor.length == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Importante");
            builder.setMessage("Por favor selecione um Produtor.\n Caso a lista de Produtor esteja vazia, faça o cadastro antes de continuar. ");
            builder.setPositiveButton("OK", (dialog, which) -> {
                Toast.makeText(InserirProducao.this, "", Toast.LENGTH_SHORT);
            });
            builder.show();
            return;

        }else{
            pro = produtor[p].getId();

        }
        if(quant.getText().toString().isEmpty()){
            quant.setError("Preencha esse campo!");
            quant.requestFocus();
        }else if(!dataProducao.getText().toString().isEmpty() && !validaData(dataProducao.getText().toString())){
            dataProducao.setError("Data Inválida!");
            dataProducao.requestFocus();
        }else if(validarProducao(dataProducao.getText().toString(), pro)){
            dataProducao.setError("Data Já Possui Produção!");
        }else{
            String idOnline = UUID.randomUUID().toString();
            producao.setQuant(Integer.parseInt(quant.getText().toString()));
            producao.setData(dataProducao.getText().toString());
            Log.e("Erro", "Id Produtor " +pro);
            producao.setIdProdutor(pro);
            producao.setIdOnline(idOnline);

            try {
                if(isOnline()){
                    List<Produtor> listPodutor = bd.selecionarProdutor();
                    for(int i = 0; i < listPodutor.size(); i++) {
                        if (listPodutor.get(i).getTipo() == -1) {
                            if(listPodutor.get(i).getId() == producao.getIdProdutor()){
                                databaseReference.child(listPodutor.get(i).getCodigoSocronizacao()).child("producao").child(producao.getIdOnline()).setValue(producao);
                            }
                        }
                    }
                    bd.inserirProducao(producao);
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
                    builder.setTitle("Produção salva com Sucesso!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(InserirProducao.this,  "", Toast.LENGTH_SHORT);
                            quant.setText("");
                            quant.requestFocus();

                        }
                    });
                    builder.show();

                }else{
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
                    builder.setTitle("Produção não foi salva. Sem conexão com a internet!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(InserirProducao.this,  "", Toast.LENGTH_SHORT);
                            quant.setText("");
                            quant.requestFocus();

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

    private boolean validaData(String data){
        String[] vet;
        vet=data.split("/");
        try {
            int dia = Integer.parseInt(vet[0]);
            int mes = Integer.parseInt(vet[1]);
            int ano = Integer.parseInt(vet[2]);
            if(dia>0 && dia<=31 && mes>0 && mes<=12)
                return true;
        }catch (Exception e){
            dataProducao.setError("Data Inválida!");
            dataProducao.requestFocus();
        }
        return false;
    }

    private boolean validarProducao(String dataNova , int idProdutor){

        String[] vetDataNova;
        vetDataNova=dataNova.split("/");
        Dao bd = new Dao(this);
        List<Producao> producao = bd.selecionarProducao();


        int diaNova = Integer.parseInt(vetDataNova[0]);
        int mesNova = Integer.parseInt(vetDataNova[1]);
        int anoNova = Integer.parseInt(vetDataNova[2]);

        for(int i = 0; i < producao.size(); i++){
            if(idProdutor == producao.get(i).getIdProdutor()){
                String[] vetData;
                vetData=producao.get(i).getData().split("/");
                int dia = Integer.parseInt(vetData[0]);
                int mes = Integer.parseInt(vetData[1]);
                int ano = Integer.parseInt(vetData[2]);
                if(dia == diaNova && mes == mesNova && ano == anoNova){
                    return true;
                }
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
    private void inicializarFireBase(){
        FirebaseApp.initializeApp(InserirProducao.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
