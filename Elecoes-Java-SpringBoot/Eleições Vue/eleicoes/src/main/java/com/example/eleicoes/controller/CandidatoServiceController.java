package com.example.eleicoes.controller;

import com.example.eleicoes.model.Candidato;
import com.example.eleicoes.model.Cargo;
import com.example.eleicoes.model.Partido;
import com.example.eleicoes.repository.CandidatoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/apis/candidato")
public class CandidatoServiceController {

    @Autowired
    private CandidatoDAO candidatoDAO;

    @PostMapping("/incluir")
    public ResponseEntity<Object> inserir(@RequestBody Candidato candidato) {
        try {
            if (candidato.getNome() != null && !candidato.getNome().isEmpty()) {
                candidato.setId(0L);
                candidatoDAO.save(candidato);
                return new ResponseEntity<>(candidato, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new Candidato(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new Candidato() , HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/alterar")
    public ResponseEntity<Candidato> alterar(@RequestBody Candidato novoCandidato) {
        try {
            var id = novoCandidato.getId();
            var response = candidatoDAO.findById(id)
                    .map(candidato -> {
                       candidato.setNome(novoCandidato.getNome());
                       candidato.setNumero(novoCandidato.getNumero());
                       candidato.setPartido(novoCandidato.getPartido());
                       candidatoDAO.save(candidato);
                       return new ResponseEntity<>(candidato, HttpStatus.OK);
                    }).orElse(new ResponseEntity<>(novoCandidato, HttpStatus.NOT_FOUND));
            return response;
        } catch (Exception e) {
            return new ResponseEntity<>(new Candidato(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        try {
            candidatoDAO.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/buscar-todos")
    public ResponseEntity<Object> listarTodos(@RequestParam(value="filtro") String filtro) {
        return new ResponseEntity<>(candidatoDAO.findWithFilter(filtro), HttpStatus.OK);
    }
    @GetMapping("/buscar-ParID")
    public ResponseEntity<Object> buscarPerPartido(@RequestParam(value="id") int id)
    {
        return new ResponseEntity<>(candidatoDAO.findWithParID(id),HttpStatus.OK);
    }
//    @GetMapping("/buscar-todos")
//    public ResponseEntity<Object> listarTodos() {
//        return new ResponseEntity<>(candidatoDAO.findAll(), HttpStatus.OK);
//    }

    @GetMapping("/buscar-um")
    public ResponseEntity<Object> buscarUm(@RequestParam(value="id") Long id) {
        return new ResponseEntity<>(candidatoDAO.findById(id)
                .orElse(new Candidato()), HttpStatus.OK);
    }

    @GetMapping("/buscar-filtro")
    public ResponseEntity<Object> buscaFiltro(@RequestParam(value="nome") String nome) {
        var busca = candidatoDAO.findWithFilter(nome);
        if(nome.isEmpty()) {
            return new ResponseEntity<>(candidatoDAO.findAll(), HttpStatus.NOT_FOUND);
        } else if (busca.isEmpty()) {
            return new ResponseEntity<>(new Partido(), HttpStatus.NOT_FOUND);
        } else {
            return  new ResponseEntity<>(busca, HttpStatus.OK);
        }
    }

    @GetMapping("/buscar-nome")
    public ResponseEntity<Object> buscaNome(@RequestParam(value="nome") String nome) {
        var busca = candidatoDAO.findWithName(nome);
        if(nome.isEmpty() || busca.isEmpty()) {
            return new ResponseEntity<>(new Candidato(), HttpStatus.NOT_FOUND);
        } else {
            return  new ResponseEntity<>(busca.get(0), HttpStatus.OK);
        }
    }


}
