package com.example.gcoole.Listviews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Activity_Producao;
import com.example.gcoole.Adapters.AdapterProducao;
import com.example.gcoole.Adapters.AdapterProdutor;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.R;

import java.util.List;

public class ListviewProducao extends AppCompatActivity {
    public static Producao producao;
    private ListView producoes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_producao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        producoes= (ListView)findViewById(R.id.idlistviewProducao);


        Dao bd = new Dao(this);
        List<Producao> listaProducao = bd.selecionarProducao();
        Log.e("Erro", "Entrei"+listaProducao.get(0).getQuant());
        producoes.setAdapter(new AdapterProducao(ListviewProducao.this, listaProducao));

        producoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                producao = (Producao) producoes.getItemAtPosition(position);
                click(view);
            }


        });
    }

    private void click(View view) {
        startActivity(new Intent(this, Activity_Producao.class));
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

            /*case R.id.addProp:
                startActivity(new Intent(this, CadastroProdutor.class));
                finishAffinity();
                break;*/
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Dao bd = new Dao(this);
        List<Producao> producaoList = null;
        producaoList = bd.selecionarProducao();
        producoes.setAdapter(new AdapterProducao(ListviewProducao.this, producaoList));
    }

}
