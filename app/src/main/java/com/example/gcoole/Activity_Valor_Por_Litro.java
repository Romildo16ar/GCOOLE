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
import com.example.gcoole.CRUD.EditarValorPorLitro;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.Listview_Valor_Por_Litro;
import com.example.gcoole.Listviews.ListviewsVaca;

public class Activity_Valor_Por_Litro extends AppCompatActivity {

    private TextView textViewvalorPorLitro;
    private TextView textVieMesEAno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valor_por_litro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        textViewvalorPorLitro = findViewById(R.id.idValorPorLitroActivity);
        textVieMesEAno = findViewById(R.id.idMesEAnoActivity);

        textViewvalorPorLitro.setText("Valor Por Litro Mensal: "+ Listview_Valor_Por_Litro.valorPorLitro.getValor());
        textVieMesEAno.setText("Mes e Ano Referênte: "+ Listview_Valor_Por_Litro.valorPorLitro.getMes()+"/"+Listview_Valor_Por_Litro.valorPorLitro.getAno());

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_activity_valor_por_litro, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, Listview_Valor_Por_Litro.class));
                finishAffinity();
                break;

            case R.id.idEditarValorPorLitro:
                startActivity(new Intent(Activity_Valor_Por_Litro.this, EditarValorPorLitro.class));
                finishAffinity();
                break;
            case R.id.idDeletarValorProLitro:
                Dao bd = new Dao(this);
                bd.deleteValorPorLitro(Listview_Valor_Por_Litro.valorPorLitro.getId());
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Valor Excluido com Sucesso!");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Activity_Valor_Por_Litro.this, "", Toast.LENGTH_SHORT);
                        startActivity(new Intent(Activity_Valor_Por_Litro.this, Listview_Valor_Por_Litro.class));
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
