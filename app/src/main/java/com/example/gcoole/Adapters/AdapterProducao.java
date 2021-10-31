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
import com.example.gcoole.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterProducao extends BaseAdapter {

    private final Context context;
    private List<Produtor> produtorList = new ArrayList<>();

    public AdapterProducao(Context context, List<Produtor> producoesNome) {
        this.context = context;
        this.produtorList = producoesNome;
    }
    @Override
    public int getCount() {
        return produtorList.size();
    }

    @Override
    public Object getItem(int position) {
        return produtorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Produtor produ = this.produtorList.get(position);
        View vi;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            vi = layoutInflater.inflate(R.layout.activity_adapter_producao, null);

        }else {
            vi = view;
        }



        if(produ.getTipo() == -1){
            TextView textViewNome = (TextView) vi.findViewById(R.id.idAdapeterNomeProdutorProducao);
            TextView textViewNumProp = (TextView) vi.findViewById(R.id.idAdapeterNumeroProdutorProducao);
            TextView textViewGestor = (TextView) vi.findViewById(R.id.idAdapterGestorProdutor);
            textViewGestor.setText("");
            textViewNome.setText(""+produ.getNome());
            textViewNumProp.setText("Nº: " +produ.getNumProd());

        }else{
            TextView textViewNome = (TextView) vi.findViewById(R.id.idAdapeterNomeProdutorProducao);
            TextView textViewNumProp = (TextView) vi.findViewById(R.id.idAdapeterNumeroProdutorProducao);
            TextView textViewGestor = (TextView) vi.findViewById(R.id.idAdapterGestorProdutor);
            textViewGestor.setText("Gestor");
            textViewNome.setText(""+produ.getNome());
            textViewNumProp.setText("Nº: " +produ.getNumProd());

        }



        return vi;
    }

}
