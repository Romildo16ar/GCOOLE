package com.example.gcoole.Listviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Activity_Produtor;
import com.example.gcoole.Adapters.AdapterProdutor;
import com.example.gcoole.CRUD.CadastroProdutor;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.R;

import java.util.List;

public class ListviewProdutor extends AppCompatActivity {
    public static Produtor produtor;
    private ListView produtores;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_produtor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        produtores= (ListView)findViewById(R.id.idlistProd);


        Dao bd = new Dao(this);
        List<Produtor> listaProd = bd.selecionarProd();
        produtores.setAdapter(new AdapterProdutor(ListviewProdutor.this, listaProd));

        produtores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                produtor = (Produtor) produtores.getItemAtPosition(position);
                click(view);
            }


        });
    }

    private void click(View view) {
        startActivity(new Intent(this, Activity_Produtor.class));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_listview_prod, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
                break;

            case R.id.addProp:
                startActivity(new Intent(this, CadastroProdutor.class));
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
        List<Produtor> produtorList = null;
        produtorList = bd.selecionarProd();
        produtores.setAdapter(new AdapterProdutor(ListviewProdutor.this, produtorList));
    }
}
