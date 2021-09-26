package com.example.gcoole;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.CRUD.EditarVaca;
import com.example.gcoole.CRUD.EditarVacaPrenha;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.ListviewVacaPrenha;
import com.example.gcoole.Listviews.ListviewsVaca;
import com.example.gcoole.Modelo.Vaca;

import java.util.List;

public class Activity_Vaca_Prenha  extends AppCompatActivity {

    private TextView textViewNomeVaca;
    private TextView textViewDataInicialGestacao;
    private TextView textViewNumeroGestacao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaca_prenha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        textViewNomeVaca = findViewById(R.id.idActivityNomeVaca);
        textViewNumeroGestacao = findViewById(R.id.idActivityNumeroGestacao);
        textViewDataInicialGestacao = findViewById(R.id.idActivityDataGestacao);
        Dao bd = new Dao(this);
        List<Vaca> vacaList = bd.selecionarVaca();
        String auxNomeVaca = "";
        for(int i =0; i < vacaList.size(); i++){
            if(ListviewVacaPrenha.vacaPrenha.getIdVaca() == vacaList.get(i).getId()){
                auxNomeVaca = vacaList.get(i).getNome();
            }
        }

        textViewNomeVaca.setText("Nome: "+ auxNomeVaca);
        textViewNumeroGestacao.setText("N°: "+ListviewVacaPrenha.vacaPrenha.getNumeroGestacao());
        textViewDataInicialGestacao.setText("Data incial da Gestação: "+ListviewVacaPrenha.vacaPrenha.getDataInicialGestacao());


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_activity_vaca_prenha, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, ListviewVacaPrenha.class));
                finishAffinity();
                break;

            case R.id.idEditarVacaPrenha:
                startActivity(new Intent(Activity_Vaca_Prenha.this, EditarVacaPrenha.class));
                finishAffinity();
                break;
            case R.id.idDeletarVacaPrenha:
                Dao bd = new Dao(this);
                bd.deleteVacaPrenha(ListviewVacaPrenha.vacaPrenha.getId());
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Vaca Prenha Excluida com Sucesso!");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Activity_Vaca_Prenha.this, "", Toast.LENGTH_SHORT);
                        startActivity(new Intent(Activity_Vaca_Prenha.this, ListviewVacaPrenha.class));
                        finishAffinity();
                    }
                });
                builder.show();
                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
