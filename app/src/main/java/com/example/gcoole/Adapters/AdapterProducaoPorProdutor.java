package com.example.gcoole.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gcoole.Listviews.ListviewProducao;
import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterProducaoPorProdutor extends BaseAdapter {
    private final Context context;
    private List<Producao> producaoList = new ArrayList<>();


    public AdapterProducaoPorProdutor(Context context , List<Producao> producao ) {
        this.context = context;
        this.producaoList = producao;

    }



    @Override
    public int getCount() {
        return producaoList.size();
    }

    @Override
    public Object getItem(int position) {
        return producaoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Producao producoes = this.producaoList.get(position);
        View vi;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            vi = layoutInflater.inflate(R.layout.acitivity_adapter_producao_pro_produtor, null);

        }else {
            vi = view;
        }





        TextView textViewQuant = (TextView) vi.findViewById(R.id.idQuantAdapter);
        TextView textViewData = (TextView) vi.findViewById(R.id.idDataProducaoAdapter);

        textViewQuant.setText("Quantidade de Leite: "+producoes.getQuant());
        textViewData.setText("Data: "+producoes.getData());



        return vi;
    }

}
