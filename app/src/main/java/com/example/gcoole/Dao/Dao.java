package com.example.gcoole.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.Vaca;

import java.util.ArrayList;
import java.util.List;

public class Dao extends SQLiteOpenHelper {
    private static final String NOME_BD = "Bando do Aplicativo";
    private static final int VERSAO_BD = 1016;

    public Dao(Context context){
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbProdutor = "CREATE TABLE produtor(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR(50), tipo INTEGER, "+
                "numProd INTEGER)";
        String tbVaca = "CREATE TABLE vaca(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR(50), numVaca INTEGER)";

        String tbProducao = "CREATE TABLE producao(id INTEGER PRIMARY KEY AUTOINCREMENT, quant INTEGER, data VARCHAR(20), idProdutor INTEGER)";

        db.execSQL(tbProducao);
        db.execSQL(tbProdutor);
        db.execSQL(tbVaca);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTbProdutor = "DROP TABLE IF EXISTS produtor";
        String dropTbVaca = "DROP TABLE IF EXISTS vaca";
        String dropTbProducao = "DROP TABLE IF EXISTS producao";



        db.execSQL(dropTbProdutor);
        db.execSQL(dropTbVaca);
        db.execSQL(dropTbProducao);
        onCreate(db);


    }

// Crud Produtor----------------------------------------------------------------------------------------------------------------------------------
    public void insertProdutor(Produtor produtor){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues prod = new ContentValues();
        prod.put("nome", produtor.getNome());
        prod.put("tipo", produtor.getTipo());
        prod.put("numProd", produtor.getNumProd());


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

                listaProducao.add(producao);
            }while (cur.moveToNext());
        }
        db.close();
        return listaProducao;

    }




// Fim Inserir Produção ----------------------------------------------------------------------------------------------------

}
