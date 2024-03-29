package com.example.gcoole.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.ProducaoPorVaca;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.Sicronizacao;
import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.Modelo.VacaPrenha;
import com.example.gcoole.Modelo.ValorPorLitro;

import java.util.ArrayList;
import java.util.List;

public class Dao extends SQLiteOpenHelper {
    private static final String NOME_BD = "Banco do Aplicativo";
    private static final int VERSAO_BD = 1016;

    public Dao(Context context){
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbProdutor = "CREATE TABLE produtor(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR(50), tipo INTEGER, "+
                "numProd INTEGER, codigoSicronizacao VARCHAR(50))";
        String tbVaca = "CREATE TABLE vaca(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR(50), numVaca INTEGER)";

        String tbProducao = "CREATE TABLE producao(id INTEGER PRIMARY KEY AUTOINCREMENT, quant INTEGER, data VARCHAR(20), idProdutor INTEGER, idOnline VARCHAR(40))";

        String tbValorPorLitro = "CREATE TABLE valorporlitro(id INTEGER PRIMARY KEY AUTOINCREMENT, valor FLOAT, mes INTEGER , ano INTEGER, idOnline VARCHAR(40))";

        String tbInserirVacaPrenha = "CREATE TABLE vacaPrenha(id INTEGER PRIMARY KEY AUTOINCREMENT, dataInicialGestacao VARCHAR(20), numeroGestacao INTEGER, idVaca INTEGER)";

        String tbSicronizacao = "CREATE TABLE sicronizacao(id INTEGER PRIMARY KEY AUTOINCREMENT, codigo VARCHAR(40), flag INTEGER)";

        String tbProducaoPorVaca = "CREATE TABLE producaoporvaca(id INTEGER PRIMARY KEY AUTOINCREMENT, quant INTEGER, data VARCHAR(20), idVaca INTEGER)";

        db.execSQL(tbProducao);
        db.execSQL(tbProdutor);
        db.execSQL(tbVaca);
        db.execSQL(tbValorPorLitro);
        db.execSQL(tbInserirVacaPrenha);
        db.execSQL(tbSicronizacao);
        db.execSQL(tbProducaoPorVaca);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTbProdutor = "DROP TABLE IF EXISTS produtor";
        String dropTbVaca = "DROP TABLE IF EXISTS vaca";
        String dropTbProducao = "DROP TABLE IF EXISTS producao";
        String dropTbValorPorLitro = "DROP TABLE IF EXISTS valorporlitro";
        String dropTbInserirVacaPrenha = "DROP TABLE IF EXISTS vacaPrenha";
        String dropTbSicronizacao = "DROP TABLE IF EXISTS sicronizacao";
        String dropTbProducaoPorVaca = "DROP TABLE IF EXISTS producaoporvaca";


        db.execSQL(dropTbProdutor);
        db.execSQL(dropTbVaca);
        db.execSQL(dropTbProducao);
        db.execSQL(dropTbValorPorLitro);
        db.execSQL(dropTbInserirVacaPrenha);
        db.execSQL(dropTbSicronizacao);
        db.execSQL(dropTbProducaoPorVaca);
        onCreate(db);


    }
// Insert e Select Sicronização ------------------------------------------------------------------------------------------------------------
    public void insertSicronizacao(Sicronizacao sicronizacao){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues sicro = new ContentValues();
        sicro.put("codigo", sicronizacao.getCodigo());
        sicro.put("flag", sicronizacao.getFlag());

        db.insert("sicronizacao", null, sicro);
        db.close();

    }

    public List<Sicronizacao> selecionarSicronizacao(){
        List<Sicronizacao> listaSicro = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM sicronizacao";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()){
            do{
                Sicronizacao sicro = new Sicronizacao();
                sicro.setId(cur.getInt(0));
                sicro.setCodigo(cur.getString(1));
                sicro.setFlag(cur.getInt(2));

                listaSicro.add(sicro);

            }while (cur.moveToNext());

        }
        db.close();
        return listaSicro;

    }

// Fim Sicronização------------------------------------------------------------------------------------------------------------------------------

// Crud Produtor----------------------------------------------------------------------------------------------------------------------------------
    public void insertProdutor(Produtor produtor){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues prod = new ContentValues();
        prod.put("nome", produtor.getNome());
        prod.put("tipo", produtor.getTipo());
        prod.put("numProd", produtor.getNumProd());
        prod.put("codigoSicronizacao", produtor.getCodigoSocronizacao());


        db.insert("produtor", null, prod);
        db.close();

    }

    public void updateProdutor(Produtor produtor){
        SQLiteDatabase db = getReadableDatabase();
        String where = "id='"+produtor.getId()+"'";
        ContentValues prod = new ContentValues();
        prod.put("nome", produtor.getNome());
        prod.put("tipo", produtor.getTipo());
        prod.put("numProd", produtor.getNumProd());
        prod.put("codigoSicronizacao", produtor.getCodigoSocronizacao());

        db.update("produtor",prod,where,null);
        db.close();

    }

    public void deleteProdutor(int id){
        String delete = "id ='" + id +"'";
        SQLiteDatabase bd = getReadableDatabase();
        bd.delete("produtor", delete, null);
        bd.close();
    }


    public List<Produtor> selecionarProdutor(){
        List<Produtor> listaProd = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM produtor";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()){
            do{
                Produtor prod = new Produtor();
                prod.setId(cur.getInt(0));
                prod.setNome(cur.getString(1));
                prod.setTipo(cur.getInt(2));
                prod.setNumProd(cur.getInt(3));
                prod.setCodigoSocronizacao(cur.getString(4));

                listaProd.add(prod);

            }while (cur.moveToNext());

        }
        db.close();
        return listaProd;

    }



//Fim Crud Produtor--------------------------------------------------------------------------------------------------------------------------

// Incio Crud Vaca ------------------------------------------------------------------------------------------------------------------
    public void insertVaca(Vaca vaca){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vc = new ContentValues();
        vc.put("nome", vaca.getNome());
        vc.put("numVaca", vaca.getNumVaca());

        db.insert("vaca", null, vc);
        db.close();

    }

    public List<Vaca> selecionarVaca(){
        List<Vaca> listaVaca = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM vaca";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()){
            do{
                Vaca vaca = new Vaca();
                vaca.setId(cur.getInt(0));
                vaca.setNome(cur.getString(1));
                vaca.setNumVaca(cur.getInt(2));
                listaVaca.add(vaca);
            }while (cur.moveToNext());
        }
        db.close();
        return listaVaca;

    }

    public void deleteVaca(int id){
        String delete = "id ='" + id +"'";
        SQLiteDatabase bd = getReadableDatabase();
        bd.delete("vaca", delete, null);
        bd.close();
    }

    public void updateVaca(Vaca vaca){
        SQLiteDatabase db = getReadableDatabase();
        String where = "id='"+vaca.getId()+"'";
      //  Log.e("Erro", "Entrei"+vaca.getId());
        ContentValues cv = new ContentValues();
        cv.put("nome", vaca.getNome());
        cv.put("numVaca", vaca.getNumVaca());

        db.update("vaca",cv,where, null);
        db.close();

    }



//Fim do Crud VACA ----------------------------------------------------------------------------------------------------------------
// Incio Crud Inserir Produção ---------------------------------------------------------------------------------------------------

    public void inserirProducao(Producao producao){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vc = new ContentValues();
        vc.put("quant", producao.getQuant());
        vc.put("data", producao.getData());
        vc.put("idProdutor", producao.getIdProdutor());
        vc.put("idOnline", producao.getIdOnline());

        db.insert("producao", null, vc);
        db.close();
    }

    public List<Producao> selecionarProducao(){
        List<Producao> listaProducao = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM producao";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()){
            do{
                Producao producao = new Producao();
                producao.setId(cur.getInt(0));
                producao.setQuant(cur.getInt(1));
                producao.setData(cur.getString(2));
                producao.setIdProdutor(cur.getInt(3));
                producao.setIdOnline(cur.getString(4));

                listaProducao.add(producao);
            }while (cur.moveToNext());
        }
        db.close();
        return listaProducao;

    }

    public void deleteProducao(int id){
        String delete = "id ='" + id +"'";
        SQLiteDatabase bd = getReadableDatabase();
        bd.delete("producao", delete, null);
        bd.close();
    }


    public void updateProducao(Producao producao){
        SQLiteDatabase db = getReadableDatabase();
        String where = "id='"+producao.getId()+"'";
        //  Log.e("Erro", "Entrei"+vaca.getId());
        ContentValues cv = new ContentValues();
        cv.put("quant", producao.getQuant());
        cv.put("data", producao.getData());
        cv.put("idProdutor", producao.getIdProdutor());
        cv.put("idOnline", producao.getIdOnline());

        db.update("producao",cv,where, null);
        db.close();

    }



// Fim Inserir Produção ----------------------------------------------------------------------------------------------------
    // Incio Crud Inserir ProduçãoPorVaca ---------------------------------------------------------------------------------------------------

    public void inserirProducaoPorVaca(ProducaoPorVaca producao){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vc = new ContentValues();
        vc.put("quant", producao.getQuant());
        vc.put("data", producao.getData());
        vc.put("idVaca", producao.getIdVaca());


        db.insert("producaoporvaca", null, vc);
        db.close();
    }

    public List<ProducaoPorVaca> selecionarProducaoPorvaca(){
        List<ProducaoPorVaca> listaProducaoPorVaca = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM producaoporvaca";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()){
            do{
                ProducaoPorVaca producaoPorVaca = new ProducaoPorVaca();
                producaoPorVaca.setId(cur.getInt(0));
                producaoPorVaca.setQuant(cur.getInt(1));
                producaoPorVaca.setData(cur.getString(2));
                producaoPorVaca.setIdVaca(cur.getInt(3));


                listaProducaoPorVaca.add(producaoPorVaca);
            }while (cur.moveToNext());
        }
        db.close();
        return listaProducaoPorVaca;

    }

    public void deleteProducaoPorvava(int id){
        String delete = "id ='" + id +"'";
        SQLiteDatabase bd = getReadableDatabase();
        bd.delete("producaoporvaca", delete, null);
        bd.close();
    }


    public void updateProducaoPorVaca(ProducaoPorVaca producaoPorvaca){
        SQLiteDatabase db = getReadableDatabase();
        String where = "id='"+producaoPorvaca.getId()+"'";
        ContentValues cv = new ContentValues();
        cv.put("quant", producaoPorvaca.getQuant());
        cv.put("data", producaoPorvaca.getData());
        cv.put("idVaca", producaoPorvaca.getIdVaca());

        db.update("producaoporvaca",cv,where, null);
        db.close();

    }



// Fim Inserir Produção ----------------------------------------------------------------------------------------------------
// Incio do crud valor por litro mensal ------------------------------------------------------------------------------------

    public void inserirValorPorLitro(ValorPorLitro valorPorLitro){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vc = new ContentValues();
        vc.put("valor",valorPorLitro.getValor() );
        vc.put("mes", valorPorLitro.getMes());
        vc.put("ano", valorPorLitro.getAno());
        vc.put("idOnline", valorPorLitro.getIdOnline());

        db.insert("valorporlitro", null, vc);
        db.close();
    }



    public void deleteValorPorLitro(int id){
        String delete = "id ='" + id +"'";
        SQLiteDatabase bd = getReadableDatabase();
        bd.delete("valorporlitro", delete, null);
        bd.close();
    }

    public List<ValorPorLitro> selecionarValorProLitro(){
        List<ValorPorLitro> listavalorPorLitro = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM valorporlitro";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()){
            do{
                ValorPorLitro valorPorLitro = new ValorPorLitro();
                valorPorLitro.setId(cur.getInt(0));
                valorPorLitro.setValor(cur.getFloat(1));
                valorPorLitro.setMes(cur.getInt(2));
                valorPorLitro.setAno(cur.getInt(3));
                valorPorLitro.setIdOnline(cur.getString(4));

                listavalorPorLitro.add(valorPorLitro);
            }while (cur.moveToNext());
        }
        db.close();
        return listavalorPorLitro;

    }



    public void updateValorPorLitro(ValorPorLitro valorPorLitro){
        SQLiteDatabase db = getReadableDatabase();
        String where = "id='"+valorPorLitro.getId()+"'";
        //  Log.e("Erro", "Entrei"+vaca.getId());
        ContentValues cv = new ContentValues();
        cv.put("valor", valorPorLitro.getValor());
        cv.put("mes", valorPorLitro.getMes());
        cv.put("ano", valorPorLitro.getAno());

        db.update("valorporlitro",cv,where, null);
        db.close();

    }





// Fim crud valor por litro mensal ------------------------------------------------------------------------------------------
//Inicil Crud VacaPenha -----------------------------------------------------------------------------------------------------

    public void inserirVacaPrenha(VacaPrenha vacaPrenha){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues vc = new ContentValues();
        vc.put("dataInicialGestacao", vacaPrenha.getDataInicialGestacao());
        vc.put("numeroGestacao", vacaPrenha.getNumeroGestacao());
        vc.put("idVaca", vacaPrenha.getIdVaca());
        db.insert("vacaPrenha", null, vc);
        db.close();
    }


    public List<VacaPrenha> selecionarVacaPrenha(){
        List<VacaPrenha> vacaPrenhaList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM vacaPrenha";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()){
            do{
                VacaPrenha vacaPrenha = new VacaPrenha();
                vacaPrenha.setId(cur.getInt(0));
                vacaPrenha.setDataInicialGestacao(cur.getString(1));
                vacaPrenha.setNumeroGestacao(cur.getInt(2));
                vacaPrenha.setIdVaca(cur.getInt(3));
                vacaPrenhaList.add(vacaPrenha);
            }while (cur.moveToNext());
        }
        db.close();
        return vacaPrenhaList;

    }

    public void deleteVacaPrenha(int id){
        String delete = "id ='" + id +"'";
        SQLiteDatabase bd = getReadableDatabase();
        bd.delete("vacaPrenha", delete, null);
        bd.close();
    }

    public void updateVacaPrenha(VacaPrenha vacaPrenha){
        SQLiteDatabase db = getReadableDatabase();
        String where = "id='"+vacaPrenha.getId()+"'";
        //  Log.e("Erro", "Entrei"+vaca.getId());
        ContentValues cv = new ContentValues();
        cv.put("dataInicialGestacao", vacaPrenha.getDataInicialGestacao());
        cv.put("numeroGestacao", vacaPrenha.getNumeroGestacao());
        cv.put("idVaca", vacaPrenha.getIdVaca());

        db.update("vacaPrenha",cv,where, null);
        db.close();

    }




// Fim crud vacaPrenha


}
