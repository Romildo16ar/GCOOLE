package com.example.gcoole.Grafico;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.androidplot.xy.XYPlot;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.ListviewProdutorParaProducao;
import com.example.gcoole.Listviews.Listview_Producao_Por_Produtor;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.R;
import com.example.gcoole.Ultil.PdfCreator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
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
import java.util.Date;
import java.util.List;

public class Grafico_Anual_Producao extends AppCompatActivity {
    private XYPlot plot;
    private static final int REQUEST_EXTERNAL_STORAGe = 1;
    protected BarChart mChart;

    private static String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_grafico_anual_producao_barra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        verifystoragepermissions(this);


        //plot = findViewById(R.id.idPlotAnualProducao);

        prencherGrafico();
    }

    private void verifystoragepermissions(Grafico_Anual_Producao grafico_anual_producao) {
        int permissions = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // If storage permission is not given then request for External Storage Permission
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissionstorage, REQUEST_EXTERNAL_STORAGe);
        }
    }

    private void prencherGrafico() {
        Dao bd = new Dao(this);
        List<Producao> producaos = bd.selecionarProducao();


        final String[] meses = {"Jan", "Fev", "Mar", "Abr", "Maio", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        int aux1 = 0, aux2 = 0, aux3 = 0, aux4 = 0, aux5 = 0, aux6 = 0, aux7 = 0, aux8 = 0, aux9 = 0, aux10 = 0, aux11 = 0, aux12 = 0;
        final int[] seriaA = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < producaos.size(); i++) {
            if (ListviewProdutorParaProducao.produtor.getId() == producaos.get(i).getIdProdutor()) {
                if (Listview_Producao_Por_Produtor.anoGrafico == selecionaAno(producaos.get(i).getData())) {
                    if (selecionaMes(producaos.get(i).getData()) == 1) {

                        aux1 = aux1 + producaos.get(i).getQuant();
                        seriaA[0] = aux1;
                    } else if (selecionaMes(producaos.get(i).getData()) == 2) {

                        aux2 = aux2 + producaos.get(i).getQuant();
                        seriaA[1] = aux2;
                    } else if (selecionaMes(producaos.get(i).getData()) == 3) {

                        aux3 = aux3 + producaos.get(i).getQuant();
                        seriaA[2] = aux3;
                    } else if (selecionaMes(producaos.get(i).getData()) == 4) {

                        aux4 = aux4 + producaos.get(i).getQuant();
                        seriaA[3] = aux4;
                    } else if (selecionaMes(producaos.get(i).getData()) == 5) {

                        aux5 = aux5 + producaos.get(i).getQuant();
                        seriaA[4] = aux5;
                    } else if (selecionaMes(producaos.get(i).getData()) == 6) {

                        aux6 = aux6 + producaos.get(i).getQuant();
                        seriaA[5] = aux6;
                    } else if (selecionaMes(producaos.get(i).getData()) == 7) {

                        aux7 = aux7 + producaos.get(i).getQuant();
                        seriaA[6] = aux7;
                    } else if (selecionaMes(producaos.get(i).getData()) == 8) {

                        aux8 = aux8 + producaos.get(i).getQuant();
                        seriaA[7] = aux8;
                    } else if (selecionaMes(producaos.get(i).getData()) == 9) {

                        aux9 = aux9 + producaos.get(i).getQuant();
                        seriaA[8] = aux9;
                    } else if (selecionaMes(producaos.get(i).getData()) == 10) {

                        aux10 = aux10 + producaos.get(i).getQuant();
                        seriaA[9] = aux10;
                    } else if (selecionaMes(producaos.get(i).getData()) == 11) {

                        aux11 = aux11 + producaos.get(i).getQuant();
                        seriaA[10] = aux11;
                    } else if (selecionaMes(producaos.get(i).getData()) == 12) {

                        aux12 = aux12 + producaos.get(i).getQuant();
                        seriaA[11] = aux12;
                    }

                }
            }


        }

        mChart = (BarChart) findViewById(R.id.graficoHorizontalBarraAnualProducao);
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
        xAxis.setLabelCount(12);


        xAxis.setValueFormatter(new IndexAxisValueFormatter(meses));

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
        for (int i = 0; i < 12; i++) {
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



/*

        XYSeries series1 = new SimpleXYSeries(Arrays.asList(seriaA), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Quant em litro de Leite por mês");

        BarFormatter series1Format = new BarFormatter (Color.BLUE, Color.BLACK);

        series1Format.setInterpolationParams(new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
        //BarRenderer renderer = plot.getRenderer(BarRenderer.class);
        //renderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_GAP,10);
        //series1Format.setMarginLeft(PixelUtils.dpToPix(1));
        //series1Format.setMarginRight(PixelUtils.dpToPix(1));

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
*/

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

    public class CategoryBarChartXaxisFormatter implements IAxisValueFormatter {

        ArrayList<String> mValues;

        public CategoryBarChartXaxisFormatter(ArrayList<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            int val = (int) value;
            String label = "";
            if (val >= 0 && val < mValues.size()) {
                label = mValues.get(val);
            } else {
                label = "";
            }
            return label;
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(Grafico_Anual_Producao.this, Listview_Producao_Por_Produtor.class));
                break;
            case R.id.idPdfGraficoAnualProducao:
                //File imagem = screenshot(getWindow().getDecorView().getRootView(), "result");
                View view = getWindow().getDecorView().getRootView();
                Date date = new Date();

                // Here we are initialising the format of our image name
                CharSequence format = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
                try {

                    File diretorioRaiz = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File diretorio = new File(diretorioRaiz.getPath() + "/Imagens/");

                    if (!diretorio.exists()) {
                        diretorio.mkdir();
                    }
                    String nomeArquivo = diretorio.getPath() + "/graficoAnual.jpeg";

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
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Grafico Anual");
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
                    String nomeArquivo = diretorio.getPath() + "/Grafico Anual.pdf";
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

                    String nomeArquivo = diretorio.getPath() + "/graficoAnual.jpeg";

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

    protected static File screenshot(View view, String filename) {
        Date date = new Date();

        // Here we are initialising the format of our image name
        CharSequence format = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        try {

            File diretorioRaiz = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File diretorio = new File(diretorioRaiz.getPath() + "/Imagens/");

            if (!diretorio.exists()) {
                diretorio.mkdir();
            }
            String nomeArquivo = diretorio.getPath() + "/graficoAnual.jpeg";

            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            File imageurl = new File(nomeArquivo);
            FileOutputStream outputStream = new FileOutputStream(imageurl);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            outputStream.flush();
            outputStream.close();
            return imageurl;

        } catch (FileNotFoundException io) {
            io.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
