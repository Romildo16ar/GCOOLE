package com.example.gcoole;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gcoole.CRUD.InserirProducao;
import com.example.gcoole.Listviews.ListviewProdutorParaProducao;
import com.example.gcoole.Listviews.ListviewProdutor;
import com.example.gcoole.Listviews.Listview_Valor_Por_Litro;
import com.example.gcoole.Listviews.ListviewsVaca;
import com.example.gcoole.Ultil.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){

            String permissao[] = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            };

            Util.validate(this, 17, permissao);
       // }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ativando as opções do menu
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.listProd:
                startActivity(new Intent(this, ListviewProdutor.class));
                return true;
            case R.id.ListVaca:
                startActivity(new Intent(this, ListviewsVaca.class));

                return true;
            case R.id.idInserirProducao:
                startActivity(new Intent(this, InserirProducao.class));

                return true;
            case R.id.idListProducao:
                startActivity(new Intent(this, ListviewProdutorParaProducao.class));

                return true;
            case R.id.idValorPorLitroMain:
                startActivity(new Intent(this, Listview_Valor_Por_Litro.class));

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permissao = true;
        for(int result: grantResults){
            if(result == PackageManager.PERMISSION_DENIED){
                permissao = false;
                break;
            }
        }

        if(!permissao){
            Toast.makeText(getBaseContext(), "Aceite as permição necessárias para o plicativo funcionar", Toast.LENGTH_SHORT).show();
        }


    }


}