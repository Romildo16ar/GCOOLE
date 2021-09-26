package com.example.gcoole.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.Modelo.VacaPrenha;
import com.example.gcoole.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterMain extends BaseAdapter {

    private final Context context;
    private List<VacaPrenha> vacaPrenhaList = new ArrayList<>();
    private List<Vaca> vacaList = new ArrayList<>();

    public AdapterMain(Context context, List<VacaPrenha> vacaPrenhasNome , List<Vaca> vacas) {
        this.context = context;
        this.vacaPrenhaList = vacaPrenhasNome;
        this.vacaList = vacas;
    }
    @Override
    public int getCount() {
        return vacaPrenhaList.size();
    }

    @Override
    public Object getItem(int position) {
        return vacaPrenhaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        VacaPrenha vaca = this.vacaPrenhaList.get(position);
        View vi;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            vi = layoutInflater.inflate(R.layout.activity_adapter_main, null);

        }else {
            vi = view;
        }
        Vaca vacaAux = new Vaca();
        for(int i = 0; i < vacaList.size(); i++){
            if(vaca.getIdVaca() == vacaList.get(i).getId()){
                vacaAux.setId(vacaList.get(i).getId());
                vacaAux.setNome(vacaList.get(i).getNome());
                vacaAux.setNumVaca(vacaList.get(i).getNumVaca());
            }
        }

        TextView textViewNomeVaca = (TextView) vi.findViewById(R.id.idAdapterNomeVacaPrenha);
        TextView textViewNumGestacao = (TextView) vi.findViewById(R.id.idAdapterNumeroGestacao);
        TextView textViewNumIndetificacao = (TextView) vi.findViewById(R.id.idAdapterNumeroVaca);
        TextView textViewDataSecagem = (TextView) vi.findViewById(R.id.idAdapterDataDaSecagem);
        TextView textViewDataParto = (TextView) vi.findViewById(R.id.idAdapeterDataParto);

        vaca.getDataInicialGestacao();



        textViewNomeVaca.setText("Nome da Vaca:"+vacaAux.getNome());
        textViewNumGestacao.setText("Nº da Gestação: " +vaca.getNumeroGestacao());
        textViewNumIndetificacao.setText("Nº de Indetificação da Vaca: "+vacaAux.getNumVaca());
        textViewDataSecagem.setText("Data Aproximado do Parto: "+dataAproximadoParto(vaca.getDataInicialGestacao()));
        textViewDataParto.setText("Data da Secagem: "+dataSecagem(vaca.getDataInicialGestacao()));


        return vi;
    }

    public String dataAproximadoParto(String data){
        int mesParto = 0;
        int anoParto = 0;
        int diaParto = 0;
        String dataDoParto = "";
        mesParto = selecionaMes(data) + 9;
        diaParto = selecionaDia(data);
        if(mesParto > 12){
            mesParto = mesParto - 12;
            anoParto = selecionaAno(data) +1;
        }else{
            anoParto = selecionaAno(data);
        }
        if(mesParto == 12 || mesParto == 11 || mesParto ==10){
            if(diaParto < 10){
                dataDoParto = "0"+diaParto+"/"+mesParto+"/"+anoParto;
            }else{
                dataDoParto = diaParto+"/"+mesParto+"/"+anoParto;
            }
        }else{
            if(diaParto < 10) {
                dataDoParto = "0"+diaParto + "/0" + mesParto + "/" + anoParto;
            }else{
                dataDoParto = diaParto + "/0" + mesParto + "/" + anoParto;
            }
        }

        return dataDoParto;
    }

    public String dataSecagem(String data){
        int mesParto = 0;
        int anoParto = 0;
        int diaParto = 0;
        String dataDoParto = "";
        mesParto = selecionaMes(data) + 6;
        diaParto = selecionaDia(data);
        if(mesParto > 12){
            mesParto = mesParto - 12;
            anoParto = selecionaAno(data) +1;
        }else{
            anoParto = selecionaAno(data);
        }
        if(mesParto == 12 || mesParto == 11 || mesParto ==10){
            if(diaParto < 10){
                dataDoParto = "0"+diaParto+"/"+mesParto+"/"+anoParto;
            }else{
                dataDoParto = diaParto+"/"+mesParto+"/"+anoParto;
            }
        }else{
            if(diaParto < 10) {
                dataDoParto = "0"+diaParto + "/0" + mesParto + "/" + anoParto;
            }else{
                dataDoParto = diaParto + "/0" + mesParto + "/" + anoParto;
            }
        }

        return dataDoParto;
    }

    public int selecionaMes(String data){
        String []vet;
        vet= data.split("/");
        int mes = Integer.parseInt(vet[1]);
        return mes;
    }
    public int selecionaAno(String data){
        String []vet;
        vet= data.split("/");
        int ano = Integer.parseInt(vet[2]);
        return ano;
    }
    public int selecionaDia(String data){
        String []vet;
        vet= data.split("/");
        int dia = Integer.parseInt(vet[0]);
        return dia;
    }

}
