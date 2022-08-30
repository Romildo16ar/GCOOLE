package com.example.gcoole.CRUD;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Dao.Dao;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.ProducaoPorVaca;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.R;
import com.example.gcoole.Ultil.MaskEditUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class InserirProducaoPorVaca extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerVaca;
    private String[]vacaNome;
    private Vaca[]vaca;
    private ArrayAdapter<String> arrayAdapterVaca;
    private EditText quant;
    private EditText dataProducao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_inserir_producao_por_vaca);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        spinnerVaca = (Spinner) findViewById(R.id.idSpinnervaca);
        Dao bd = new Dao(this);

        List<Vaca> listarVaca = bd.selecionarVaca();
        vaca = new Vaca[listarVaca.size()];
        vacaNome = new String[listarVaca.size()];

        for (int i = 0; i< listarVaca.size(); i++){
            vaca[i]=listarVaca.get(i);
            vacaNome[i]= listarVaca.get(i).getNome();
        }

        arrayAdapterVaca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vacaNome);
        spinnerVaca.setAdapter(arrayAdapterVaca);

        quant = (EditText) findViewById(R.id.idQuantPorVaca);
        dataProducao = (EditText) findViewById(R.id.idDataProducaoPorVaca);

        dataProducao.addTextChangedListener(MaskEditUtil.mask(dataProducao, MaskEditUtil.FORMAT_DATE));

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formatteDate = df.format(c.getTime());
        dataProducao.setText(formatteDate);

        Button btInserir = (Button) findViewById(R.id.idBTInserirProducaoPorVaca);

        btInserir.setOnClickListener(this);


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(InserirProducaoPorVaca.this, MainActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Dao bd = new Dao(this);
        ProducaoPorVaca producaoPorVaca = new ProducaoPorVaca();
        int p = spinnerVaca.getSelectedItemPosition();
        int idvaca;

        if (vaca.length == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Importante");
            builder.setMessage("Por favor selecione uma Vaca.\n Caso a lista de Vaca esteja vazia, faça o cadastro antes de continuar. ");
            builder.setPositiveButton("OK", (dialog, which) -> {
                Toast.makeText(InserirProducaoPorVaca.this, "", Toast.LENGTH_SHORT);
            });
            builder.show();
            return;

        }else{
            idvaca = vaca[p].getId();

        }
        if(quant.getText().toString().isEmpty()){
            quant.setError("Preencha esse campo!");
            quant.requestFocus();
        }else if(!dataProducao.getText().toString().isEmpty() && !validaData(dataProducao.getText().toString())){
            dataProducao.setError("Data Inválida!");
            dataProducao.requestFocus();
        }else if(validarProducao(dataProducao.getText().toString(), idvaca)){
            dataProducao.setError("Data Já Possui Produção!");
        }else{
            producaoPorVaca.setQuant(Integer.parseInt(quant.getText().toString()));
            producaoPorVaca.setData(dataProducao.getText().toString());
            Log.e("Erro", "Id Produtor " +idvaca);
            producaoPorVaca.setIdVaca(idvaca);

            try {
                    bd.inserirProducaoPorVaca(producaoPorVaca);
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
                    builder.setTitle("Produção salva com Sucesso!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(InserirProducaoPorVaca.this,  "", Toast.LENGTH_SHORT);
                            quant.setText("");
                            quant.requestFocus();

                        }
                    });
                    builder.show();

            }catch (Exception e){
                Log.e("Erro", "Erro ao Cadastrar");
            }


            return;
        }

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
            dataProducao.setError("Data Inválida!");
            dataProducao.requestFocus();
        }
        return false;
    }

    private boolean validarProducao(String dataNova , int idProdutor){

        String[] vetDataNova;
        vetDataNova=dataNova.split("/");
        Dao bd = new Dao(this);
        List<Producao> producao = bd.selecionarProducao();


        int diaNova = Integer.parseInt(vetDataNova[0]);
        int mesNova = Integer.parseInt(vetDataNova[1]);
        int anoNova = Integer.parseInt(vetDataNova[2]);

        for(int i = 0; i < producao.size(); i++){
            if(idProdutor == producao.get(i).getIdProdutor()){
                String[] vetData;
                vetData=producao.get(i).getData().split("/");
                int dia = Integer.parseInt(vetData[0]);
                int mes = Integer.parseInt(vetData[1]);
                int ano = Integer.parseInt(vetData[2]);
                if(dia == diaNova && mes == mesNova && ano == anoNova){
                    return true;
                }
            }
        }

        return false;
    }
}


