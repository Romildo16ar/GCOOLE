package com.example.gcoole;

import android.content.DialogInterface;
import android.content.Intent;
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

public class Activity_Produtor extends AppCompatActivity {
    private TextView textViewNome;
    private TextView textViewNumProd;
    private TextView textViewPropreitarioTague;
    private TextView textViewCodigoSicronização;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        textViewNome = findViewById(R.id.idAdapterNome);
        textViewNumProd = findViewById(R.id.idActivityNumeroProd);
        textViewPropreitarioTague = findViewById(R.id.idActivityDonoDoTaque);
        textViewCodigoSicronização = findViewById(R.id.idCodigoSicronizacao);

        textViewNome.setText("Nome: "+ ListviewProdutor.produtor.getNome());
        textViewNumProd.setText("Nº: "+ ListviewProdutor.produtor.getNumProd());
        textViewCodigoSicronização.setText("Código de Sicronização: "+ListviewProdutor.produtor.getCodigoSocronizacao());
        if(ListviewProdutor.produtor.getTipo() == 1){
            textViewPropreitarioTague.setText("Gestor: Sim");
        }else {
            textViewPropreitarioTague.setText("Gestor: Não");
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
                Dao bd = new Dao(this);
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



                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
