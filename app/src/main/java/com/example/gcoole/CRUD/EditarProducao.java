package com.example.gcoole.CRUD;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Activity_Producao;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.Listview_Producao_Por_Produtor;
import com.example.gcoole.Ultil.MaskEditUtil;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.R;

import java.util.List;

public class EditarProducao extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextQuant;
    private EditText editTextData;
    private Spinner spinnerProdutor;
    private ArrayAdapter<String> arrayAdapterProdutor;
    private Produtor[]produtor;
    private String[]produtorNome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_editar_producao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        spinnerProdutor = (Spinner) findViewById(R.id.idEditarSpinnerProdutor);
        editTextQuant = (EditText) findViewById(R.id.idEditarQuant);
        editTextData = (EditText) findViewById(R.id.idEditarDataProducao);

        editTextData.addTextChangedListener(MaskEditUtil.mask(editTextData, MaskEditUtil.FORMAT_DATE));

        Dao bd = new Dao(this);

        List<Produtor> listaProdutor = bd.selecionarProdutor();

        produtor = new Produtor[listaProdutor.size()];
        produtorNome = new String[listaProdutor.size()];
        for (int i = 0; i < listaProdutor.size(); i++) {
            Log.e("Erro", "Id Produtor " +listaProdutor.get(i).getId() + listaProdutor.get(i).getNome());
            produtor[i] = listaProdutor.get(i);
            produtorNome[i] = listaProdutor.get(i).getNome();
        }

        arrayAdapterProdutor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, produtorNome);
        spinnerProdutor.setAdapter(arrayAdapterProdutor);
        String nome = null;
        for (int i = 0; i < listaProdutor.size(); i++) {
            if(Listview_Producao_Por_Produtor.producao.getIdProdutor() == listaProdutor.get(i).getId()){
                nome = listaProdutor.get(i).getNome();
            }
        }
        spinnerProdutor.setSelection(arrayAdapterProdutor.getPosition(nome));

        editTextQuant.setText(String.valueOf(Listview_Producao_Por_Produtor.producao.getQuant()));
        editTextData.setText(Listview_Producao_Por_Produtor.producao.getData());

        Button btSalvar = findViewById(R.id.idBTEditarInserirProducao);
        btSalvar.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(EditarProducao.this, Activity_Producao.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        Producao producao = new Producao();
        Dao bd = new Dao(this);
        int idprodutor = spinnerProdutor.getSelectedItemPosition();
        if (editTextQuant.getText().toString().isEmpty()){
            editTextQuant.setError("Campo Obrigatório");
            editTextQuant.requestFocus();
        }else if(!editTextData.getText().toString().isEmpty() && !validaData(editTextData.getText().toString())){
            editTextData.setError("Data Inválida");
            editTextData.requestFocus();
        }else{

            producao.setId(Listview_Producao_Por_Produtor.producao.getId());
            producao.setQuant(Integer.parseInt(editTextQuant.getText().toString()));
            producao.setData(editTextData.getText().toString());
            producao.setIdProdutor(produtor[idprodutor].getId());

            try {
                bd.updateProducao(producao);
            } catch (Exception e) {
                Log.e("Erro", "Erro ao Cadastrar");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Produção Atualizada com Sucesso!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(EditarProducao.this, "", Toast.LENGTH_SHORT);
                    startActivity(new Intent(EditarProducao.this, Listview_Producao_Por_Produtor.class));
                    finishAffinity();

                }
            });
            builder.show();
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
            editTextData.setError("Data Inválida!");
            editTextData.requestFocus();
        }
        return false;
    }

}
