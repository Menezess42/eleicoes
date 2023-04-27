package com.example.app_votacao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CNService {

    //ROTA Q PEGA DO BACKEND TODAS AS ELEIÇÕES EXISTENTES
    @GET("eleicao/find_all")
    Call<List<Eleicao>> buscarTodos();

    //ROTA QUE PEGA DO BACKEND TODOS OS VOTOS ASSOCIADOS COM A ELEIÇÃO ENVIADA POR PARÂMETRO
    @GET("votos/buscar-eleicao?")
    Call<List<Votos>> buscarTodosCandidatos(@Query("filtro") int ele_id);

    //ROTA QUE ENVIA INFORMAÇÃO PARA O BACK PARA Q O VOTO NO CANDIDATO ESPECÍFICO SEJA COMPUTADO
    @POST("votos/alterar")
    Call<Votos> updateVotos(@Body Votos voto);
}
