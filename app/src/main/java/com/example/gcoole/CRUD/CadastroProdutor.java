package com.example.gcoole.CRUD;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.ListviewProdutor;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.ValorPorLitro;
import com.example.gcoole.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.UUID;

public class CadastroProdutor extends AppCompatActivity implements View.OnClickListener {

    private EditText nomeProp;
    private CheckBox checkBoxPropTaque;
    private EditText numProd;
    List<Produtor> list = null;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_cadastro_produtor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Dao bd = new Dao(this);
        list = bd.selecionarProdutor();

        nomeProp = findViewById(R.id.idnomeProd);
        checkBoxPropTaque = findViewById(R.id.idradioBTdonoTaque);
        numProd = findViewById(R.id.idNumProd);

        if(list.size()== 0){
            checkBoxPropTaque.setChecked(true);
            checkBoxPropTaque.setEnabled(false);
            this.setTitle("Cadastro do Gestor");
        }
        inicializarFireBase();
        Button btSalvar = findViewById(R.id.idBtsalvar);
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
                startActivity(new Intent(CadastroProdutor.this, ListviewProdutor.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        Produtor prod = new Produtor();
        Dao bd = new Dao(this);
        String codigoDeSicronizacao = "";

        if(nomeProp.getText().toString().isEmpty()){
            nomeProp.setError("Campo Obrigatório!");
            nomeProp.requestFocus();
        }else if(numProd.getText().toString().isEmpty()){
            numProd.setError("Campo Obrigatório!");
            numProd.requestFocus();
        }else if (verificarProdutor(Integer.parseInt(numProd.getText().toString()))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Número do Produtor já em uso!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(CadastroProdutor.this,  "", Toast.LENGTH_SHORT);

                }
            });
            builder.show();

        }else{

            prod.setNome(nomeProp.getText().toString());
            prod.setNumProd(Integer.parseInt(numProd.getText().toString()));
            codigoDeSicronizacao = UUID.randomUUID().toString();
            prod.setCodigoSocronizacao(codigoDeSicronizacao);


            if(checkBoxPropTaque.isChecked()){
                prod.setTipo(1);
            }else{
                prod.setTipo(-1);
            }

            try {
                if(isOnline()){
                    List<ValorPorLitro> listValorPorlitro =bd.selecionarValorProLitro();
                    if(listValorPorlitro.size() == 0){
                        bd.insertProdutor(prod);
                        if(prod.getTipo() == -1){
                            databaseReference.child(codigoDeSicronizacao).child("produtor").child(codigoDeSicronizacao).setValue(prod);
                        }
                    }else{
                        bd.insertProdutor(prod);
                        if(prod.getTipo() == -1){
                            databaseReference.child(codigoDeSicronizacao).child("produtor").child(codigoDeSicronizacao).setValue(prod);
                            for(int i = 0; i< listValorPorlitro.size(); i++){
                                databaseReference.child(codigoDeSicronizacao).child("Valor_por_litro").child(listValorPorlitro.get(i).getIdOnline()).setValue(listValorPorlitro.get(i));

                            }

                        }

                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Produtor Cadastardo com Sucesso!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(CadastroProdutor.this,  "", Toast.LENGTH_SHORT);
                            nomeProp.setText("");
                            nomeProp.requestFocus();
                            numProd.setText("");
                            checkBoxPropTaque.setChecked(false);
                            if(list.size()== 0){
                                finish();
                                startActivity(new Intent(CadastroProdutor.this, MainActivity.class));
                            }

                        }
                    });
                    builder.show();
                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Produtor não Cadastrado. Sem Conexão com a internet!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(CadastroProdutor.this,  "", Toast.LENGTH_SHORT);

                            if(list.size()== 0){
                                finish();
                                startActivity(new Intent(CadastroProdutor.this, MainActivity.class));
                            }

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


    public boolean verificarProdutor(int numeroProdutor){
        Dao bd = new Dao(this);
        List<Produtor> produtorList = bd.selecionarProdutor();
        for(int i =0; i< produtorList.size(); i++){
            if(numeroProdutor == produtorList.get(i).getNumProd()){
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
    private void inicializarFireBase(){
        FirebaseApp.initializeApp(CadastroProdutor.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
