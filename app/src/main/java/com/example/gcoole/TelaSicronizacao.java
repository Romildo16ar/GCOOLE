package com.example.gcoole;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.CRUD.InserirProducao;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.Sicronizacao;
import com.example.gcoole.Modelo.ValorPorLitro;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TelaSicronizacao extends AppCompatActivity implements View.OnClickListener{

    private EditText codigo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private int aux = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sicronizacao_produtor);

        codigo = findViewById(R.id.idEditTextCodigo);
        inicializarFireBase();

        Button btSicronizar = findViewById(R.id.idBotomSicronizar);
        btSicronizar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Dao bd = new Dao(TelaSicronizacao.this);

        if(codigo.getText().toString().isEmpty()) {
            codigo.setError("Campo Obrigatório!");
            codigo.requestFocus();


        }else if(!isOnline()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Sem conexão com a internet!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();

        }else{

            databaseReference.child(codigo.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getValue() == null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(TelaSicronizacao.this);
                        builder.setTitle("Código de Sicronização Invalido!");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();

                    }else{
                        Sicronizacao sicronizacao = new Sicronizacao();
                        sicronizacao.setCodigo(codigo.getText().toString());
                        sicronizacao.setFlag(1);
                        try {
                            bd.insertSicronizacao(sicronizacao);
                            databaseReference.child(sicronizacao.getCodigo()).child("Valor_por_litro").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    for(DataSnapshot keyNote:snapshot.getChildren()){
                                        Dao bd = new Dao(TelaSicronizacao.this);
                                        ValorPorLitro valorPorLitro = keyNote.getValue(ValorPorLitro.class);
                                        assert valorPorLitro != null;
                                        bd.inserirValorPorLitro(valorPorLitro);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            databaseReference.child(sicronizacao.getCodigo()).child("producao").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot keyNote:snapshot.getChildren()){
                                        Dao bd = new Dao(TelaSicronizacao.this);
                                        Producao producao = keyNote.getValue(Producao.class);
                                        assert producao != null;
                                        bd.inserirProducao(producao);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            databaseReference.child(sicronizacao.getCodigo()).child("produtor").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot keyNote:snapshot.getChildren()){
                                        Dao bd = new Dao(TelaSicronizacao.this);
                                        Produtor produtor = keyNote.getValue(Produtor.class);
                                        assert produtor != null;
                                        bd.insertProdutor(produtor);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });



                        }catch (Exception e){
                            Log.e("Erro", "Erro ao Sicroniza");
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(TelaSicronizacao.this);
                        builder.setTitle("Sicronização feita com Sucesso!");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(TelaSicronizacao.this, MainActivity.class));
                            }
                        });
                        builder.show();


                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




        }

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
        FirebaseApp.initializeApp(TelaSicronizacao.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}
