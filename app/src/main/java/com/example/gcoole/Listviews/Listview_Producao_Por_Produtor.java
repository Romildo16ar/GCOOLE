package com.example.gcoole.Listviews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Activity_Producao;
import com.example.gcoole.Activity_Produtor;
import com.example.gcoole.Adapters.AdapterProducaoPorProdutor;
import com.example.gcoole.Adapters.AdapterProdutor;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.R;

import java.util.ArrayList;
import java.util.List;

public class Listview_Producao_Por_Produtor extends AppCompatActivity {

    public static Producao producao;
    private ListView producoes;
    public static String nomeProdutor;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_producao_por_produtor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        producoes = (ListView) findViewById(R.id.idListProducaoPorProdutor);


        Dao bd = new Dao(this);

        List<Producao> producaoList = bd.selecionarProducao();
        List<Producao> producaoListPorId = new ArrayList<>();
        nomeProdutor = ListviewProducao.produtor.getNome();
        for(int i = 0; i < producaoList.size(); i++){
            if(ListviewProducao.produtor.getId() == producaoList.get(i).getIdProdutor()){

                producaoListPorId.add(producaoList.get(i));

            }

        }
        TextView textViewnome = (TextView) findViewById(R.id.idTextViewProdutor);

        textViewnome.setText("Produtor: "+nomeProdutor);

        producoes.setAdapter(new AdapterProducaoPorProdutor(Listview_Producao_Por_Produtor.this, producaoListPorId));
        producaoList = null;

        producoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                producao = (Producao) producoes.getItemAtPosition(position);
                click(view);
            }


        });
        
        //int idProdutor = ListviewProducao.produtor.getId();
        
        
    }

    private void click(View view) {
       startActivity(new Intent(this, Activity_Producao.class));
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
