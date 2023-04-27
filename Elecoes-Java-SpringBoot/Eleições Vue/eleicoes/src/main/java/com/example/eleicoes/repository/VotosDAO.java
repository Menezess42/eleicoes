package com.example.eleicoes.repository;

import com.example.eleicoes.model.Candidato;
import com.example.eleicoes.model.Partido;
import com.example.eleicoes.model.Votos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VotosDAO extends JpaRepository<Votos, Long> {
    @Query(value="SELECT * FROM votos v WHERE v.ele_id = :filter",nativeQuery=true)
    List<Votos> findByEleId(@Param("filter")int filter);

    @Query(value="UPDATE VOTOS set vot_total= :total WHERE vot_id= :id",nativeQuery = true)
    List<Votos> updateVoto(@Param("id")int id,@Param("total") int total);

    @Query(value="SELECT * FROM votos v WHERE v.ca_id = :filter",nativeQuery = true)
    List<Votos> findByCaId(@Param("filter") int filter);
}
