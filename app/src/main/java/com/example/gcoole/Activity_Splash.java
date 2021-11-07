package com.example.gcoole;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.CRUD.CadastroVaca;
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

import java.util.List;

public class Activity_Splash extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        inicializarFireBase();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Dao dao = new Dao(Activity_Splash.this);
                List<Produtor> produtorList = dao.selecionarProdutor();
                List<Sicronizacao> sicronizacaoList = dao.selecionarSicronizacao();
                List<ValorPorLitro> valorPorLitroList = dao.selecionarValorProLitro();
                List<Producao> producaoList = dao.selecionarProducao();

                if(produtorList.size() == 0 && sicronizacaoList.size() == 0){
                    Intent intent = new Intent(Activity_Splash.this, TelaInicial.class);
                    startActivity(intent);
                    finish();
                }else if(sicronizacaoList.size() != 0) {
                    if(isOnline()){
                        for (int i = 0; i < valorPorLitroList.size(); i++){
                            dao.deleteValorPorLitro(valorPorLitroList.get(i).getId());
                        }
                        for (int i = 0; i < producaoList.size(); i++){
                            dao.deleteProducao(producaoList.get(i).getId());
                        }
                        for (int i = 0; i < produtorList.size(); i++){
                            dao.deleteProdutor(produtorList.get(i).getId());
                        }

                        databaseReference.child(sicronizacaoList.get(0).getCodigo()).child("Valor_por_litro").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for(DataSnapshot keyNote:snapshot.getChildren()){
                                    Dao bd = new Dao(Activity_Splash.this);
                                    ValorPorLitro valorPorLitro = keyNote.getValue(ValorPorLitro.class);
                                    assert valorPorLitro != null;
                                    bd.inserirValorPorLitro(valorPorLitro);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        databaseReference.child(sicronizacaoList.get(0).getCodigo()).child("producao").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot keyNote:snapshot.getChildren()){
                                    Dao bd = new Dao(Activity_Splash.this);
                                    Producao producao = keyNote.getValue(Producao.class);
                                    assert producao != null;
                                    bd.inserirProducao(producao);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        databaseReference.child(sicronizacaoList.get(0).getCodigo()).child("produtor").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot keyNote:snapshot.getChildren()){
                                    Dao bd = new Dao(Activity_Splash.this);
                                    Produtor produtor = keyNote.getValue(Produtor.class);
                                    assert produtor != null;
                                    bd.insertProdutor(produtor);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Intent intent = new Intent(Activity_Splash.this, MainActivity.class);
                        startActivity(intent);
                        finish();


                    }else{
                        Toast.makeText(getApplicationContext(), "Não foi possivel sicronizar os dados verifique a conexão com a internet!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Activity_Splash.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Intent intent = new Intent(Activity_Splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        }, 2000);
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
        FirebaseApp.initializeApp(Activity_Splash.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}
