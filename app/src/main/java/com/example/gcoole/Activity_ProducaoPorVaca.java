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
import com.example.gcoole.CRUD.EditarProducaoPorVaca;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.ListviewProducaoPorVaca;
import com.example.gcoole.Listviews.ListviewVacaParaProducao;
import com.example.gcoole.Listviews.Listview_Producao_Por_Produtor;
import com.example.gcoole.Modelo.Produtor;

import java.util.List;

public class Activity_ProducaoPorVaca extends AppCompatActivity {
    private TextView textViewNomeVaca;
    private TextView textViewQuant;
    private TextView textViewData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producao_por_vaca);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        textViewNomeVaca = findViewById(R.id.idNomeVacaProducaoPorVaca);
        textViewQuant = findViewById(R.id.idQuantActProducaoPorVaca);
        textViewData = findViewById(R.id.idDataActProducaoPorVaca);

        textViewNomeVaca.setText("Vaca: "+ ListviewProducaoPorVaca.nomeVaca);
        textViewQuant.setText("Quantidade: "+ListviewProducaoPorVaca.producao.getQuant());
        textViewData.setText("Data da Produção: "+ListviewProducaoPorVaca.producao.getData() );
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_activity_producao_por_vaca, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, ListviewProducaoPorVaca.class));
                finishAffinity();
                break;

            case R.id.idEditarProducaoPorVaca:
                startActivity(new Intent(Activity_ProducaoPorVaca.this, EditarProducaoPorVaca.class));
                finishAffinity();
                break;

            case R.id.idDeletarProducaoPorVaca:

                    Dao bd = new Dao(this);
                    List<Produtor> listPodutor = bd.selecionarProdutor();
                    bd.deleteProducaoPorvava(ListviewProducaoPorVaca.producao.getId());
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Produçao Excluida com Sucesso!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Activity_ProducaoPorVaca.this,  "", Toast.LENGTH_SHORT);
                            startActivity(new Intent(Activity_ProducaoPorVaca.this, ListviewProducaoPorVaca.class));
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
