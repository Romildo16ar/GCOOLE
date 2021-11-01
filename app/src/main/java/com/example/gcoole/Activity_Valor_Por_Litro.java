package com.example.gcoole;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.CRUD.CadastroProdutor;
import com.example.gcoole.CRUD.EditarVaca;
import com.example.gcoole.CRUD.EditarValorPorLitro;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.ListviewProdutor;
import com.example.gcoole.Listviews.Listview_Valor_Por_Litro;
import com.example.gcoole.Listviews.ListviewsVaca;
import com.example.gcoole.Modelo.Produtor;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Activity_Valor_Por_Litro extends AppCompatActivity {

    private TextView textViewvalorPorLitro;
    private TextView textVieMesEAno;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valor_por_litro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        inicializarFireBase();

        textViewvalorPorLitro = findViewById(R.id.idValorPorLitroActivity);
        textVieMesEAno = findViewById(R.id.idMesEAnoActivity);

        textViewvalorPorLitro.setText("Valor Por Litro Mensal: "+ Listview_Valor_Por_Litro.valorPorLitro.getValor());
        textVieMesEAno.setText("Mes e Ano Referênte: "+ Listview_Valor_Por_Litro.valorPorLitro.getMes()+"/"+Listview_Valor_Por_Litro.valorPorLitro.getAno());

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_activity_valor_por_litro, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, Listview_Valor_Por_Litro.class));
                finishAffinity();
                break;

            case R.id.idEditarValorPorLitro:
                startActivity(new Intent(Activity_Valor_Por_Litro.this, EditarValorPorLitro.class));
                finishAffinity();
                break;
            case R.id.idDeletarValorProLitro:
                if(isOnline()){
                    Dao bd = new Dao(this);
                    List<Produtor> listPodutor = bd.selecionarProdutor();
                    for(int i = 0; i < listPodutor.size(); i++) {
                        if (listPodutor.get(i).getTipo() == -1) {
                            databaseReference.child(listPodutor.get(i).getCodigoSocronizacao()).child("Valor_por_litro").child(Listview_Valor_Por_Litro.valorPorLitro.getIdOnline()).removeValue();
                            Log.e("Erro", "Entrei");
                        }
                    }
                    bd.deleteValorPorLitro(Listview_Valor_Por_Litro.valorPorLitro.getId());
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Valor Excluido com Sucesso!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Activity_Valor_Por_Litro.this, "", Toast.LENGTH_SHORT);
                            startActivity(new Intent(Activity_Valor_Por_Litro.this, Listview_Valor_Por_Litro.class));
                            finishAffinity();
                        }
                    });
                    builder.show();

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Valor não Excluido. Sem Conexão com a internet!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Activity_Valor_Por_Litro.this, "", Toast.LENGTH_SHORT);
                            startActivity(new Intent(Activity_Valor_Por_Litro.this, Listview_Valor_Por_Litro.class));
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
        FirebaseApp.initializeApp(Activity_Valor_Por_Litro.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

}
