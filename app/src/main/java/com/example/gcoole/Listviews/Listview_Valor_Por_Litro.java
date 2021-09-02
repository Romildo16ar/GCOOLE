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

import com.example.gcoole.Activity_Vaca;
import com.example.gcoole.Activity_Valor_Por_Litro;
import com.example.gcoole.Adapters.AdapterValorPorLitro;
import com.example.gcoole.CRUD.CadastroProdutor;
import com.example.gcoole.CRUD.InserirValorPorLitro;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.Modelo.ValorPorLitro;
import com.example.gcoole.R;

import java.util.List;

public class Listview_Valor_Por_Litro extends AppCompatActivity {

    private ListView listViewvalorProLitro;
    public static ValorPorLitro valorPorLitro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_valor_por_litro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listViewvalorProLitro= (ListView) findViewById(R.id.idListViewValorPorLitro);
        Dao bd = new Dao(this);
        List<ValorPorLitro> listValorPorLitro = bd.selecionarValorProLitro();

        listViewvalorProLitro.setAdapter(new AdapterValorPorLitro(Listview_Valor_Por_Litro.this, listValorPorLitro));

        listViewvalorProLitro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                valorPorLitro = (ValorPorLitro) listViewvalorProLitro.getItemAtPosition(position);
                click(view);
            }


        });

    }

    private void click(View view) {
        startActivity(new Intent(this, Activity_Valor_Por_Litro.class));
    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_listview_valor_por_litro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
                break;

            case R.id.idaddValor:
                startActivity(new Intent(this, InserirValorPorLitro.class));
                finishAffinity();
                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
