package com.example.gcoole.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gcoole.Modelo.Produtor;
import com.example.gcoole.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterProdutor extends BaseAdapter{

    private final Context context;
    private List<Produtor> produtors = new ArrayList<>();

    public AdapterProdutor(Context context, List<Produtor> produtor) {
        this.context = context;
        this.produtors = produtor;
    }


    @Override
    public int getCount() {
        return produtors.size();
    }

    @Override
    public Object getItem(int position) {
        return produtors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Produtor produtores = this.produtors.get(position);
        View vi;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            vi = layoutInflater.inflate(R.layout.activity_adapter_produtor, null);

        }else {
            vi = view;
        }

        if(produtores.getTipo() == -1){
            TextView textViewNome = (TextView) vi.findViewById(R.id.idAdapterNome);
            TextView textViewNumProp = (TextView) vi.findViewById(R.id.idAdapterNumeroProd);
            TextView textViewGestor = (TextView) vi.findViewById(R.id.idAdapterGestorProdutor);
            textViewGestor.setText("");
            textViewNome.setText(""+produtores.getNome());
            textViewNumProp.setText("Nº:"+produtores.getNumProd());


        }else{
            TextView textViewNome = (TextView) vi.findViewById(R.id.idAdapterNome);
            TextView textViewNumProp = (TextView) vi.findViewById(R.id.idAdapterNumeroProd);
            TextView textViewGestor = (TextView) vi.findViewById(R.id.idAdapterGestorProdutor);
            textViewGestor.setText("Gestor");
            textViewNome.setText(""+produtores.getNome());
            textViewNumProp.setText("Nº:"+produtores.getNumProd());

        }






        return vi;
    }
}
