package com.example.gcoole.Grafico;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

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
import com.example.gcoole.Modelo.Sicronizacao;
import com.example.gcoole.R;
import com.example.gcoole.Ultil.PdfCreator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Grafico_Mensal_Producao extends AppCompatActivity {
    private XYPlot plot;
    protected BarChart mChart;
    private String mesNome = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_grafico_mensal_producao_barra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //plot = findViewById(R.id.idPlotMensal);

        prencherGrafico();

    }

    private void prencherGrafico() {
        Dao bd = new Dao(this);
        List<Producao> producaos = bd.selecionarProducao();
        List<Sicronizacao> sicronizacaoList = bd.selecionarSicronizacao();

        final String[] dias ={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32"};


        final int[] seriaA = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        if(sicronizacaoList.size() != 0){
            for(int i = 0; i < producaos.size(); i++){

                    if(Listview_Producao_Por_Produtor.mesGrafico == selecionaMes(producaos.get(i).getData())){
                        if(Listview_Producao_Por_Produtor.anoGrafico == selecionaAno(producaos.get(i).getData())) {

                            seriaA[selecionaDia(producaos.get(i).getData())-1] = producaos.get(i).getQuant();
                        }

                    }
            }
        }else{
            for(int i = 0; i < producaos.size(); i++){
                if(ListviewProdutorParaProducao.produtor.getId() == producaos.get(i).getIdProdutor()){
                    if(Listview_Producao_Por_Produtor.mesGrafico == selecionaMes(producaos.get(i).getData())){
                        if(Listview_Producao_Por_Produtor.anoGrafico == selecionaAno(producaos.get(i).getData())) {

                            seriaA[selecionaDia(producaos.get(i).getData())-1] = producaos.get(i).getQuant();
                        }

                    }
                }


            }
        }


/*
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(seriaA), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Quant em litro de Leite");

        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.BLACK, null , null);

        series1Format.setInterpolationParams(new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));


        plot.addSeries(series1,series1Format);
        plot.setDomainStepValue(31);*/

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

      /*  plot.setTitle("Quant em litro de leite: "+mesNome+"/"+Listview_Producao_Por_Produtor.anoGrafico);

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
        });*/


        mChart = (BarChart) findViewById(R.id.graficomensalproducaoBarra);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(0);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(32);


        xAxis.setValueFormatter(new IndexAxisValueFormatter(dias));

        YAxis yl = mChart.getAxisLeft();
        yl.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yl.setDrawGridLines(false);
        yl.setEnabled(false);
        yl.setAxisMinimum(0f);

        YAxis yr = mChart.getAxisRight();
        yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f);




        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < 31; i++) {
            yVals1.add(new BarEntry(i, seriaA[i]));
        }


        BarDataSet set1;

        set1 = new BarDataSet(yVals1, "Produção Mensal");
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        set1.setColor(Color.DKGRAY);
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(.9f);

        mChart.setGridBackgroundColor(R.color.black);

        mChart.setData(data);
        mChart.setFitBars(true);
        mChart.getLegend().setEnabled(true);



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
        getMenuInflater().inflate(R.menu.menu_grafico, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(Grafico_Mensal_Producao.this, Listview_Producao_Por_Produtor.class));
                break;
            case R.id.idPdfGraficoAnualProducao:
                //File imagem = screenshot(getWindow().getDecorView().getRootView(), "result");
                View view = getWindow().getDecorView().getRootView();

                try {

                    File diretorioRaiz = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File diretorio = new File(diretorioRaiz.getPath() + "/Imagens/");

                    if (!diretorio.exists()) {
                        diretorio.mkdir();
                    }
                    String nomeArquivo = diretorio.getPath() + "/graficoMensalProducao.jpeg";

                    view.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
                    view.setDrawingCacheEnabled(false);
                    File imageurl = new File(nomeArquivo);
                    FileOutputStream outputStream = new FileOutputStream(imageurl);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
                    outputStream.flush();
                    outputStream.close();


                } catch (FileNotFoundException io) {
                    io.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                OutputStream outputStream = null;
                File pdf = null;
                Uri uri = null;
                ParcelFileDescriptor descriptor = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Grafico Mensal Produção");
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/Graficos/");

                    ContentResolver resolver = getContentResolver();
                    uri = resolver.insert(MediaStore.Downloads.getContentUri("external"), contentValues);

                    try {
                        descriptor = resolver.openFileDescriptor(uri, "rw");
                        outputStream = new FileOutputStream(descriptor.getFileDescriptor());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                } else {
                    File diretorioRaiz = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File diretorio = new File(diretorioRaiz.getPath() + "/Graficos/");

                    if (!diretorio.exists()) {
                        diretorio.mkdir();
                    }
                    String nomeArquivo = diretorio.getPath() + "/Grafico Mensal Produção "+mesNome+".pdf";
                    pdf = new File(nomeArquivo);
                    try {
                        outputStream = new FileOutputStream(pdf);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }

                Document document = new Document();

                PdfCreator pdfCreator = new PdfCreator();
                try {
                    PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
                    pdfWriter.setBoxSize("box", new Rectangle(0, 0, 0, 0));
                    pdfWriter.setPageEvent(pdfCreator);

                } catch (DocumentException e) {
                    e.printStackTrace();
                }




                document.open();

                document.addAuthor("Gcoole");


                Font fontNegritaTitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                try {
                    // Paragraph paragraphTitulo = new Paragraph("Grafico Anual ", fontNegritaTitulo);
                    // paragraphTitulo.setAlignment(Element.ALIGN_CENTER);
                    //document.add(paragraphTitulo);

                    File diretorioRaiz = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File diretorio = new File(diretorioRaiz.getPath() + "/Imagens/");

                    String nomeArquivo = diretorio.getPath() + "/graficoMensalProducao.jpeg";

                    File imgFile = new  File(nomeArquivo);

                    if(imgFile.exists()) {

                        Bitmap bm = BitmapFactory.decodeFile(imgFile.getAbsolutePath());


                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        Image img = null;


                        byte[] byteArray = stream.toByteArray();
                        try {
                            img = Image.getInstance(byteArray);

                            img.scaleToFit(800, 800);

                            document.add(img);
                        } catch (BadElementException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }





                    document.close();

                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    try {

                        descriptor.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    visualizarPdfUri(uri);
                } else {
                    visualizarPdfFile(pdf);
                }

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void visualizarPdfFile(File pdf){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("application/pdf");

        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider.getUriForFile(getBaseContext(),"com.example.gcoole.Grafico", pdf);
        intent1.setDataAndType(uri,"application/pdf");

        startActivityForResult(intent1,1234);



    }
    private void visualizarPdfUri(Uri uri){

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("application/pdf");

        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent1.setDataAndType(uri,"application/pdf");

        startActivityForResult(intent1,1234);



    }

}
