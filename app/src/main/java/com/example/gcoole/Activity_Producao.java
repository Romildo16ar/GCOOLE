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

import com.example.gcoole.CRUD.EditarProducao;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.Listview_Producao_Por_Produtor;

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

        textViewNomeProdutor = findViewById(R.id.idNomeProdutorActProducao);
        textViewQuant = findViewById(R.id.idQuantActProducao);
        textViewData = findViewById(R.id.idDataActProducao);

        textViewNomeProdutor.setText("Produtor: "+Listview_Producao_Por_Produtor.nomeProdutor);
        textViewQuant.setText("Quantidade: "+Listview_Producao_Por_Produtor.producao.getQuant());
        textViewData.setText("Data da Produção: "+Listview_Producao_Por_Produtor.producao.getData() );

    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_activity_producao, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, Listview_Producao_Por_Produtor.class));
                finishAffinity();
                break;

            case R.id.idEditarProducao:
                startActivity(new Intent(Activity_Producao.this, EditarProducao.class));
                finishAffinity();
                break;
            case R.id.idDeletarProducao:
                Dao bd = new Dao(this);
                bd.deleteProducao(Listview_Producao_Por_Produtor.producao.getId());
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Produçao Excluida com Sucesso!");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Activity_Producao.this,  "", Toast.LENGTH_SHORT);
                        startActivity(new Intent(Activity_Producao.this, Listview_Producao_Por_Produtor.class));
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
