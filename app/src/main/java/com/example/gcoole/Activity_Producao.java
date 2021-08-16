package com.example.gcoole;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.CRUD.EditarProdutor;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.ListviewProducao;
import com.example.gcoole.Listviews.ListviewProdutor;

public class Activity_Producao extends AppCompatActivity {
    private TextView textViewNomeProdutor;
    private TextView textViewQuant;
    private TextView textViewData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_producao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        textViewNomeProdutor = findViewById(R.id.idNomeProdutorActivity);
        textViewQuant = findViewById(R.id.idQuant);
        textViewData = findViewById(R.id.idDataProducaoAcitivity);

        textViewNomeProdutor.setText("Nome do Produtor: "+ ListviewProducao.producao.getIdProdutor());
        textViewQuant.setText("Quantidade: "+ ListviewProducao.producao.getQuant());
        textViewData.setText("Data da Produção: "+ListviewProducao.producao.getData());


    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, ListviewProducao.class));
                finishAffinity();
                break;

           /* case R.id.idBTEditarProd:
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



                break;*/
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
