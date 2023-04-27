package com.example.eleicoes.repository;

import com.example.eleicoes.model.Candidato;
import com.example.eleicoes.model.Eleicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EleicaoDAO extends JpaRepository<Eleicao, Long> {

    @Query(value="SELECT * FROM eleicao e WHERE e.ele_tipo LIKE %:filter%",nativeQuery=true)
    List<Eleicao> findWithFilter(@Param("filter")String filter);

    @Query(value="SELECT * FROM eleicao e WHERE e.ele_tipo = :nome",nativeQuery=true)
    List<Eleicao> findWithName(@Param("nome")String nome);

}
