package com.example.gcoole.Grafico;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.ListviewProdutorParaProducao;
import com.example.gcoole.Listviews.Listview_Producao_Por_Produtor;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.R;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.List;

public class Grafico_Mensal_Producao extends AppCompatActivity {
    private XYPlot plot;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_grafico_mensal_producao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        plot = findViewById(R.id.idPlotMensal);

        prencherGrafico();

    }

    private void prencherGrafico() {
        Dao bd = new Dao(this);
        List<Producao> producaos = bd.selecionarProducao();


        final int[] dias ={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};


        Number[] seriaA = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        for(int i = 0; i < producaos.size(); i++){
            if(ListviewProdutorParaProducao.produtor.getId() == producaos.get(i).getIdProdutor()){
                if(Listview_Producao_Por_Produtor.mesGrafico == selecionaMes(producaos.get(i).getData())){
                    if(Listview_Producao_Por_Produtor.anoGrafico == selecionaAno(producaos.get(i).getData())) {

                        seriaA[selecionaDia(producaos.get(i).getData())] = producaos.get(i).getQuant();
                    }

                }
            }


        }


        XYSeries series1 = new SimpleXYSeries(Arrays.asList(seriaA), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Quant em litro de Leite");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.BLACK, null , null);

        series1Format.setInterpolationParams(new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));


        plot.addSeries(series1,series1Format);
        plot.setDomainStepValue(31);
        String mesNome = "";
        if (Listview_Producao_Por_Produtor.mesGrafico == 1){
            mesNome = "Janeiro";
        }else if(Listview_Producao_Por_Produtor.mesGrafico == 2){
           mesNome = "Fevereiro";
        }else if(Listview_Producao_Por_Produtor.mesGrafico == 3){
            mesNome = "Março";
        }else if(Listview_Producao_Por_Produtor.mesGrafico == 4){
            mesNome = "Abril";
        }else if(Listview_Producao_Por_Produtor.mesGrafico == 5){
            mesNome = "Maio";
        }else if(Listview_Producao_Por_Produtor.mesGrafico == 6){
            mesNome = "Junho";
        }else if(Listview_Producao_Por_Produtor.mesGrafico == 7){
            mesNome = "Julho";
        }else if(Listview_Producao_Por_Produtor.mesGrafico == 8){
            mesNome = "Agosto";
        }else if(Listview_Producao_Por_Produtor.mesGrafico == 9){
            mesNome = "Setembro";
        }else if(Listview_Producao_Por_Produtor.mesGrafico == 10){
            mesNome = "Outubro";
        }else if(Listview_Producao_Por_Produtor.mesGrafico == 11){
            mesNome = "Novembro";
        }else if(Listview_Producao_Por_Produtor.mesGrafico == 12){
            mesNome = "Dezembro";
        }
        plot.setTitle("Quant em litro de leite: "+mesNome+"/"+Listview_Producao_Por_Produtor.anoGrafico);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number)obj).floatValue());
                return toAppendTo.append(dias[i]);
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });





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
    public int selecionaDia(String data){
        String []vet;
        vet= data.split("/");
        int ano = Integer.parseInt(vet[0]);
        return ano;
    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(Grafico_Mensal_Producao.this, Listview_Producao_Por_Produtor.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
