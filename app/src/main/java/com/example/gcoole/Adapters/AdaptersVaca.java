package com.example.gcoole.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptersVaca  extends BaseAdapter {
    private final Context context;
    private List<Vaca> vacas = new ArrayList<>();


    public AdaptersVaca(Context context, List<Vaca> vaca){
        this.context = context;
        this.vacas = vaca;
    }


    @Override
    public int getCount() {
        return vacas.size();
    }

    @Override
    public Object getItem(int position) {
        return vacas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Vaca vacass = this.vacas.get(position);
        View vi;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            vi = layoutInflater.inflate(R.layout.activity_adapter_vaca, null);

        }else {
            vi = view;
        }

        Log.e("Erro", "Entrei");
        TextView textViewNome = (TextView) vi.findViewById(R.id.idNomeAdapterVaca);
        TextView textViewNumVaca = (TextView) vi.findViewById(R.id.idNumeroAdapterVaca);
        textViewNome.setText(""+vacass.getNome());
        textViewNumVaca.setText("Nº:"+vacass.getNumVaca());


        return vi;
    }


}
