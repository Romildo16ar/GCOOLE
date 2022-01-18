package com.example.gcoole.Listviews;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.gcoole.Activity_Producao;
import com.example.gcoole.Adapters.AdapterProducaoPorProdutor;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Grafico.Grafico_Anual_Producao;
import com.example.gcoole.Grafico.Grafico_Mensal_Producao;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.Sicronizacao;
import com.example.gcoole.Modelo.ValorPorLitro;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Listview_Producao_Por_Produtor extends AppCompatActivity implements View.OnClickListener{

    public static Producao producao;
    private ListView producoes;
    public static String nomeProdutor;
    public static int numeroProdutor;
    private  List<Producao> producaoListPorId = new ArrayList<>();
    private EditText ano;
    private String mesPdf = "";
    private int mesFiltro = -1;
    private String anoPdf = "";
    private int totalProducao = 0;
    public static int anoGrafico =0;
    public static int mesGrafico = 0;
    private int mesDinheiro = 0;
    private int anoDinheiro = 0;

    private List<Producao> producaoListPorIdComFiltro = new ArrayList<>();
  //  private List<Producao> producaoListPorIdComFiltroAno = new ArrayList<>();

    private String[]mes = {"Meses","Janeiro", "Fevereiro", "Março" , "Abril", "Maio", "Junho", "Julho", "Agosto",
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
        ano.addTextChangedListener(MaskEditUtil.mask(ano, MaskEditUtil.FORMAT_ANO));


        Dao bd = new Dao(this);

        List<Producao> producaoList = bd.selecionarProducao();
        List<Sicronizacao> sicronizacaoList = bd.selecionarSicronizacao();
        //producaoListPorIdComFiltro = bd.selecionarProducao();
        arrayAdapterMes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mes);
        spinnerMes.setAdapter(arrayAdapterMes);

        nomeProdutor = ListviewProdutorParaProducao.produtor.getNome();
        numeroProdutor = ListviewProdutorParaProducao.produtor.getNumProd();

        if(sicronizacaoList.size() != 0){
            for(int i = 0; i < producaoList.size(); i++){
                producaoListPorId.add(producaoList.get(i));
                producaoListPorIdComFiltro.add(producaoList.get(i));
            }
        }else{
            for(int i = 0; i < producaoList.size(); i++){
                if(ListviewProdutorParaProducao.produtor.getId() == producaoList.get(i).getIdProdutor()){

                    producaoListPorId.add(producaoList.get(i));
                    producaoListPorIdComFiltro.add(producaoList.get(i));

                }

            }
        }



        prencherLista(producaoListPorId);

        Button btFiltrar = (Button) findViewById(R.id.idBtFiltro);
        btFiltrar.setOnClickListener(this);

        
    }

    @Override
    public void onClick(View v) {
        producaoListPorIdComFiltro.clear();
        mesFiltro =-1;

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
        }else if(mes[m].equals("Meses")){
            mesFiltro = 13;

        }
        if(mesFiltro == 13 && ano.getText().toString().isEmpty()){
            prencherLista(producaoListPorId);
            mesFiltro =-1;
            Dao bd = new Dao(this);
            for(int i = 0; producaoListPorId.size() > i; i++){
                producaoListPorIdComFiltro.add(producaoListPorId.get(i));
            }
        }else if(mesFiltro == 13) { //filto por ano
            for(int i = 0; i < producaoListPorId.size(); i ++){
                anoPdf = ano.getText().toString();
                if(Integer.parseInt(ano.getText().toString()) == selecionaAno(producaoListPorId.get(i).getData())){
                    producaoListPorIdComFiltro.add(producaoListPorId.get(i));

                }
            }
            prencherLista(producaoListPorIdComFiltro);
        }else if (!ano.getText().toString().isEmpty()){ //filtro  por mes
            for(int i = 0; i < producaoListPorId.size(); i ++){
                if(mesFiltro == selecionaMes(producaoListPorId.get(i).getData()) && Integer.parseInt(ano.getText().toString()) == selecionaAno(producaoListPorId.get(i).getData())){
                    producaoListPorIdComFiltro.add(producaoListPorId.get(i));

                }
            }
            mesPdf = mes[m];
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
        Dao bd = new Dao(Listview_Producao_Por_Produtor.this);
        List<Sicronizacao> sicronizacaoList = bd.selecionarSicronizacao();

        textViewnome.setText("Produtor: "+nomeProdutor);

        producoes.setAdapter(new AdapterProducaoPorProdutor(Listview_Producao_Por_Produtor.this, producaoListPreecherProducao));

        if(sicronizacaoList.size() != 0){

        }else {
            producoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    producao = (Producao) producoes.getItemAtPosition(position);
                    click(view);
                }

            });
        }


    }

    private void click(View view) {
       startActivity(new Intent(this, Activity_Producao.class));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_listview_producao_por_producao, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, ListviewProdutorParaProducao.class));
                finishAffinity();
                break;

            case R.id.idPdf:
                OutputStream outputStream = null;
                File pdf = null;
                Uri uri = null;
                ParcelFileDescriptor descriptor = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Produção " + nomeProdutor);
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/Produção/");

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
                    String nomeArquivo = diretorio.getPath() + "/Produção " + nomeProdutor + ".pdf";
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
                    Paragraph paragraphTitulo = new Paragraph(" Produção de " + nomeProdutor + " N°: " + numeroProdutor + " \n\n", fontNegritaTitulo);
                    paragraphTitulo.setAlignment(Element.ALIGN_CENTER);
                    document.add(paragraphTitulo);
                    if (mesFiltro == 13) {
                        document.add(new Paragraph("Referênte ao Ano: " + anoPdf + " \n\n"));
                    } else if (mesFiltro == -1) {

                    } else {
                        document.add(new Paragraph("Referênte ao Mês: " + mesPdf + " \n\n"));
                    }


                    //BaseFont arial = BaseFont.createFont("resources/fonts/Arial.ttf" ,"CP1251", BaseFont.EMBEDDED);
                    Font font = new Font(Font.FontFamily.HELVETICA, 8);
                    Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
                    PdfPTable tabela1 = new PdfPTable(2);
                    tabela1.addCell(new PdfPCell((new Paragraph("Data", fontNegrita))));
                    tabela1.addCell(new PdfPCell((new Paragraph("Quantidade de Leite", fontNegrita))));


                    Producao p;
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
            case R.id.idDinheiro:

                AlertDialog.Builder builderInicial = new AlertDialog.Builder(this);
                builderInicial.setTitle("Calculo Mensal");
                builderInicial.setMessage("Selecione a opção desejada:");
                builderInicial.setPositiveButton("Valor a Receber", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        AlertDialog.Builder builderDinheiro = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                        builderDinheiro.setTitle("Digite o ano desejado!");
                        //builder.setMessage("Valor do litro:  Total da Produção");
                        final EditText inputAno = new EditText(Listview_Producao_Por_Produtor.this);
                        inputAno.addTextChangedListener(MaskEditUtil.mask(inputAno, MaskEditUtil.FORMAT_ANO));
                        inputAno.setInputType(InputType.TYPE_CLASS_NUMBER);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        inputAno.setLayoutParams(lp);
                        builderDinheiro.setView(inputAno);

                        builderDinheiro.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                mesDinheiro = 0;
                                if (inputAno.getText().toString().isEmpty()) {
                                    Toast.makeText(Listview_Producao_Por_Produtor.this, "Campo Obrigatório! Opção Cancelada", Toast.LENGTH_SHORT).show();

                                } else {
                                    anoDinheiro = Integer.parseInt(inputAno.getText().toString());
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                                    builder.setTitle("Selecione o Mês");

                                    String[] animals = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novenbro", "Dezenbro"};
                                    int checkedItem = 0;
                                    builder.setSingleChoiceItems(animals, checkedItem, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mesDinheiro = which + 1;
                                        }
                                    });


                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Producao p;
                                            totalProducao = 0;
                                            if(mesDinheiro == 0){
                                                mesDinheiro = 1;
                                            }
                                            float auxValor = 0;
                                            float total = 0;
                                            Dao bd = new Dao(Listview_Producao_Por_Produtor.this);
                                            List<ValorPorLitro> valorPorLitrosMensal = bd.selecionarValorProLitro();
                                            List<Producao> producaoList = bd.selecionarProducao();
                                            List<Sicronizacao> sicronizacaoList = bd.selecionarSicronizacao();
                                            if(sicronizacaoList.size() != 0){
                                                for(int i = 0; i < producaoList.size(); i++){
                                                    if(selecionaAno(producaoList.get(i).getData()) == anoDinheiro){
                                                        if(selecionaMes(producaoList.get(i).getData()) == mesDinheiro){
                                                            p=producaoList.get(i);
                                                            totalProducao = totalProducao + p.getQuant();
                                                        }
                                                    }

                                                }

                                            }else{
                                                for(int i = 0; i < producaoList.size(); i++){
                                                    if(producaoList.get(i).getIdProdutor() == ListviewProdutorParaProducao.produtor.getId()){
                                                        if(selecionaAno(producaoList.get(i).getData()) == anoDinheiro){
                                                            if(selecionaMes(producaoList.get(i).getData()) == mesDinheiro){
                                                                p=producaoList.get(i);
                                                                totalProducao = totalProducao + p.getQuant();
                                                            }
                                                        }

                                                    }

                                                }
                                            }


                                            for(int i = 0; i < valorPorLitrosMensal.size(); i++){
                                                if (mesDinheiro == valorPorLitrosMensal.get(i).getMes() && anoDinheiro == valorPorLitrosMensal.get(i).getAno()){

                                                    auxValor = valorPorLitrosMensal.get(i).getValor();


                                                }
                                            }

                                            total = auxValor*totalProducao;

                                            if(auxValor == 0){
                                                AlertDialog.Builder builder = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                                                builder.setTitle("Importante!");
                                                builder.setMessage("Mês Referente não possui valor do litro Cadastrado! \nRetorne a tela inicial para afetual o cadastro.");
                                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Toast.makeText(Listview_Producao_Por_Produtor.this, "", Toast.LENGTH_SHORT);
                                                    }
                                                });
                                                builder.show();
                                            }else{
                                                AlertDialog.Builder builder = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                                                builder.setTitle("Valor Total Do mes: "+mes[mesDinheiro]);
                                                builder.setMessage("Valor do litro: "+auxValor+" \n Total da Produção:"+totalProducao+"\n Valor a Receber:"+total);
                                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Toast.makeText(Listview_Producao_Por_Produtor.this, "", Toast.LENGTH_SHORT);
                                                    }
                                                });
                                                builder.show();
                                            }

                                        }
                                    });
                                    builder.setNegativeButton("Cancel", null);

                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                }

                            }
                        });
                        builderDinheiro.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(Listview_Producao_Por_Produtor.this, "Opção Cancelada", Toast.LENGTH_SHORT).show();
                            }
                        });


                        builderDinheiro.show();



                    }
                });
                //define um botão como negativo.
                builderInicial.setNegativeButton("Valor a Pagar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        AlertDialog.Builder builderDinheiro = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                        builderDinheiro.setTitle("Digite o ano desejado!");
                        //builder.setMessage("Valor do litro:  Total da Produção");
                        final EditText inputAno = new EditText(Listview_Producao_Por_Produtor.this);
                        inputAno.addTextChangedListener(MaskEditUtil.mask(inputAno, MaskEditUtil.FORMAT_ANO));
                        inputAno.setInputType(InputType.TYPE_CLASS_NUMBER);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        inputAno.setLayoutParams(lp);
                        builderDinheiro.setView(inputAno);

                        builderDinheiro.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                mesDinheiro = 0;
                                if (inputAno.getText().toString().isEmpty()) {
                                    Toast.makeText(Listview_Producao_Por_Produtor.this, "Campo Obrigatório! Opção Cancelada", Toast.LENGTH_SHORT).show();

                                } else {
                                    anoDinheiro = Integer.parseInt(inputAno.getText().toString());
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                                    builder.setTitle("Selecione o Mês");

                                    String[] animals = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novenbro", "Dezenbro"};
                                    int checkedItem = 0;
                                    builder.setSingleChoiceItems(animals, checkedItem, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mesDinheiro = which + 1;
                                        }
                                    });


                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                                            builder.setTitle("Forma de Pagamento");
                                            builder.setMessage("Selecione a forma de Pagamento:");
                                            builder.setPositiveButton("Porcentagem", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    AlertDialog.Builder builderDinheiro = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                                                    builderDinheiro.setTitle("Digite o valor da Porcentagem!");
                                                    //builder.setMessage("Valor do litro:  Total da Produção");
                                                    final EditText inputPorcentagem = new EditText(Listview_Producao_Por_Produtor.this);
                                                    inputPorcentagem.setInputType(InputType.TYPE_CLASS_NUMBER);
                                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                                            LinearLayout.LayoutParams.MATCH_PARENT);
                                                    inputPorcentagem.setLayoutParams(lp);
                                                    builderDinheiro.setView(inputPorcentagem);

                                                    builderDinheiro.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface arg0, int arg1) {

                                                            if (inputPorcentagem.getText().toString().isEmpty()) {
                                                                Toast.makeText(Listview_Producao_Por_Produtor.this, "Campo Obrigatório! Opção Cancelada", Toast.LENGTH_SHORT).show();

                                                            } else {
                                                                int valorPorcentagem = Integer.parseInt(inputPorcentagem.getText().toString());

                                                                        Producao p;
                                                                        totalProducao = 0;
                                                                        if(mesDinheiro == 0){
                                                                            mesDinheiro = 1;
                                                                        }
                                                                        float auxValor = 0 , total = 0;
                                                                        Dao bd = new Dao(Listview_Producao_Por_Produtor.this);
                                                                        List<ValorPorLitro> valorPorLitrosMensal = bd.selecionarValorProLitro();
                                                                        List<Producao> producaoList = bd.selecionarProducao();
                                                                        List<Sicronizacao> sicronizacaoList = bd.selecionarSicronizacao();
                                                                        if(sicronizacaoList.size() != 0){
                                                                            for(int i = 0; i < producaoList.size(); i++){
                                                                                if(selecionaAno(producaoList.get(i).getData()) == anoDinheiro){
                                                                                    if(selecionaMes(producaoList.get(i).getData()) == mesDinheiro){
                                                                                        p=producaoList.get(i);
                                                                                        totalProducao = totalProducao + p.getQuant();
                                                                                    }
                                                                                }

                                                                            }
                                                                        }else{
                                                                            for(int i = 0; i < producaoList.size(); i++){
                                                                                if(producaoList.get(i).getIdProdutor() == ListviewProdutorParaProducao.produtor.getId()){
                                                                                    if(selecionaAno(producaoList.get(i).getData()) == anoDinheiro){
                                                                                        if(selecionaMes(producaoList.get(i).getData()) == mesDinheiro){
                                                                                            p=producaoList.get(i);
                                                                                            totalProducao = totalProducao + p.getQuant();
                                                                                        }
                                                                                    }

                                                                                }

                                                                            }
                                                                        }


                                                                        for(int i = 0; i < valorPorLitrosMensal.size(); i++){
                                                                            if (mesDinheiro == valorPorLitrosMensal.get(i).getMes() && anoDinheiro == valorPorLitrosMensal.get(i).getAno()){

                                                                                auxValor = valorPorLitrosMensal.get(i).getValor();


                                                                            }
                                                                        }

                                                                        total = auxValor*totalProducao;
                                                                        float totalPagar = 0;
                                                                        totalPagar = ((total*valorPorcentagem)/100);

                                                                        if(auxValor == 0){
                                                                            AlertDialog.Builder builder = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                                                                            builder.setTitle("Importante!");
                                                                            builder.setMessage("Mês Referente não possui valor do litro Cadastrado! \nRetorne a tela inicial para afetual o cadastro.");
                                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(DialogInterface dialog, int which) {
                                                                                    Toast.makeText(Listview_Producao_Por_Produtor.this, "", Toast.LENGTH_SHORT);
                                                                                }
                                                                            });
                                                                            builder.show();
                                                                        }else{
                                                                            AlertDialog.Builder builder = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                                                                            builder.setTitle("Valor Total Do mes: "+mes[mesDinheiro]);
                                                                            builder.setMessage("Valor do litro: "+auxValor+" \n Total da Produção:"+totalProducao+"\n Valor a Receber:"+total+"\n Total a Pagar: "+totalPagar);
                                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(DialogInterface dialog, int which) {
                                                                                    Toast.makeText(Listview_Producao_Por_Produtor.this, "", Toast.LENGTH_SHORT);
                                                                                }
                                                                            });
                                                                            builder.show();
                                                                        }

                                                                    }




                                                        }
                                                    });
                                                    builderDinheiro.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface arg0, int arg1) {
                                                            Toast.makeText(Listview_Producao_Por_Produtor.this, "Opção Cancelada", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });


                                                    builderDinheiro.show();


                                                }
                                            });
                                            //define um botão como negativo.
                                            builder.setNegativeButton("Valor por litro", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    AlertDialog.Builder builderDinheiro = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                                                    builderDinheiro.setTitle("Digite o valor por litro!");
                                                    //builder.setMessage("Valor do litro:  Total da Produção");
                                                    final EditText inputValor = new EditText(Listview_Producao_Por_Produtor.this);
                                                    inputValor.addTextChangedListener(MaskEditUtil.mask(inputValor, MaskEditUtil.FORMAT_VALOR));
                                                    inputValor.setInputType(InputType.TYPE_CLASS_NUMBER);
                                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                                            LinearLayout.LayoutParams.MATCH_PARENT);
                                                    inputValor.setLayoutParams(lp);
                                                    builderDinheiro.setView(inputValor);

                                                    builderDinheiro.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface arg0, int arg1) {

                                                            if (inputValor.getText().toString().isEmpty()) {
                                                                Toast.makeText(Listview_Producao_Por_Produtor.this, "Campo Obrigatório! Opção Cancelada", Toast.LENGTH_SHORT).show();

                                                            } else {
                                                                float valorPorLitro = Float.parseFloat(inputValor.getText().toString());

                                                                Producao p;
                                                                totalProducao = 0;
                                                                if(mesDinheiro == 0){
                                                                    mesDinheiro = 1;
                                                                }
                                                                float auxValor = 0 , total = 0;
                                                                Dao bd = new Dao(Listview_Producao_Por_Produtor.this);
                                                                List<ValorPorLitro> valorPorLitrosMensal = bd.selecionarValorProLitro();
                                                                List<Producao> producaoList = bd.selecionarProducao();
                                                                List<Sicronizacao> sicronizacaoList = bd.selecionarSicronizacao();
                                                                if(sicronizacaoList.size() != 0){
                                                                    for(int i = 0; i < producaoList.size(); i++){
                                                                        if(selecionaAno(producaoList.get(i).getData()) == anoDinheiro){
                                                                            if(selecionaMes(producaoList.get(i).getData()) == mesDinheiro){
                                                                                p=producaoList.get(i);
                                                                                totalProducao = totalProducao + p.getQuant();
                                                                            }
                                                                        }

                                                                    }
                                                                }else{
                                                                    for(int i = 0; i < producaoList.size(); i++){
                                                                        if(producaoList.get(i).getIdProdutor() == ListviewProdutorParaProducao.produtor.getId()){
                                                                            if(selecionaAno(producaoList.get(i).getData()) == anoDinheiro){
                                                                                if(selecionaMes(producaoList.get(i).getData()) == mesDinheiro){
                                                                                    p=producaoList.get(i);
                                                                                    totalProducao = totalProducao + p.getQuant();
                                                                                }
                                                                            }

                                                                        }

                                                                    }
                                                                }


                                                                for(int i = 0; i < valorPorLitrosMensal.size(); i++){
                                                                    if (mesDinheiro == valorPorLitrosMensal.get(i).getMes() && anoDinheiro == valorPorLitrosMensal.get(i).getAno()){

                                                                        auxValor = valorPorLitrosMensal.get(i).getValor();

                                                                    }
                                                                }

                                                                total = auxValor*totalProducao;
                                                                float totalPagar = 0;
                                                                totalPagar = ((totalProducao*valorPorLitro));

                                                                if(auxValor == 0){
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                                                                    builder.setTitle("Importante!");
                                                                    builder.setMessage("Mês Referente não possui valor do litro Cadastrado! \nRetorne a tela inicial para afetual o cadastro.");
                                                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            Toast.makeText(Listview_Producao_Por_Produtor.this, "", Toast.LENGTH_SHORT);
                                                                        }
                                                                    });
                                                                    builder.show();
                                                                }else{
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                                                                    builder.setTitle("Valor Total Do mes: "+mes[mesDinheiro]);
                                                                    builder.setMessage("Valor do litro: "+auxValor+" \n Total da Produção:"+totalProducao+"\n Valor a Receber:"+total+"\n Total a Pagar: "+totalPagar);
                                                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            Toast.makeText(Listview_Producao_Por_Produtor.this, "", Toast.LENGTH_SHORT);
                                                                        }
                                                                    });
                                                                    builder.show();
                                                                }

                                                            }




                                                        }
                                                    });
                                                    builderDinheiro.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface arg0, int arg1) {
                                                            Toast.makeText(Listview_Producao_Por_Produtor.this, "Opção Cancelada", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });


                                                    builderDinheiro.show();


                                                }
                                            });
                                            builder.show();



                                        }
                                    });
                                    builder.setNegativeButton("Cancel", null);

                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                }

                            }
                        });
                        builderDinheiro.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(Listview_Producao_Por_Produtor.this, "Opção Cancelada", Toast.LENGTH_SHORT).show();
                            }
                        });


                        builderDinheiro.show();




                    }
                });


                builderInicial.show();



                break;
            case R.id.idGrafico:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Gerar Gráfico");
                builder.setMessage("Selecione a opção desejada:");
                builder.setPositiveButton("Produção Anual", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                        builder.setTitle("Digite o ano desejado!");
                        //builder.setMessage("Valor do litro:  Total da Produção");
                        final EditText inputAno = new EditText(Listview_Producao_Por_Produtor.this);
                        inputAno.addTextChangedListener(MaskEditUtil.mask(inputAno, MaskEditUtil.FORMAT_ANO));
                        inputAno.setInputType(InputType.TYPE_CLASS_NUMBER);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        inputAno.setLayoutParams(lp);
                        builder.setView(inputAno);

                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                if(inputAno.getText().toString().isEmpty()){
                                    Toast.makeText(Listview_Producao_Por_Produtor.this, "Campo Obrigatório! Opção Cancelada", Toast.LENGTH_SHORT).show();


                                }else{
                                    anoGrafico = Integer.parseInt(inputAno.getText().toString());
                                    startActivity(new Intent(Listview_Producao_Por_Produtor.this, Grafico_Anual_Producao.class));


                                }

                            }
                        });
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(Listview_Producao_Por_Produtor.this, "Opção Cancelada", Toast.LENGTH_SHORT).show();
                            }
                        });


                        builder.show();

                    }
                });
                //define um botão como negativo.
                builder.setNegativeButton("Produção Mesal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                        builder.setTitle("Digite o ano desejado!");
                        //builder.setMessage("Valor do litro:  Total da Produção");
                        final EditText inputAno = new EditText(Listview_Producao_Por_Produtor.this);
                        inputAno.addTextChangedListener(MaskEditUtil.mask(inputAno, MaskEditUtil.FORMAT_ANO));
                        inputAno.setInputType(InputType.TYPE_CLASS_NUMBER);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        inputAno.setLayoutParams(lp);
                        builder.setView(inputAno);

                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                if(inputAno.getText().toString().isEmpty()){
                                    Toast.makeText(Listview_Producao_Por_Produtor.this, "Campo Obrigatório! Opção Cancelada", Toast.LENGTH_SHORT).show();

                                }else{
                                    anoGrafico = Integer.parseInt(inputAno.getText().toString());
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Listview_Producao_Por_Produtor.this);
                                    builder.setTitle("Selecione o Mês");

                                    String[] animals = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novenbro","Dezenbro"};
                                    int checkedItem = 0;
                                    builder.setSingleChoiceItems(animals, checkedItem, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mesGrafico = which +1;
                                        }
                                    });


                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            startActivity(new Intent(Listview_Producao_Por_Produtor.this, Grafico_Mensal_Producao.class));


                                        }
                                    });
                                    builder.setNegativeButton("Cancel", null);

                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                }

                            }
                        });
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(Listview_Producao_Por_Produtor.this, "Opção Cancelada", Toast.LENGTH_SHORT).show();
                            }
                        });


                        builder.show();

                    }
                });
                builder.show();


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
