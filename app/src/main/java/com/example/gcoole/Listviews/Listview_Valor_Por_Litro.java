package com.example.gcoole.Listviews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Activity_Valor_Por_Litro;
import com.example.gcoole.Adapters.AdapterValorPorLitro;
import com.example.gcoole.CRUD.InserirValorPorLitro;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Grafico.Grafico_Anual_Valor_Por_litro;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Modelo.Sicronizacao;
import com.example.gcoole.Modelo.ValorPorLitro;
import com.example.gcoole.R;
import com.example.gcoole.TelaInicial;
import com.example.gcoole.Ultil.MaskEditUtil;

import java.util.List;

public class Listview_Valor_Por_Litro extends AppCompatActivity {

    private ListView listViewvalorProLitro;
    public static ValorPorLitro valorPorLitro;
    public static int anoGrafico =0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_valor_por_litro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listViewvalorProLitro= (ListView) findViewById(R.id.idListViewValorPorLitro);
        Dao bd = new Dao(this);
        List<ValorPorLitro> listValorPorLitro = bd.selecionarValorProLitro();

        listViewvalorProLitro.setAdapter(new AdapterValorPorLitro(Listview_Valor_Por_Litro.this, listValorPorLitro));

        listViewvalorProLitro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                valorPorLitro = (ValorPorLitro) listViewvalorProLitro.getItemAtPosition(position);
                click(view);
            }


        });

    }

    private void click(View view) {
        startActivity(new Intent(this, Activity_Valor_Por_Litro.class));
    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_listview_valor_por_litro, menu);
        Dao dao = new Dao(this);
        List<Sicronizacao> sicronizacaoList = dao.selecionarSicronizacao();

        if(sicronizacaoList.size() != 0) {
            MenuItem itemADD = menu.findItem(R.id.idaddValor);
            itemADD.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
                break;

            case R.id.idaddValor:
                startActivity(new Intent(this, InserirValorPorLitro.class));
                finishAffinity();
                break;

            case R.id.idGraficoValorPorLitro:
                AlertDialog.Builder builder = new AlertDialog.Builder(Listview_Valor_Por_Litro.this);
                builder.setTitle("Digite o ano desejado!");
                //builder.setMessage("Valor do litro:  Total da Produção");
                final EditText input = new EditText(Listview_Valor_Por_Litro.this);
                input.addTextChangedListener(MaskEditUtil.mask(input, MaskEditUtil.FORMAT_ANO));
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder.setView(input);

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        if(input.getText().toString().isEmpty()){
                            Toast.makeText(Listview_Valor_Por_Litro.this, "Campo Obrigatório! Opção Cancelada", Toast.LENGTH_SHORT).show();

                        }else{
                            anoGrafico = Integer.parseInt(input.getText().toString());
                            startActivity(new Intent(Listview_Valor_Por_Litro.this, Grafico_Anual_Valor_Por_litro.class));
                        }

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(Listview_Valor_Por_Litro.this, "Opção Cancelada", Toast.LENGTH_SHORT).show();
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
