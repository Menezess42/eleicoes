package com.example.eleicoes.repository;

import com.example.eleicoes.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CargoDAO extends JpaRepository<Cargo, Long> {

    @Query(value="SELECT * FROM cargo c WHERE c.car_descr LIKE %:filter%",nativeQuery=true)
    List<Cargo> findWithFilter(@Param("filter")String filter);

}
