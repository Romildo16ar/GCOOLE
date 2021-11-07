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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;

import com.example.gcoole.Activity_Produtor;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.ListviewProdutor;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarProdutor extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNome;
    private EditText edtNumero;
    private CheckBox edtTipo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_editar_produtor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        inicializarFireBase();
        edtNome = (EditText) findViewById(R.id.idnomeProdEditar);
        edtNumero = (EditText) findViewById(R.id.idNumProdEditar);
        edtTipo = (CheckBox) findViewById(R.id.idradioBTdonoTaqueEditar);

        edtNome.setText(ListviewProdutor.produtor.getNome());
        edtNumero.setText(String.valueOf(ListviewProdutor.produtor.getNumProd()));
        if(ListviewProdutor.produtor.getTipo() == 1){
            edtTipo.setChecked(true);
        }else{
            edtTipo.setChecked(false);

        }

        Button btSalvar = findViewById(R.id.idBtsalvarEditar);
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
                startActivity(new Intent(EditarProdutor.this, Activity_Produtor.class));
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

        if(edtNome.getText().toString().isEmpty()){
            edtNome.setError("Campo Obrigatório!");
            edtNome.requestFocus();
        }else if(edtNumero.getText().toString().isEmpty()){
            edtNumero.setError("Campo Obrigatório!");
            edtNumero.requestFocus();
        }else {

            prod.setId(ListviewProdutor.produtor.getId());
            prod.setNome(edtNome.getText().toString());
            prod.setNumProd(Integer.parseInt(edtNumero.getText().toString()));
            prod.setCodigoSocronizacao(ListviewProdutor.produtor.getCodigoSocronizacao());


            if(edtTipo.isChecked()){
                prod.setTipo(1);
            }else{
                prod.setTipo(-1);
            }

            try {
                if(isOnline()){
                    bd.updateProdutor(prod);
                    if(prod.getTipo() == -1) {
                        databaseReference.child(ListviewProdutor.produtor.getCodigoSocronizacao()).child("produtor").child(ListviewProdutor.produtor.getCodigoSocronizacao()).setValue(prod);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Produtor Atualizado com Sucesso!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(EditarProdutor.this, "", Toast.LENGTH_SHORT);
                            startActivity(new Intent(EditarProdutor.this, ListviewProdutor.class));
                            finishAffinity();
                        }
                    });
                    builder.show();


                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Produtor não Alterado. Sem Conexão com a internet!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(EditarProdutor.this,  "", Toast.LENGTH_SHORT);
                        }
                    });
                    builder.show();
                }
            }catch (Exception e){
                Log.e("Erro", "Erro ao Editar");
            }

            return;

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
        FirebaseApp.initializeApp(EditarProdutor.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
