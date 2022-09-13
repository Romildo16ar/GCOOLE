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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.androidplot.xy.XYPlot;
import com.example.gcoole.Listviews.ListviewProducaoPorVaca;
import com.example.gcoole.Listviews.Listview_Producao_Por_Produtor;
import com.example.gcoole.Modelo.ProducaoPorVaca;
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
import java.util.ArrayList;
import java.util.List;

public class Grafico_producao_por_vaca extends AppCompatActivity {

    private XYPlot plot;
    protected BarChart mChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_grafico_producao_por_vaca);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        prencherGrafico(ListviewProducaoPorVaca.producaoListPorIdComFiltro);
    }


    private void prencherGrafico(List<ProducaoPorVaca> producaoPorVacaList){


        final String[] dias = new String[producaoPorVacaList.size()];

        final int[] seriaA = new int[producaoPorVacaList.size()];
        for(int i = 0; i < producaoPorVacaList.size(); i++){
            dias[i] = producaoPorVacaList.get(i).getData();
            seriaA[i]= producaoPorVacaList.get(i).getQuant();

        }

        mChart = (BarChart) findViewById(R.id.idGraficoVaca);
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
        xAxis.setLabelCount(ListviewProducaoPorVaca.producaoListPorIdComFiltro.size());


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
        for (int i = 0; i < producaoPorVacaList.size(); i++) {
            yVals1.add(new BarEntry(i, seriaA[i]));
        }


        BarDataSet set1;

        set1 = new BarDataSet(yVals1, "");
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


        producaoPorVacaList.clear();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_grafico, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(Grafico_producao_por_vaca.this, ListviewProducaoPorVaca.class));
                break;

            case R.id.idPdfGraficoAnualProducao:
                //File imagem = screenshot(getWindow().getDecorView().getRootView(), "result");
                View view = getWindow().getDecorView().getRootView();
                //Date date = new Date();

                // Here we are initialising the format of our image name
                //CharSequence format = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
                try {

                    File diretorioRaiz = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File diretorio = new File(diretorioRaiz.getPath() + "/Imagens/");

                    if (!diretorio.exists()) {
                        diretorio.mkdir();
                    }
                    String nomeArquivo = diretorio.getPath() + "/graficoProducaoPorVaca.jpeg";

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
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Grafico Produção or Vaca");
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
                    String nomeArquivo = diretorio.getPath() + "/Grafico Por Vaca.pdf";
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

                    String nomeArquivo = diretorio.getPath() + "/graficoProducaoPorVaca.jpeg";

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

