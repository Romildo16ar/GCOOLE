package com.example.gcoole.Listviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Activity_Producao;
import com.example.gcoole.Adapters.AdapterProducaoPorProdutor;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.R;

import java.util.ArrayList;
import java.util.List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Listview_Producao_Por_Produtor extends AppCompatActivity implements View.OnClickListener{

    public static Producao producao;
    private ListView producoes;
    public static String nomeProdutor;
    private  List<Producao> producaoListPorId = new ArrayList<>();
    private EditText ano;

    private String[]mes = {"Todos","Janeiro", "Fevereiro", "Março" , "Abril", "Maio", "Junho", "Julho", "Agosto",
            "Setembro", "Outubro", "Novembro", "Dezembro"} ;
    private Spinner spinnerMes;
    private ArrayAdapter<String> arrayAdapterMes;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_producao_por_produtor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        producoes = (ListView) findViewById(R.id.idListProducaoPorProdutor);
        spinnerMes = (Spinner) findViewById(R.id.idSpinnerFiltro);
        ano = (EditText) findViewById(R.id.idAnoFiltro);



        Dao bd = new Dao(this);

        List<Producao> producaoList = bd.selecionarProducao();
        arrayAdapterMes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mes);
        spinnerMes.setAdapter(arrayAdapterMes);

        nomeProdutor = ListviewProdutorParaProducao.produtor.getNome();

        for(int i = 0; i < producaoList.size(); i++){
            if(ListviewProdutorParaProducao.produtor.getId() == producaoList.get(i).getIdProdutor()){

                producaoListPorId.add(producaoList.get(i));

            }

        }


        prencherLista(producaoListPorId);

        Button btFiltrar = (Button) findViewById(R.id.idBtFiltro);
        btFiltrar.setOnClickListener(this);

        
    }

    @Override
    public void onClick(View v) {
        List<Producao> producaoListPorIdComFiltro = new ArrayList<>();
        List<Producao> producaoListPorIdComFiltroAno = new ArrayList<>();
        int mesFiltro = -1;
        int m = spinnerMes.getSelectedItemPosition();


        if (mes[m].equals("Janeiro")){
            mesFiltro = 1;
        }else if(mes[m].equals("Fevereiro")){
            mesFiltro = 2;
        }else if (mes[m].equals("Março")){
            mesFiltro = 3;
        }else if (mes[m].equals("Abril")){
            mesFiltro = 4;
        }else if (mes[m].equals("Maio")){
            mesFiltro = 5;
        }else if (mes[m].equals("Junho")){
            mesFiltro = 6;
        }else if (mes[m].equals("Julho")){
            mesFiltro = 7;
        }else if (mes[m].equals("Agosto")){
            mesFiltro = 8;
        }else if (mes[m].equals("Setembro")){
            mesFiltro = 9;
        }else if (mes[m].equals("Outubro")){
            mesFiltro = 10;
        }else if (mes[m].equals("Novembro")){
            mesFiltro = 11;
        }else if (mes[m].equals("Dezembro")){
            mesFiltro = 12;
        }else if(mes[m].equals("Todos")){
            mesFiltro = 13;
        }
        if(mesFiltro == 13 && ano.getText().toString().isEmpty()){
            prencherLista(producaoListPorId);
        }else if(mesFiltro == 13) {
            for(int i = 0; i < producaoListPorId.size(); i ++){
                if(Integer.parseInt(ano.getText().toString()) == selecionaAno(producaoListPorId.get(i).getData())){
                    producaoListPorIdComFiltroAno.add(producaoListPorId.get(i));
                }
            }
            prencherLista(producaoListPorIdComFiltroAno);
        }else if (!ano.getText().toString().isEmpty()){
            for(int i = 0; i < producaoListPorId.size(); i ++){
                if(mesFiltro == selecionaMes(producaoListPorId.get(i).getData()) && Integer.parseInt(ano.getText().toString()) == selecionaAno(producaoListPorId.get(i).getData())){
                    producaoListPorIdComFiltro.add(producaoListPorId.get(i));
                }
            }
            prencherLista(producaoListPorIdComFiltro);

        }else{
            ano.setError("Preencha esse campo!");
            ano.requestFocus();
        }

    }

    public int selecionaMes(String data){
        String []vet;
        vet= data.split("/");
        int mes = Integer.parseInt(vet[1]);
        return mes;
    }
    public int selecionaAno(String data){
        String []vet;
        vet= data.split("/");
        int ano = Integer.parseInt(vet[2]);
        return ano;
    }

    public void prencherLista(List<Producao> producaoListPreecherProducao){
        TextView textViewnome = (TextView) findViewById(R.id.idTextViewProdutor);

        textViewnome.setText("Produtor: "+nomeProdutor);

        producoes.setAdapter(new AdapterProducaoPorProdutor(Listview_Producao_Por_Produtor.this, producaoListPreecherProducao));
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, ListviewProdutorParaProducao.class));
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
