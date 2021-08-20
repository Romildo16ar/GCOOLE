package com.example.gcoole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gcoole.CRUD.InserirProducao;
import com.example.gcoole.Listviews.ListviewProdutorParaProducao;
import com.example.gcoole.Listviews.ListviewProdutor;
import com.example.gcoole.Listviews.ListviewsVaca;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ativando as opções do menu
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.listProd:
                startActivity(new Intent(this, ListviewProdutor.class));
                return true;
            case R.id.ListVaca:
                startActivity(new Intent(this, ListviewsVaca.class));

                return true;
            case R.id.idInserirProducao:
                startActivity(new Intent(this, InserirProducao.class));

                return true;
            case R.id.idListProducao:
                startActivity(new Intent(this, ListviewProdutorParaProducao.class));

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}