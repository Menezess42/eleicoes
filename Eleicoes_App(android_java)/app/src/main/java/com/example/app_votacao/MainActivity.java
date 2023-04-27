package com.example.app_votacao;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// AQUI É A TELA PRINCIPAL Q LISTA AS ELEIÇÕES, ELA FAZ BÁSICAMENTE A MESMA COISA DA ACTIVITY DE CANDIDATOS,
// COM DUAS COISINHAS A+ Q VOU COMENTAR.
public class MainActivity extends AppCompatActivity {
    private List<Eleicao> eleicoes = new ArrayList<>();
    private ListView listView;
    private int ele=0;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.eleicaoListVIew);
        chamarWS();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //AQUI QUANDO UMA ELEIÇÃO FOR CLICADA ELA PEGA O ID DESSA ELEIÇÃO E ENVIA PARA A FUNÇÃO
                //QUE CHAMARA A PRÓXIMA TELA(TELA DE CANDIDATOS)
                Eleicao e = (Eleicao) adapterView.getItemAtPosition(i);
                openNextActivity(e.getId()); //FUNÇÃO RESPONSÁVEL POR FAZER A TRANSIÇÃO
            }
        });
    }
    private void chamarWS()
    {
        Call<List<Eleicao>> call = new RetrofitConfig().getCEPService().buscarTodos();
            call.enqueue(new Callback<List<Eleicao>>(){
            @Override
            public void onResponse(Call<List<Eleicao>> call, Response<List<Eleicao>> response) {
                Eleicao ele = new Eleicao();
                eleicoes = response.body();
                listView.setAdapter(new EleicaoAdapter(MainActivity.this, R.layout.item_eleicao,eleicoes));
            }

            @Override
            public void onFailure(Call<List<Eleicao>> call, Throwable t) {
                String nome;
                for (int i=0;i<15;i++)
                {
                    nome = "nome"+i;
                    eleicoes.add(new Eleicao(i,i*100,nome));
                }
            }
        });
    }
    //FUNÇÃO Q CHAMA UMA INTENÇÃO DE TROCAR DE TELA, ALEM DE TROCAR DE TELA NOS PASSAMOS PARA ESSA INTENÇÃO
    // O ID DA ELEIÇÃO CLICADA PARA QUE LÁ NA OUTRA TELA A FUNÇÃO PROCURE VOTOS ASSOCIADOS COM ESSA ELEIÇÃO
    private void openNextActivity(int e)
    {
        Intent intent = new Intent(this, CandidatosActivity2.class);
        intent.putExtra("ele_id",e);
        startActivity(intent);
    }
}