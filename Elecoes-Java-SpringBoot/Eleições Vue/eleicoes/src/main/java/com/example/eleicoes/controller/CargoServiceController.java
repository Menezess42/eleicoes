package com.example.eleicoes.controller;

import com.example.eleicoes.model.Cargo;
import com.example.eleicoes.model.Partido;
import com.example.eleicoes.model.Votos;
import com.example.eleicoes.repository.CargoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/apis/cargo")
public class CargoServiceController {

    @Autowired
    private CargoDAO cargoDAO;

    @PostMapping("/incluir")
    public ResponseEntity<Object> inserir(@RequestBody Cargo cargo) {
        try {
            if (cargo.getDescricao() != null && !cargo.getDescricao().isEmpty()) {
                cargo.setId(0L);
                cargoDAO.save(cargo);
                return new ResponseEntity<>(cargo, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new Cargo(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new Cargo() , HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/alterar")
    public ResponseEntity<Cargo> alterar(@RequestBody Cargo novoCargo) {
        try {
            var id = novoCargo.getId();
            return cargoDAO.findById(id)
                    .map(cargo -> {
                        cargo.setDescricao(novoCargo.getDescricao());
                        cargoDAO.save(cargo);
                        return new ResponseEntity<>(cargo, HttpStatus.OK);
                    }).orElse(new ResponseEntity<>(new Cargo(), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(new Cargo(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        try {
            cargoDAO.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/busca-todos")
    public ResponseEntity<Object> listarTodos(@RequestParam(value="filtro") String filtro) {
        return new ResponseEntity<>(cargoDAO.findWithFilter(filtro), HttpStatus.OK);
    }

//    @GetMapping("/busca-todos")
//    public ResponseEntity<Object> listarTodos() {
//        return new ResponseEntity<>(cargoDAO.findAll(), HttpStatus.OK);
//    }

    @GetMapping("/buscar-um")
    public ResponseEntity<Object> buscarUm(@RequestParam(value="id") Long id) {
        return new ResponseEntity<>(cargoDAO.findById(id)
                .orElse(new Cargo()), HttpStatus.OK);
    }

    @GetMapping("/buscar-filtro")
    public ResponseEntity<Object> buscaFiltro(@RequestParam(value="nome") String nome) {
        var busca = cargoDAO.findWithFilter(nome);
        if(nome.isEmpty()) {
            return new ResponseEntity<>(cargoDAO.findAll(), HttpStatus.NOT_FOUND);
        } else if (busca.isEmpty()) {
            return new ResponseEntity<>(new Partido(), HttpStatus.NOT_FOUND);
        } else {
            return  new ResponseEntity<>(busca, HttpStatus.OK);
        }
    }

}
