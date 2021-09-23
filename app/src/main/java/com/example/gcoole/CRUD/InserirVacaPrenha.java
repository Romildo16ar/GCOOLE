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
import com.example.gcoole.Listviews.ListviewVacaPrenha;
import com.example.gcoole.Listviews.Listview_Valor_Por_Litro;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.Modelo.VacaPrenha;
import com.example.gcoole.R;
import com.example.gcoole.Ultil.MaskEditUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class InserirVacaPrenha extends AppCompatActivity implements View.OnClickListener {

    private EditText dataInicioGestacao;
    private EditText numeroGestacao;
    private Spinner spinnerVaca;
    private Vaca[]vaca;
    private String[]vacaNome;
    private ArrayAdapter<String> arrayAdapterVaca;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_inserir_vaca_prenha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        spinnerVaca = (Spinner) findViewById(R.id.idSpinnerInserirvacaPrenha);
        Dao bd = new Dao(this);

        List<Vaca> vacaList = bd.selecionarVaca();

        vaca = new Vaca[vacaList.size()];
        vacaNome = new String[vacaList.size()];

        for(int i = 0; i < vacaList.size(); i++){
            vaca[i] = vacaList.get(i);
            vacaNome[i] = vacaList.get(i).getNome();
        }

        arrayAdapterVaca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vacaNome);
        spinnerVaca.setAdapter(arrayAdapterVaca);

        dataInicioGestacao = (EditText) findViewById(R.id.idInserirdataInicilGestacao);
        numeroGestacao = (EditText) findViewById(R.id.idInserirNumerogestacao);

        dataInicioGestacao.addTextChangedListener(MaskEditUtil.mask(dataInicioGestacao, MaskEditUtil.FORMAT_DATE));

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formatteDate = df.format(c.getTime());
        dataInicioGestacao.setText(formatteDate);

        Button btInserir = (Button) findViewById(R.id.idInserirVacaPrenha);
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
                startActivity(new Intent(InserirVacaPrenha.this, ListviewVacaPrenha.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Dao bd = new Dao(this);
        VacaPrenha vacaPrenha = new VacaPrenha();
        int p = spinnerVaca.getSelectedItemPosition();
        int vac;
        if (vaca.length == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Importante");
            builder.setMessage("Por favor selecione a Vaca.\n Caso a lista esteja vazia, faça o cadastro antes de continuar. ");
            builder.setPositiveButton("OK", (dialog, which) -> {
                Toast.makeText(InserirVacaPrenha.this, "", Toast.LENGTH_SHORT);
            });
            builder.show();
            return;

        }else{
            vac = vaca[p].getId();

        }
        if(numeroGestacao.getText().toString().isEmpty()){
            numeroGestacao.setError("Preencha esse campo!");
            numeroGestacao.requestFocus();
        }else if(!dataInicioGestacao.getText().toString().isEmpty() && !validaData(dataInicioGestacao.getText().toString())){
            dataInicioGestacao.setError("Data Inválida!");
            dataInicioGestacao.requestFocus();
        }else if(varificarVacaPrenha(Integer.parseInt(numeroGestacao.getText().toString()),vac)){
            numeroGestacao.setError("Vaca Já Possui esse número de gestação!");
        }else{

            vacaPrenha.setDataInicialGestacao(dataInicioGestacao.getText().toString());
            vacaPrenha.setNumeroGestacao(Integer.parseInt(numeroGestacao.getText().toString()));
            vacaPrenha.setIdVaca(vac);

            try {
                bd.inserirVacaPrenha(vacaPrenha);
            }catch (Exception e){
                Log.e("Erro", "Erro ao Cadastrar");
            }

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
            builder.setTitle("Produção salva com Sucesso!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(InserirVacaPrenha.this,  "", Toast.LENGTH_SHORT);
                    numeroGestacao.setText("");
                    numeroGestacao.requestFocus();

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
            dataInicioGestacao.setError("Data Inválida!");
            dataInicioGestacao.requestFocus();
        }
        return false;
    }

    private boolean varificarVacaPrenha(int numeroGestacao, int idVaca){
        Dao bd = new Dao(this);
        List<VacaPrenha> vacaPrenhas = bd.selecionarVacaPrenha();
        for(int i =0; i < vacaPrenhas.size(); i++){
            if(numeroGestacao == vacaPrenhas.get(i).getNumeroGestacao() && idVaca == vacaPrenhas.get(i).getIdVaca()){
                return true;
            }
        }
        return false;
    }

}
