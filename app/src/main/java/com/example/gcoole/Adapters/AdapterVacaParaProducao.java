package com.example.gcoole.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterVacaParaProducao extends BaseAdapter {
    private final Context context;
    private List<Vaca> vacaList = new ArrayList<>();

    public AdapterVacaParaProducao(Context context, List<Vaca> vacaNome) {
        this.context = context;
        this.vacaList = vacaNome;
    }
    @Override
    public int getCount() {
        return vacaList.size();
    }

    @Override
    public Object getItem(int position) {
        return vacaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Vaca vac = this.vacaList.get(position);
        View vi;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            vi = layoutInflater.inflate(R.layout.activity_adapter_vaca_para_producao, null);

        }else {
            vi = view;
        }

            TextView textViewNome = (TextView) vi.findViewById(R.id.idAdapeterNomeVacaParaProducao);
            TextView textViewNumProp = (TextView) vi.findViewById(R.id.idAdapeterNumeroVacaParaProducao);
            textViewNome.setText(""+vac.getNome());
            textViewNumProp.setText("Nº: " +vac.getNumVaca());




        return vi;
    }


}
