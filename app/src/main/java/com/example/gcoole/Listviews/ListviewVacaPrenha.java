package com.example.gcoole.Listviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gcoole.Activity_Produtor;
import com.example.gcoole.Adapters.AdapterProdutor;
import com.example.gcoole.Adapters.AdapterVacaPrenha;
import com.example.gcoole.CRUD.CadastroVaca;
import com.example.gcoole.CRUD.InserirVacaPrenha;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.MainActivity;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.Modelo.VacaPrenha;
import com.example.gcoole.R;

import java.util.List;

public class ListviewVacaPrenha extends AppCompatActivity {
    public static VacaPrenha vacaPrenha;
    private ListView vacaPrenhas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_vaca_prenha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        vacaPrenhas = (ListView) findViewById(R.id.idListViewVacaPrenha);

        Dao bd = new Dao(this);
        List<VacaPrenha> vacaPrenhaList = bd.selecionarVacaPrenha();
        List<Vaca> vacaList = bd.selecionarVaca();
        vacaPrenhas.setAdapter(new AdapterVacaPrenha(ListviewVacaPrenha.this, vacaPrenhaList, vacaList));

        /*vacaPrenhas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vacaPrenha = (VacaPrenha) vacaPrenhas.getItemAtPosition(position);
                click(view);
            }


        });

         */
    }

         /*
    private void click(View view) {
        startActivity(new Intent(this, Activity_Produtor.class));
    }
*/

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_listview_vaca_prenha, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
                break;

            case R.id.idAddVacaPrenha:
                startActivity(new Intent(this, InserirVacaPrenha.class));
                finishAffinity();
                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Dao bd = new Dao(this);
        List<VacaPrenha> vacaPrenhaList = null;
        vacaPrenhaList = bd.selecionarVacaPrenha();
        List<Vaca> vacaList = null;
        vacaList = bd.selecionarVaca();
        vacaPrenhas.setAdapter(new AdapterVacaPrenha(ListviewVacaPrenha.this, vacaPrenhaList, vacaList));
    }
}
