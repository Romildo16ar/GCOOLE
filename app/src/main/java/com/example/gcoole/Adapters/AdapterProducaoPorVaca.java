package com.example.gcoole.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gcoole.Modelo.Producao;
import com.example.gcoole.Modelo.ProducaoPorVaca;
import com.example.gcoole.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterProducaoPorVaca extends BaseAdapter {
    private final Context context;
    private List<ProducaoPorVaca> producaoPorVacaList = new ArrayList<>();


    public AdapterProducaoPorVaca(Context context , List<ProducaoPorVaca> producao ) {
        this.context = context;
        this.producaoPorVacaList = producao;

    }



    @Override
    public int getCount() {
        return producaoPorVacaList.size();
    }

    @Override
    public Object getItem(int position) {
        return producaoPorVacaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ProducaoPorVaca producoes = this.producaoPorVacaList.get(position);
        View vi;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            vi = layoutInflater.inflate(R.layout.activity_adapter_producao_por_vaca, null);

        }else {
            vi = view;
        }





        TextView textViewQuant = (TextView) vi.findViewById(R.id.idQuantAdapterVaca);
        TextView textViewData = (TextView) vi.findViewById(R.id.idDataProducaoAdapterVaca);

        textViewQuant.setText("Quantidade total de Leite no dia: "+producoes.getQuant());
        textViewData.setText("Data: "+producoes.getData());



        return vi;
    }
}
