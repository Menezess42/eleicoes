package com.example.app_votacao;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

// ELEIÇÃO ADAPTER FAZ A MESMA COISA QUE O ADAPTER DE CANDIDATOS
public class    EleicaoAdapter extends ArrayAdapter <Eleicao>{
    private int layout;
    public EleicaoAdapter(@NonNull Context context, int resource, @NonNull List<Eleicao> objects) {
        super(context, resource, objects);
        layout=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.layout, parent, false);
        }
        TextView eleicaoid = convertView.findViewById(R.id.eleicaoid);
        TextView eleicaoano = convertView.findViewById(R.id.eleicaoano);
        TextView eleicaotipo = convertView.findViewById(R.id.eleicaotipo);

        eleicaoid.setText(""+getItem(position).getId());
        eleicaoano.setText(""+getItem(position).getAno());
        eleicaotipo.setText(getItem(position).getTipo());

        return convertView;
    }
}
