package com.example.app_votacao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

// ESSE É O ADAPTER DE CANDIDATOS, USAMOS ELE PARA Q ELE CRIE A LISTA(LISTVIEW) DE CANDIDATOS NA TELA DE CANDIDATOS
public class CandidatoAdapter extends ArrayAdapter <Candidato>{
    private int layout;
    public CandidatoAdapter(@NonNull Context context, int resource, @NonNull List<Candidato> objects) {
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
        // ESSA É A PARTE ONDE RECEBEMOS DO FRONT ITEM_CANDIDATO SEUS ATRIBUTOS PARA Q A GENTE CONSIGA PREENCHER
        // ELES COM OS VALORES VINDO DO BACKEND
        TextView candidatoid = convertView.findViewById(R.id.candidatoid);
        TextView candidatonumero = convertView.findViewById(R.id.candidatonumero);
        TextView candidatonome = convertView.findViewById(R.id.candidatonome);

        candidatoid.setText(""+getItem(position).getId());
        candidatonumero.setText(""+getItem(position).getNumero());
        candidatonome.setText(getItem(position).getNome());

        return convertView;
    }
}
