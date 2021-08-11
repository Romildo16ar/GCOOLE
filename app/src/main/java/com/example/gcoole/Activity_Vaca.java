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
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.ListviewProdutor;
import com.example.gcoole.Listviews.ListviewsVaca;

public class Activity_Vaca extends AppCompatActivity {

    private TextView textViewNomeVaca;
    private TextView textViewNumVaca;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_vaca);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        textViewNomeVaca = findViewById(R.id.idActivityNomeVaca);
        textViewNumVaca = findViewById(R.id.idActivityNumeroVaca);

        textViewNomeVaca.setText("Nome: "+ ListviewsVaca.vaca.getNome());
        textViewNumVaca.setText("Nº: "+ ListviewsVaca.vaca.getNumVaca());


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_activity_vaca, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, ListviewsVaca.class));
                finishAffinity();
                break;

            case R.id.idEditarVaca:
                startActivity(new Intent(Activity_Vaca.this, EditarVaca.class));
                finishAffinity();
                break;
            case R.id.idDeletarVaca:
                Dao bd = new Dao(this);
                bd.deleteVaca(ListviewsVaca.vaca.getId());
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Vaca Excluido com Sucesso!");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Activity_Vaca.this, "", Toast.LENGTH_SHORT);
                        startActivity(new Intent(Activity_Vaca.this, ListviewsVaca.class));
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
