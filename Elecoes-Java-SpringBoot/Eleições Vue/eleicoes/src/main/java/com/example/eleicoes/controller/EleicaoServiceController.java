package com.example.eleicoes.controller;

import com.example.eleicoes.model.Candidato;
import com.example.eleicoes.model.Eleicao;
import com.example.eleicoes.model.Partido;
import com.example.eleicoes.repository.EleicaoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/apis/eleicao")
public class EleicaoServiceController {

    @Autowired
    private EleicaoDAO eleicaoDAO;

    @PostMapping("/incluir")
    public ResponseEntity<Object> inserir(@RequestBody Eleicao eleicao) {
        try {
            if (eleicao.getTipo() != null && !eleicao.getTipo().isEmpty()) {
                // Tenho colocar 0 no id para não alterar?
                eleicao.setId(0L);
                eleicaoDAO.save(eleicao);
                return new ResponseEntity<>(eleicao, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new Eleicao(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new Eleicao() , HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/alterar")
    public ResponseEntity<Eleicao> alterar(@RequestBody Eleicao novaEleicao) {
        try {
            var id = novaEleicao.getId();
            return eleicaoDAO.findById(id)
                    .map(eleicao -> {
                        eleicao.setAno(novaEleicao.getAno());
                        eleicao.setTipo(novaEleicao.getTipo());
                        eleicaoDAO.save(eleicao);
                        return new ResponseEntity<>(eleicao, HttpStatus.OK);
                    }).orElse(new ResponseEntity<>(new Eleicao(), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(new Eleicao(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        try {
            eleicaoDAO.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/buscar-todos")
    public ResponseEntity<Object> listarTodos(@RequestParam(value="filtro") String filtro) {
        return new ResponseEntity<>(eleicaoDAO.findWithFilter(filtro), HttpStatus.OK);
    }

    @GetMapping("/find_all")
   public ResponseEntity<Object> listarTodos()
    {
        return new ResponseEntity<>(eleicaoDAO.findAll(), HttpStatus.OK);
    }

    @GetMapping("/buscar-um")
    public ResponseEntity<Object> buscarUm(@RequestParam(value="id") Long id) {
        return new ResponseEntity<>(eleicaoDAO.findById(id)
                .orElse(new Eleicao()), HttpStatus.OK);
    }

    @GetMapping("/buscar-filtro")
    public ResponseEntity<Object> buscaFiltro(@RequestParam(value="nome") String nome) {
        var busca = eleicaoDAO.findWithFilter(nome);
        if(nome.isEmpty()) {
            return new ResponseEntity<>(eleicaoDAO.findAll(), HttpStatus.NOT_FOUND);
        } else if (busca.isEmpty()) {
            return new ResponseEntity<>(new Partido(), HttpStatus.NOT_FOUND);
        } else {
            return  new ResponseEntity<>(busca, HttpStatus.OK);
        }
    }

    @GetMapping("/buscar-nome")
    public ResponseEntity<Object> buscaNome(@RequestParam(value="nome") String nome) {
        var busca = eleicaoDAO.findWithName(nome);
        System.out.println(busca.get(0));
        if(nome.isEmpty() || busca.isEmpty()) {
            return new ResponseEntity<>(new Eleicao(), HttpStatus.NOT_FOUND);
        } else {
            return  new ResponseEntity<>(busca.get(0), HttpStatus.OK);
        }
    }

}
