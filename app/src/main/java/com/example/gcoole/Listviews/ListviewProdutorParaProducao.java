package com.example.gcoole.Listviews;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Adapters.AdapterProducao;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.R;

import java.util.List;

public class ListviewProdutorParaProducao extends AppCompatActivity {
   // public static Producao producao;
   // private ListView producoes;
   public static Produtor produtor;
   private ListView produtores;
    private Dialog myDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_producao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        produtores= (ListView)findViewById(R.id.idlistviewProducao);


        Dao bd = new Dao(this);
       // List<Producao> listaProducao = bd.selecionarProducao();
        List<Produtor> listaProdutor = bd.selecionarProdutor();
        //Log.e("Erro", "Entrei"+listaProducao.get(0).getQuant());

        produtores.setAdapter(new AdapterProducao(ListviewProdutorParaProducao.this, listaProdutor));

        produtores.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                produtor = (Produtor) produtores.getItemAtPosition(position);
                click(view);
            }


        });
    }

    private void click(View view) {

        startActivity(new Intent(this, Listview_Producao_Por_Produtor.class));
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
        List<Produtor> listaProdutor = bd.selecionarProdutor();
        produtores.setAdapter(new AdapterProducao(ListviewProdutorParaProducao.this, listaProdutor));
    }

}
