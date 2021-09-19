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
import com.example.gcoole.Listviews.Listview_Valor_Por_Litro;
import com.example.gcoole.Modelo.ValorPorLitro;
import com.example.gcoole.R;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.List;

public class Grafico_Anual_Valor_Por_litro extends AppCompatActivity {

    private XYPlot plot;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_grafico_anual_valor_por_litro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        plot = findViewById(R.id.idPlot);

        prencherGrafico();
    }

    private void prencherGrafico() {
        Dao bd = new Dao(this);
        List<ValorPorLitro> valorPorLitrosMensal = bd.selecionarValorProLitro();


        final String[] meses ={"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Sentembro","Outobro","Novenbro","Dezenbro"};


        Number[] seriaA = {0,0,0,0,0,0,0,0,0,0,0,0};

        for(int i = 0; i < valorPorLitrosMensal.size(); i++){
            if(Listview_Valor_Por_Litro.anoGrafico == valorPorLitrosMensal.get(i).getAno()){
                seriaA[valorPorLitrosMensal.get(i).getMes()-1] = valorPorLitrosMensal.get(i).getValor();

            }

        }


        XYSeries series1 = new SimpleXYSeries(Arrays.asList(seriaA), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Valor Por litro");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.BLACK, null , null);

        series1Format.setInterpolationParams(new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));


        plot.addSeries(series1,series1Format);
        plot.setDomainStepValue(12);
        plot.setTitle("Valor do Litro no ano: "+Listview_Valor_Por_Litro.anoGrafico);

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

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(Grafico_Anual_Valor_Por_litro.this, Listview_Valor_Por_Litro.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
