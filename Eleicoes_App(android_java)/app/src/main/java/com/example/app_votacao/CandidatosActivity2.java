package com.example.app_votacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;
//ESSA É A ACTIVITY DE CANDIDATOS, BASICAMENTE É O CÓDIGO PARA A TELA DE CANDIDATOS FUNCIONAR
public class CandidatosActivity2 extends AppCompatActivity {
    private ListView listView;
    private Button btVoltar;
    private List<Candidato> candidatos = new ArrayList<>();
    private List<Votos> votos = new ArrayList<>();
    private EditText testeResponse; // ESSE TESTE RESPONSE ERA UMA CAIXA DE TEXTO Q EU ESTAVA USANDO PARA EXIBIR
    //                              A RESPONSE Q VINHA DO BANCO
    private int ele_id=0; // ESSE VALOR INTEIRO É PARA RECEBER O ID DE ELEIÇÃO VINDO DA OUTRA TELA
    //                  USAMOS O ID PARA BUSCAR NA TABELA DE VOTOS OS CANDIDATOS ASSOCIADOS A ESSA ELEIÇÃO
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatos2);
        //NESSAS TRÊS LINHAS APENAS ESTAMOS ASSOCIANDO VARIÁVEIS A ATRIBUTOS DA TELA
        listView = findViewById(R.id.candidatoLV);
        btVoltar = findViewById(R.id.btVoltar);

        //ESSA LINHA É O BOTÃO VOLTAR E ELE EXECUTA UMA FUNÇÃO Q FINALIZA ESSA ACTIVITY(TELA)
        btVoltar.setOnClickListener(e->{finish();});
        String nome;
        //testeResponse = findViewById(R.id.testeResponse);
        ele_id=getIntent().getIntExtra("ele_id",0); //AQUI ESTAMOS RECEBENDO O VALOR DA ID
        //                                                              DA ELEIÇÃO CLICADA Q ESTÁ VINDO DA OUTRA
        //                                                               TELA
        chamarWS(ele_id); // AQUI CHAMAMOS A FUNÇÃO Q ALIMENTA O LISTVIEW DE CANDIDATOS

        // NESTA FUNÇÃO EXECUTAMOS UMA FUNÇÃO Q PEGA O CANDIDATO DA POSIÇÃO CLICADO PELO USUÁRIO, CHAMA UMA FUNÇÃO
        // QUE COMPUTA O VOTO E ENVIA PRO BACKEND
        // DEPOIS FECHA A TELA DE CANDIDATOS E EXIBE UM POPUP NA TELA DIZENDO QUE O VOTO FOI COMPUTADO E EXIBE O NOME
        // DO CANDIDATO
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //FUNÇÃO "QUANDO CLICAR" EXECUTE ISSO:
                Candidato c = (Candidato) adapterView.getItemAtPosition(i); //AQUI ELE PEGA O CANDIDATO Q ESTÁ NA
                                                                            //POSIÇÃO CLICADA
                UpdateVotos(c); // CHAMA A FUNÇÃO Q ATUALIZA A CONTAGEM DE VOTOS
                // E POR FIM CHAMA O TOAST Q É O POPUP INFORMANDO Q O VOTO FOI CONTABILIZADO
                Toast.makeText(CandidatosActivity2.this,
                        "Voto contabilizado em: "+c.getNome(),
                        Toast.LENGTH_LONG).show();
                         finish(); // E AQUI NO FINISH ELE TERMINA A TELA;
            }
        });
    }
    //FUNÇAO PARA CONTABILIZAR O VOTO, ELA RECEBE O CANDIDATO, E USA ELE PARA PROCURAR NA LISTA DE VOTOS O ID DO SEU
    // VOTO, CONTABILIZAR O VOTO E ENVIAR TUDO ISSO PRO BACK
    private void UpdateVotos(Candidato c)
    {
        int aux=0;
        Votos v=new Votos(0,0,null);
            for(int i=0;i<votos.size();i++)
            {
                // SE O ID DO CANDIDATO CLICADO FOR IGUAL O ID DO CANDIDATO NA LISTA DE VOTOS ELE PARA A REPETIÇÃO
                // E PEGA A POSIÇÃO DO VOTO NA LISTA
                if(votos.get(i).getCandidato().getId()==c.getId())
                {
                    aux = i;
                    i=votos.size();
                }
            }
            v=votos.get(aux); // AI AQUI ELE PEGA O OBJETO VOTO DA LISTA USANDO A POSIÇÃO
        Log.d("ERROR_LOG_TOTAL:",""+v.getTotal());
            v.setTotal(v.getTotal()+1); // INCREMENTA A CONTAGEM DE VOTOS
        Log.d("ERROR_LOG_TOTAL:",""+v.getTotal());
            Log.d("ERROR_LOG_ID:",""+v.getId());
            Call<Votos> call = new RetrofitConfig().getCEPService().updateVotos(v);//ENVIA PRO BACK O VOTO
        //                                                                                     ATUALIZADO
            call.enqueue(new Callback<Votos>(){
                @Override
                public void onResponse(Call<Votos> call, Response<Votos> response) {
                    //testeResponse.setText("Correto: "+response);
                    Log.d("ERROR_LOG","Correto: "+response);
                }

                @Override
                public void onFailure(Call<Votos> call, Throwable t) {
                    //testeResponse.setText("Errado: "+t.getMessage());
                    Log.d("ERROR_LOG","Falha: "+t.getMessage());
                }
            });
    }

    //ESSA FUNÇAÕ ALIMENTA O LISTVIEW DE CANDIDATOS SEU TRABALHO É:
    // - ENVIAR O ID DE ELEIÇÃO PARA O BACK Q VAI RETORNAR UMA LISTA DE VOTOS E DENTRO DE CADA ITEM VOTO
    //   TEREMOS UM OBJETO CANDIDATO, É ELE QUE QUEREMOS
    private void chamarWS(int ele_id) {
        Call<List<Votos>> call = new RetrofitConfig().getCEPService().buscarTodosCandidatos(ele_id);
        call.enqueue(new Callback<List<Votos>>() {
            @Override
            public void onResponse(Call<List<Votos>> call, Response<List<Votos>> response) {
                Candidato ele = new Candidato();
                votos = response.body(); // ALIMENTAMOS A LIST<VOTOS> COM A LISTA DE VOTOS QUE VEIO DO BACKEND
                Log.d("ERROR_VOTOS_ID: ",""+votos.get(0).getId());

                // NESSE FOR PEGAMOS OS CANDIDATOS DE CADA VOTO DA LIST<VOTOS> E ADD NA LIST<CANDIDATOS>
                // É A LIST CANDIDATOS QUE VAI SER ALIMENTADA NO CANDIDATO ADAPTER
                if(votos.get(0).getCandidato()!=null) {
                    for (int i = 0; i < votos.size(); i++) {
                        candidatos.add(votos.get(i).getCandidato());
                    }
                }
                //UMA FUNÇÃO SIMPLES PARA EXIBIR OS CANDIDATOS EM ORDEM PELO ID
                Collections.sort(candidatos);
                //APÓS ALIMENTARMOS A LIST<CANDIDATOS> ENVIAMOS ESSE LIST PARA O CANDIDATOS ADAPTER
                listView.setAdapter(new CandidatoAdapter(CandidatosActivity2.this, R.layout.item_candidato,candidatos));
            }
            @Override
            public void onFailure(Call<List<Votos>> call, Throwable t) {
                String nome;
                for (int i = 0; i < 15; i++) {
                    nome = "nome" + i;
                    candidatos.add(new Candidato(i, i * 100, nome));
                }
            }
        });
    }
}
