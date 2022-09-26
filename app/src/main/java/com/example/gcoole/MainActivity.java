package com.example.gcoole;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gcoole.Adapters.AdapterMain;
import com.example.gcoole.CRUD.InserirProducao;
import com.example.gcoole.CRUD.InserirProducaoPorVaca;
import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Listviews.ListviewProdutorParaProducao;
import com.example.gcoole.Listviews.ListviewProdutor;
import com.example.gcoole.Listviews.ListviewVacaParaProducao;
import com.example.gcoole.Listviews.ListviewVacaPrenha;
import com.example.gcoole.Listviews.Listview_Valor_Por_Litro;
import com.example.gcoole.Listviews.ListviewsVaca;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.Sicronizacao;
import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.Modelo.VacaPrenha;
import com.example.gcoole.Modelo.ValorPorLitro;
import com.example.gcoole.Ultil.Util;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView vacaPrenhas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

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

        vacaPrenhas = (ListView) findViewById(R.id.idListaMain);

        Dao bd= new Dao(this);
        List<VacaPrenha> vacaPrenhaList = bd.selecionarVacaPrenha();
        List<Vaca> vacaList = bd.selecionarVaca();
        vacaPrenhas.setAdapter(new AdapterMain(MainActivity.this, vacaPrenhaList ,vacaList));
        inicializarFireBase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ativando as opções do menu
        getMenuInflater().inflate(R.menu.main, menu);
        Dao dao = new Dao(this);
        List<Sicronizacao> sicronizacaoList = dao.selecionarSicronizacao();

        if(sicronizacaoList.size() != 0) {
            MenuItem itemListaProdutor = menu.findItem(R.id.listProd);
            MenuItem itemInserirProdução = menu.findItem(R.id.idInserirProducao);
            itemListaProdutor.setVisible(false);
            itemInserirProdução.setVisible(false);
        }else{
            MenuItem atualizar = menu.findItem(R.id.atualizardados);
            atualizar.setVisible(false);
        }
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

            case R.id.idVacaPrenhaMenu:

                startActivity(new Intent(this, ListviewVacaPrenha.class));
                return true;

            case R.id.idMenuInserirProducaoPorVaca:

                startActivity(new Intent(this, InserirProducaoPorVaca.class));
                return true;

            case R.id.idListProducaoPorVaca:

                startActivity(new Intent(this, ListviewVacaParaProducao.class));
                return true;

            case R.id.atualizardados:;

                    Dao dao = new Dao(this);
                    List<Produtor> produtorList = dao.selecionarProdutor();
                    List<Sicronizacao> sicronizacaoList = dao.selecionarSicronizacao();
                    List<ValorPorLitro> valorPorLitroList = dao.selecionarValorProLitro();
                    List<Producao> producaoList = dao.selecionarProducao();

                    if(isOnline()){
                        for (int i = 0; i < valorPorLitroList.size(); i++){
                            dao.deleteValorPorLitro(valorPorLitroList.get(i).getId());
                        }
                        for (int i = 0; i < producaoList.size(); i++){
                            dao.deleteProducao(producaoList.get(i).getId());
                        }
                        for (int i = 0; i < produtorList.size(); i++){
                            dao.deleteProdutor(produtorList.get(i).getId());
                        }

                        databaseReference.child(sicronizacaoList.get(0).getCodigo()).child("Valor_por_litro").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for(DataSnapshot keyNote:snapshot.getChildren()){
                                    Dao bd = new Dao(MainActivity.this);
                                    ValorPorLitro valorPorLitro = keyNote.getValue(ValorPorLitro.class);
                                    assert valorPorLitro != null;
                                    bd.inserirValorPorLitro(valorPorLitro);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        databaseReference.child(sicronizacaoList.get(0).getCodigo()).child("producao").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot keyNote:snapshot.getChildren()){
                                    Dao bd = new Dao(MainActivity.this);
                                    Producao producao = keyNote.getValue(Producao.class);
                                    assert producao != null;
                                    bd.inserirProducao(producao);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        databaseReference.child(sicronizacaoList.get(0).getCodigo()).child("produtor").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot keyNote:snapshot.getChildren()){
                                    Dao bd = new Dao(MainActivity.this);
                                    Produtor produtor = keyNote.getValue(Produtor.class);
                                    assert produtor != null;
                                    bd.insertProdutor(produtor);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        Toast.makeText(getApplicationContext(), "Dados Atualizados!", Toast.LENGTH_SHORT).show();



                    }else{
                        Toast.makeText(getApplicationContext(), "Não foi possivel sicronizar os dados verifique a conexão com a internet!", Toast.LENGTH_SHORT).show();

                    }
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

    public boolean isOnline(){
        try {
            ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Erro ao verificar se estava online", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void inicializarFireBase(){
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


}