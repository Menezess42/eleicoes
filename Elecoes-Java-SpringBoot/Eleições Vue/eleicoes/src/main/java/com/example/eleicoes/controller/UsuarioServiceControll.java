package com.example.eleicoes.controller;

import com.example.eleicoes.model.Usuario;
import com.example.eleicoes.model.Votos;
import com.example.eleicoes.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/apis/usuario")
public class UsuarioServiceControll {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping("/validar-usuario")
    private ResponseEntity<Object> validaUsuario(@RequestParam(value="login") String login,
                                                  @RequestParam(value="senha") String senha) {
        var usuario = usuarioDAO.findaByLogin(login);
        if(usuario.isEmpty()) {
            return new ResponseEntity<>(new Usuario(), HttpStatus.NOT_FOUND);
        } else if(usuario.get(0).getSenha() == senha) {
            return new ResponseEntity<>(usuario.get(0).getNivel(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Usuario(), HttpStatus.BAD_REQUEST);
        }
    }

}
