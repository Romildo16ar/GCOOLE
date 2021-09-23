package com.example.gcoole.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gcoole.Dao.Dao;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.Modelo.VacaPrenha;
import com.example.gcoole.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterVacaPrenha extends BaseAdapter {
    private final Context context;
    private List<VacaPrenha> vacaPrenhaList = new ArrayList<>();
    private List<Vaca> vacaList = new ArrayList<>();

    public AdapterVacaPrenha(Context context, List<VacaPrenha> vacaPrenhasNome , List<Vaca> vacas) {
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
            vi = layoutInflater.inflate(R.layout.activity_adapter_vaca_prenha, null);

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
        textViewNomeVaca.setText("Nome:"+vacaAux.getNome());
        textViewNumGestacao.setText("Nº da Gestação: " +vaca.getNumeroGestacao());
        textViewNumIndetificacao.setText("Nº de Indetificação da Vaca:"+vacaAux.getNumVaca());



        return vi;
    }

}
