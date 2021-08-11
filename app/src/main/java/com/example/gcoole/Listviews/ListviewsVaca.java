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

import com.example.gcoole.Activity_Produtor;
import com.example.gcoole.Activity_Vaca;
import com.example.gcoole.Adapters.AdapterProdutor;
import com.example.gcoole.Adapters.AdaptersVaca;
import com.example.gcoole.CRUD.CadastroProdutor;
import com.example.gcoole.CRUD.CadastroVaca;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.R;

import java.util.List;

public class ListviewsVaca extends AppCompatActivity {

    private ListView vacas;
    public static Vaca vaca;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_vaca);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        vacas= (ListView)findViewById(R.id.idListViewVaca);


        Dao bd = new Dao(this);
        List<Vaca> listaVaca = bd.selecionarVaca();
        vacas.setAdapter(new AdaptersVaca(ListviewsVaca.this, listaVaca));

        vacas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vaca = (Vaca) vacas.getItemAtPosition(position);
                click(view);
            }


        });


    }

    private void click(View view) {
        startActivity(new Intent(this, Activity_Vaca.class));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_listview_vaca, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
                break;

            case R.id.idAddVaca:
                startActivity(new Intent(this, CadastroVaca.class));
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
        List<Vaca> vacaList = null;
        vacaList = bd.selecionarVaca();
        vacas.setAdapter(new AdaptersVaca(ListviewsVaca.this, vacaList));
    }
}
