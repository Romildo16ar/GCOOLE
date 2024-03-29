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

import com.example.gcoole.Activity_Producao;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.Listview_Producao_Por_Produtor;
import com.example.gcoole.Ultil.MaskEditUtil;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class EditarProducao extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextQuant;
    private EditText editTextData;
    private Spinner spinnerProdutor;
    private ArrayAdapter<String> arrayAdapterProdutor;
    private Produtor[]produtor;
    private String[]produtorNome;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_editar_producao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        inicializarFireBase();

        spinnerProdutor = (Spinner) findViewById(R.id.idEditarSpinnerProdutor);
        editTextQuant = (EditText) findViewById(R.id.idEditarQuant);
        editTextData = (EditText) findViewById(R.id.idEditarDataProducao);

        editTextData.addTextChangedListener(MaskEditUtil.mask(editTextData, MaskEditUtil.FORMAT_DATE));

        Dao bd = new Dao(this);

        List<Produtor> listaProdutor = bd.selecionarProdutor();

        produtor = new Produtor[listaProdutor.size()];
        produtorNome = new String[listaProdutor.size()];
        for (int i = 0; i < listaProdutor.size(); i++) {
            Log.e("Erro", "Id Produtor " +listaProdutor.get(i).getId() + listaProdutor.get(i).getNome());
            produtor[i] = listaProdutor.get(i);
            produtorNome[i] = listaProdutor.get(i).getNome();
        }

        arrayAdapterProdutor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, produtorNome);
        spinnerProdutor.setAdapter(arrayAdapterProdutor);
        String nome = null;
        for (int i = 0; i < listaProdutor.size(); i++) {
            if(Listview_Producao_Por_Produtor.producao.getIdProdutor() == listaProdutor.get(i).getId()){
                nome = listaProdutor.get(i).getNome();
            }
        }
        spinnerProdutor.setSelection(arrayAdapterProdutor.getPosition(nome));

        editTextQuant.setText(String.valueOf(Listview_Producao_Por_Produtor.producao.getQuant()));
        editTextData.setText(Listview_Producao_Por_Produtor.producao.getData());

        Button btSalvar = findViewById(R.id.idBTEditarInserirProducao);
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
                startActivity(new Intent(EditarProducao.this, Activity_Producao.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        Producao producao = new Producao();
        Dao bd = new Dao(this);
        int idprodutor = spinnerProdutor.getSelectedItemPosition();
        if (editTextQuant.getText().toString().isEmpty()){
            editTextQuant.setError("Campo Obrigatório");
            editTextQuant.requestFocus();
        }else if(!editTextData.getText().toString().isEmpty() && !validaData(editTextData.getText().toString())){
            editTextData.setError("Data Inválida");
            editTextData.requestFocus();

        }else /*if (validarProducao(editTextData.getText().toString(), idprodutor)){
            editTextData.setError("Data Já Possui Produção!");
        }else*/{

            producao.setId(Listview_Producao_Por_Produtor.producao.getId());
            producao.setQuant(Integer.parseInt(editTextQuant.getText().toString()));
            producao.setData(editTextData.getText().toString());
            producao.setIdProdutor(produtor[idprodutor].getId());
            producao.setIdOnline(Listview_Producao_Por_Produtor.producao.getIdOnline());
            Log.e("Erro", "Erro ao Cadastrar"+Listview_Producao_Por_Produtor.producao.getIdOnline());

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
                    bd.updateProducao(producao);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Produção Atualizada com Sucesso!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(EditarProducao.this, "", Toast.LENGTH_SHORT);
                            startActivity(new Intent(EditarProducao.this, Listview_Producao_Por_Produtor.class));
                            finishAffinity();

                        }
                    });
                    builder.show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Produção não Atualizada. Sem conexão com a internet!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(EditarProducao.this, "", Toast.LENGTH_SHORT);
                            startActivity(new Intent(EditarProducao.this, Listview_Producao_Por_Produtor.class));
                            finishAffinity();

                        }
                    });
                    builder.show();
                }

            } catch (Exception e) {
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
            editTextData.setError("Data Inválida!");
            editTextData.requestFocus();
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
        FirebaseApp.initializeApp(EditarProducao.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
