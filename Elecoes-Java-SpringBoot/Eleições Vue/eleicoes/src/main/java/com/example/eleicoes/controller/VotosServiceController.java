package com.example.eleicoes.controller;

import com.example.eleicoes.model.Votos;
import com.example.eleicoes.repository.VotosDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/apis/votos")
public class VotosServiceController {

    @Autowired
    private VotosDAO votosDAO;

    @PostMapping("/incluir")
    public ResponseEntity<Object> inserir(@RequestBody Votos votos) {
        try {
            votos.setVot_id(0L);
            votosDAO.save(votos);
            return new ResponseEntity<>(votos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new Votos() , HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/incluir-varios")
    public ResponseEntity<Object> inserirVarios(@RequestBody List<Votos> votos) {
        try {
            for (Votos voto: votos) {
                voto.setVot_id(0L);
                votosDAO.save(voto);
            }
            return new ResponseEntity<>(votos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new Votos() , HttpStatus.BAD_REQUEST);
        }
    }
    // Ver se Votos pode ALTERAR
    @PutMapping("/alterar")
    public ResponseEntity<Votos> alterar(@RequestBody Votos novoVotos) {
        System.out.println("aaaaaaaaaaaaa:"+novoVotos.getTotal());
        try {
            var id = novoVotos.getVot_id();
            return votosDAO.findById(id)
                    .map(votos -> {
                        votos.setTotal(novoVotos.getTotal());
                        votosDAO.save(votos);
                        return new ResponseEntity<>(votos, HttpStatus.OK);
                    }).orElse(new ResponseEntity<>(new Votos(), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(new Votos(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/alterar")
    public void update(@RequestBody Votos novoVotos) {
        //System.out.println("aaaaaaaaaaaaa:" + novoVotos.getTotal());
        int i = novoVotos.getVot_id().intValue();
        //System.out.println("bbbbbbbbbb: "+i);
        votosDAO.updateVoto(novoVotos.getVot_id().intValue(),novoVotos.getTotal());
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        try {
            votosDAO.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @GetMapping("/buscar-todos")
    public ResponseEntity<Object> listarTodos() {
        return new ResponseEntity<>(votosDAO.findAll(), HttpStatus.OK);
    }

//    @GetMapping("/buscar-todos")
//    public ResponseEntity<Object> listarTodos() {
//        return new ResponseEntity<>(votosDAO.findAll(), HttpStatus.OK);
//    }

    @GetMapping("/buscar-um")
    public ResponseEntity<Object> buscarUm(@RequestParam(value="id") Long id) {
        return new ResponseEntity<>(votosDAO.findById(id)
                .orElse(new Votos()), HttpStatus.OK);
    }


    @GetMapping("/buscar-eleicao")
    public ResponseEntity<Object> buscaEleicao(@RequestParam(value="filtro")int filtro)
    {
        //System.out.println("++++++++++++++++++++++++++++++++++++");
        var votos = votosDAO.findByEleId(filtro);
        if(votos.isEmpty()) {
            return new ResponseEntity<>(new Votos(), HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(votos, HttpStatus.OK);
        }
    }
    @GetMapping("/cand")
    public ResponseEntity<Object> buscarEle(@RequestParam(value="filtro")int filtro)
    {
        var votos = votosDAO.findByCaId(filtro);
        System.out.println(""+votos.get(0));
        if(votos.isEmpty()) {
            return new ResponseEntity<>(new Votos(), HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(votos, HttpStatus.OK);
        }
    }
}
