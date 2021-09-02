package com.example.gcoole.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gcoole.Modelo.Vaca;
import com.example.gcoole.Modelo.ValorPorLitro;
import com.example.gcoole.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterValorPorLitro extends BaseAdapter {

    private final Context context;
    private List<ValorPorLitro> valorPorLitros = new ArrayList<>();


    public AdapterValorPorLitro(Context context, List<ValorPorLitro> valorPorLitro){
        this.context = context;
        this.valorPorLitros = valorPorLitro;
    }


    @Override
    public int getCount() {
        return valorPorLitros.size();
    }

    @Override
    public Object getItem(int position) {
        return valorPorLitros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ValorPorLitro valorPorLitross = this.valorPorLitros.get(position);
        View vi;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            vi = layoutInflater.inflate(R.layout.activity_adapter_valor_por_litro, null);

        }else {
            vi = view;
        }

        //Log.e("Erro", "Entrei");
        TextView textViewNome = (TextView) vi.findViewById(R.id.idvalorPorLitroAdapter);
        TextView textViewNumVaca = (TextView) vi.findViewById(R.id.idMesEAnoAdapter);
        textViewNome.setText("Valor por Litro: "+valorPorLitross.getValor());
        textViewNumVaca.setText("Mes e ano Referênte: "+valorPorLitross.getMes()+"/" +valorPorLitross.getAno());


        return vi;
    }

}
