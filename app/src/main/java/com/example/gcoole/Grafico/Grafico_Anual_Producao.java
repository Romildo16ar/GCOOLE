package com.example.gcoole.Grafico;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.example.gcoole.R;
import com.example.gcoole.Ultil.PdfCreator;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Date;
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
        getMenuInflater().inflate(R.menu.menu_grafico_anual_producao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(Grafico_Anual_Producao.this, Listview_Producao_Por_Produtor.class));
                break;
            case R.id.idPdfGraficoAnualProducao:
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
                    Paragraph paragraphTitulo = new Paragraph("Grafico Anual ", fontNegritaTitulo);
                    paragraphTitulo.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraphTitulo);

                    Date now = new Date();
                    android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

                    try {
                        // image naming and path  to include sd card  appending name you choose for file
                        String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";


                        // create bitmap screen capture
                        View v1 = getWindow().getDecorView().getRootView();
                        v1.setDrawingCacheEnabled(true);
                        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                        v1.setDrawingCacheEnabled(false);

                        File imageFile = new File(mPath);

                        FileOutputStream Stream = new FileOutputStream(imageFile);
                        int quality = 100;
                        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, Stream);
                        Stream.flush();
                        Stream.close();

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        Uri ur = Uri.fromFile(imageFile);


                    } catch (Throwable e) {
                        // Several error may come out with file handling or DOM
                        e.printStackTrace();
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
    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    private void takeScreenshot() {
        Date now = new Date();
       // android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();


        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
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
