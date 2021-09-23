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

public class Grafico_Anual_Producao extends AppCompatActivity {
    private XYPlot plot;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_grafico_anual_producao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        plot = findViewById(R.id.idPlotAnualProducao);

        prencherGrafico();
    }

    private void prencherGrafico() {
        Dao bd = new Dao(this);
        List<Producao> producaos = bd.selecionarProducao();


        final String[] meses ={"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novenbro","Dezenbro"};
        int aux1 = 0,aux2 = 0,aux3 = 0,aux4 = 0,aux5 = 0, aux6 = 0, aux7 = 0, aux8 = 0, aux9 = 0,aux10 = 0, aux11 = 0, aux12 = 0;
        Number[] seriaA = {0,0,0,0,0,0,0,0,0,0,0,0};
        for(int i = 0; i < producaos.size(); i++){
            if(ListviewProdutorParaProducao.produtor.getId() == producaos.get(i).getIdProdutor()){
                if(Listview_Producao_Por_Produtor.anoGrafico == selecionaAno(producaos.get(i).getData())) {
                    if(selecionaMes(producaos.get(i).getData()) == 1){

                        aux1 = aux1 + producaos.get(i).getQuant();
                        seriaA[0] = aux1;
                    }else if(selecionaMes(producaos.get(i).getData()) == 2){

                        aux2 = aux2 + producaos.get(i).getQuant();
                        seriaA[1] = aux2;
                    }
                    else if(selecionaMes(producaos.get(i).getData()) == 3){

                        aux3 = aux3 + producaos.get(i).getQuant();
                        seriaA[2] = aux3;
                    }
                    else if(selecionaMes(producaos.get(i).getData()) == 4){

                        aux4 = aux4 + producaos.get(i).getQuant();
                        seriaA[3] = aux4;
                    }else if(selecionaMes(producaos.get(i).getData()) == 5){

                        aux5 = aux5 + producaos.get(i).getQuant();
                        seriaA[4] = aux5;
                    }else if(selecionaMes(producaos.get(i).getData()) == 6){

                        aux6 = aux6 + producaos.get(i).getQuant();
                        seriaA[5] = aux6;
                    }else if(selecionaMes(producaos.get(i).getData()) == 7){

                        aux7 = aux7 + producaos.get(i).getQuant();
                        seriaA[6] = aux7;
                    }else if(selecionaMes(producaos.get(i).getData()) == 8){

                        aux8 = aux8 + producaos.get(i).getQuant();
                        seriaA[7] = aux8;
                    }else if(selecionaMes(producaos.get(i).getData()) == 9){

                        aux9 = aux9 + producaos.get(i).getQuant();
                        seriaA[8] = aux9;
                    }else if(selecionaMes(producaos.get(i).getData()) == 10){

                        aux10 = aux10 + producaos.get(i).getQuant();
                        seriaA[9] = aux10;
                    }else if(selecionaMes(producaos.get(i).getData()) == 11){

                        aux11 = aux11 + producaos.get(i).getQuant();
                        seriaA[10] = aux11;
                    }else if(selecionaMes(producaos.get(i).getData()) == 12){

                        aux12 = aux12 + producaos.get(i).getQuant();
                        seriaA[11] = aux12;
                    }

                }
            }


        }


        XYSeries series1 = new SimpleXYSeries(Arrays.asList(seriaA), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Quant em litro de Leite por mês");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.BLACK, null , null);

        series1Format.setInterpolationParams(new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));


        plot.addSeries(series1,series1Format);
        plot.setDomainStepValue(12);

        plot.setTitle("Produçao do ano: "+Listview_Producao_Por_Produtor.anoGrafico+"\n Produtor: "+Listview_Producao_Por_Produtor.nomeProdutor);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number)obj).floatValue());
                return toAppendTo.append(meses[i]);
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
                startActivity(new Intent(Grafico_Anual_Producao.this, Listview_Producao_Por_Produtor.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
