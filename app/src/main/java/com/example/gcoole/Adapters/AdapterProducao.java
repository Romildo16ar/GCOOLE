package com.example.gcoole.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gcoole.Modelo.Producao;

import com.example.gcoole.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterProducao extends BaseAdapter {

    private final Context context;
    private List<Producao> producoes = new ArrayList<>();

    public AdapterProducao(Context context, List<Producao> producao) {
        this.context = context;
        this.producoes = producao;
    }
    @Override
    public int getCount() {
        return producoes.size();
    }

    @Override
    public Object getItem(int position) {
        return producoes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Producao produ = this.producoes.get(position);
        View vi;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            vi = layoutInflater.inflate(R.layout.activity_adapter_producao, null);

        }else {
            vi = view;
        }

        Log.e("Erro", "Entrei");
        TextView textViewNome = (TextView) vi.findViewById(R.id.idAdapeterNomeProdutorProducao);
        TextView textViewNumProp = (TextView) vi.findViewById(R.id.idAdapeterNumeroProdutorProducao);
        textViewNome.setText(""+produ.getIdProdutor());
        textViewNumProp.setText("Nº:");



        return vi;
    }

}
