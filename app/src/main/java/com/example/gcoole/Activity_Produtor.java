package com.example.gcoole;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.CRUD.EditarProdutor;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.ListviewProdutor;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_Produtor extends AppCompatActivity {
    private TextView textViewNome;
    private TextView textViewNumProd;
    private TextView textViewPropreitarioTague;
    private TextView textViewCodigoSicronização;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        inicializarFireBase();

        textViewNome = findViewById(R.id.idAdapterNome);
        textViewNumProd = findViewById(R.id.idActivityNumeroProd);
        textViewPropreitarioTague = findViewById(R.id.idActivityDonoDoTaque);
        textViewCodigoSicronização = findViewById(R.id.idCodigoSicronizacao);

        textViewNome.setText("Nome: "+ ListviewProdutor.produtor.getNome());
        textViewNumProd.setText("Nº: "+ ListviewProdutor.produtor.getNumProd());

        if(ListviewProdutor.produtor.getTipo() == 1){
            textViewPropreitarioTague.setText("Gestor: Sim");
            textViewCodigoSicronização.setText("");
        }else {
            textViewPropreitarioTague.setText("Gestor: Não");
            textViewCodigoSicronização.setText("Código de Sicronização: "+ListviewProdutor.produtor.getCodigoSocronizacao());

        }

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_activity_produtor, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, ListviewProdutor.class));
                finishAffinity();
                break;

            case R.id.idBTEditarProd:
                startActivity(new Intent(Activity_Produtor.this, EditarProdutor.class));
                finishAffinity();
                break;
            case R.id.idBTDeletarProd:
                if(isOnline()){
                    Dao bd = new Dao(this);
                    if(ListviewProdutor.produtor.getTipo() == -1) {
                        databaseReference.child(ListviewProdutor.produtor.getCodigoSocronizacao()).removeValue();
                    }
                    bd.deleteProdutor(ListviewProdutor.produtor.getId());
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Produtor Excluido com Sucesso!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Activity_Produtor.this,  "", Toast.LENGTH_SHORT);
                            startActivity(new Intent(Activity_Produtor.this, ListviewProdutor.class));
                            finishAffinity();

                        }
                    });
                    builder.show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Produtor não Excluido. Sem Conexão com a internet!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Activity_Produtor.this,  "", Toast.LENGTH_SHORT);
                            startActivity(new Intent(Activity_Produtor.this, ListviewProdutor.class));
                            finishAffinity();

                        }
                    });
                    builder.show();
                }




                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
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
        FirebaseApp.initializeApp(Activity_Produtor.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
