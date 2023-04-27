package com.example.eleicoes.repository;

import com.example.eleicoes.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoDAO extends JpaRepository<Partido, Long> {

    @Query(value="SELECT * FROM partido p WHERE p.par_nome LIKE %:filter%",nativeQuery=true)
    List<Partido> findWithFilter(@Param("filter")String filter);


}
