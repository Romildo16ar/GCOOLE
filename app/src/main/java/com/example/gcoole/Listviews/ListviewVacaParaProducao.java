package com.example.gcoole.Listviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Adapters.AdapterProducao;
import com.example.gcoole.Adapters.AdapterVacaParaProducao;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.R;

import java.util.List;

public class ListviewVacaParaProducao extends AppCompatActivity {
    public static Vaca vaca;
    private ListView vacas;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_vaca_para_producao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        vacas= (ListView) findViewById(R.id.idlistVacaParaProducao);

        Dao bd = new Dao(this);

        List<Vaca> listaVacas =  bd.selecionarVaca();


        vacas.setAdapter(new AdapterVacaParaProducao(ListviewVacaParaProducao.this, listaVacas));

        vacas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                vaca = (Vaca) vacas.getItemAtPosition(position);
                click(view);
            }


        });
    }

    private void click(View view){
        startActivity(new Intent(this, ListviewProducaoPorVaca.class));

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
                break;

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Dao bd = new Dao(this);
        List<Vaca> listaVaca = bd.selecionarVaca();
        vacas.setAdapter(new AdapterVacaParaProducao(ListviewVacaParaProducao.this, listaVaca));
    }
}
