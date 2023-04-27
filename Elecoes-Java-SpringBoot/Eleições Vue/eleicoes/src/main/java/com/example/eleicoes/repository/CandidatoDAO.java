package com.example.eleicoes.repository;

import com.example.eleicoes.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidatoDAO extends JpaRepository<Candidato, Long > {

    @Query(value="SELECT * FROM candidato c WHERE c.ca_nome LIKE %:filter%",nativeQuery=true)
    List<Candidato> findWithFilter(@Param("filter")String filter);

    @Query(value="SELECT * FROM candidato c WHERE c.ca_nome = :nome",nativeQuery=true)
    List<Candidato> findWithName(@Param("nome")String nome);

    @Query(value="SELECT * FROM candidato c WHERE c.par_id = :id", nativeQuery=true)
    List<Candidato> findWithParID(@Param("id")int id);

}
