package com.example.gcoole.Listviews;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.gcoole.Activity_Producao;
import com.example.gcoole.Activity_ProducaoPorVaca;
import com.example.gcoole.Adapters.AdapterProducaoPorProdutor;
import com.example.gcoole.Adapters.AdapterProducaoPorVaca;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.ProducaoPorVaca;
import com.example.gcoole.Modelo.Sicronizacao;
import com.example.gcoole.R;
import com.example.gcoole.Ultil.MaskEditUtil;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListviewProducaoPorVaca extends AppCompatActivity implements View.OnClickListener {

    public static ProducaoPorVaca producao;
    private ListView producoes;
    public static String nomeVaca;
    public static int numeroVaca;
    private EditText dataIncial;
    private EditText dataFinal;
    private Date dateInicial;
    private Date dateFinal;
    private List<ProducaoPorVaca> producaoPorVacaList = new ArrayList<>();
    private  List<ProducaoPorVaca> producaoListPorId = new ArrayList<>();
    private  List<ProducaoPorVaca> producaoListPorIdComFiltro = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private int totalProducao = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_producao_por_vaca);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        producoes = (ListView) findViewById(R.id.idListProducaoPorVaca);

        dataIncial = (EditText) findViewById(R.id.idDataInicial);
        dataFinal = (EditText) findViewById(R.id.idDataFinal);

        dataIncial.addTextChangedListener(MaskEditUtil.mask(dataIncial, MaskEditUtil.FORMAT_DATE));
        dataFinal.addTextChangedListener(MaskEditUtil.mask(dataFinal, MaskEditUtil.FORMAT_DATE));






        nomeVaca = ListviewVacaParaProducao.vaca.getNome();
        numeroVaca = ListviewVacaParaProducao.vaca.getNumVaca();

        Dao bd = new Dao(this);
        producaoPorVacaList = bd.selecionarProducaoPorvaca();
        for(int i = 0; i <producaoPorVacaList.size(); i++){
            if(ListviewVacaParaProducao.vaca.getId() == producaoPorVacaList.get(i).getIdVaca()){
                producaoListPorId.add(producaoPorVacaList.get(i));
                producaoListPorIdComFiltro.add(producaoPorVacaList.get(i));
            }

        }

        prencherLista(producaoListPorId);
        Button btFiltrar = (Button) findViewById(R.id.idBtFiltroVaca);
        btFiltrar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        producaoListPorIdComFiltro.clear();

        if(dataIncial.getText().toString().isEmpty() && dataFinal.getText().toString().isEmpty()){
            prencherLista(producaoListPorId);
        }else if (!validaData(dataIncial.getText().toString())){
            dataIncial.requestFocus();
            dataIncial.setError("Data Inválida!");

            prencherLista(producaoListPorId);
        }else if(!validaData(dataFinal.getText().toString())){
            dataFinal.setError("Data Inválida!");
            dataFinal.requestFocus();
            prencherLista(producaoListPorId);
        }else {
            try {
                dateInicial = formatter.parse(dataIncial.getText().toString());
                dateFinal = formatter.parse(dataFinal.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for(int i =0; i < producaoListPorId.size(); i++) {
                try {
                    Date date = formatter.parse(producaoListPorId.get(i).getData());
                    if(date.after(dateInicial) && date.before(dateFinal)){
                        producaoListPorIdComFiltro.add(producaoListPorId.get(i));
                    }else if(date.equals(dateInicial) || date.equals(dateFinal)){
                        producaoListPorIdComFiltro.add(producaoListPorId.get(i));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            prencherLista(producaoListPorIdComFiltro);
        }

    }

    public void prencherLista(List<ProducaoPorVaca> producaoListPreecherProducao){
        TextView textViewnome = (TextView) findViewById(R.id.idTextViewVaca);
        Dao bd = new Dao(ListviewProducaoPorVaca.this);
        List<Sicronizacao> sicronizacaoList = bd.selecionarSicronizacao();

        textViewnome.setText("Vaca: "+nomeVaca);

        producoes.setAdapter(new AdapterProducaoPorVaca(ListviewProducaoPorVaca.this, producaoListPreecherProducao));

        if(sicronizacaoList.size() != 0){

        }else {
            producoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    producao = (ProducaoPorVaca) producoes.getItemAtPosition(position);
                    click(view);
                }

            });
        }


    }

    private void click(View view) {
        startActivity(new Intent(this, Activity_ProducaoPorVaca.class));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_listview_producao_por_vaca, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, ListviewVacaParaProducao.class));
                finishAffinity();
                break;

            case R.id.idPdfVaca:
                OutputStream outputStream = null;
                File pdf = null;
                Uri uri = null;
                ParcelFileDescriptor descriptor = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Produção por vaca " + nomeVaca);
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/Produção por vaca/");

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
                    File diretorio = new File(diretorioRaiz.getPath() + "/Produção/");

                    if (!diretorio.exists()) {
                        diretorio.mkdir();
                    }
                    String nomeArquivo = diretorio.getPath() + "/Produção " + nomeVaca + ".pdf";
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
                    Paragraph paragraphTitulo = new Paragraph(" Produção de " + nomeVaca + " N°: " + numeroVaca + " \n\n", fontNegritaTitulo);
                    paragraphTitulo.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraphTitulo);

                    if( dataIncial.getText().toString().isEmpty() || dataFinal.getText().toString().isEmpty() ){

                    }else {
                        document.add(new Paragraph("Período Data Inicial: " + dataIncial.getText().toString() + " Data Final: "+ dataFinal.getText().toString() +" \n\n"));
                    }





                    //BaseFont arial = BaseFont.createFont("resources/fonts/Arial.ttf" ,"CP1251", BaseFont.EMBEDDED);
                    Font font = new Font(Font.FontFamily.HELVETICA, 8);
                    Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
                    PdfPTable tabela1 = new PdfPTable(2);
                    tabela1.addCell(new PdfPCell((new Paragraph("Data", fontNegrita))));
                    tabela1.addCell(new PdfPCell((new Paragraph("Quantidade de Leite no dia", fontNegrita))));


                    ProducaoPorVaca p;
                    totalProducao = 0;

                    for (int i = 0; i < producaoListPorIdComFiltro.size(); i++) {
                        p = producaoListPorIdComFiltro.get(i);

                        tabela1.addCell(new PdfPCell((new Paragraph(p.getData(), font))));
                        tabela1.addCell(new PdfPCell((new Paragraph(String.valueOf(p.getQuant()), font))));
                        totalProducao = totalProducao + p.getQuant();


                    }
                    document.add(tabela1);


                    document.add(new Paragraph("Total Produzido: " + totalProducao + " \n\n"));

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

    private boolean validaData(String data){
        String[] vet;
        vet=data.split("/");
        try {
            int dia = Integer.parseInt(vet[0]);
            int mes = Integer.parseInt(vet[1]);
            int ano = Integer.parseInt(vet[2]);
            if(dia>0 && dia<=31 && mes>0 && mes<=12)
                return true;
        }catch (Exception e){

        }
        return false;
    }

    private void visualizarPdfFile(File pdf){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("application/pdf");

        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider.getUriForFile(getBaseContext(),"com.example.gcoole.Listviews", pdf);
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
