package com.example.eleicoes.repository;

import com.example.eleicoes.model.Eleicao;
import com.example.eleicoes.model.Partido;
import com.example.eleicoes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    @Query(value="SELECT * FROM usuario u WHERE u.login LIKE %:filter%",nativeQuery=true)
    List<Usuario> findaByLogin(@Param("filter")String filter);

}
