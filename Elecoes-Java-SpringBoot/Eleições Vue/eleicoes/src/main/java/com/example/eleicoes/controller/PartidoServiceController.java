package com.example.eleicoes.controller;

import com.example.eleicoes.model.Partido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.eleicoes.repository.PartidoDAO;

import java.util.List;
import java.util.Locale;

@CrossOrigin
@RestController
@RequestMapping("/apis/partido")
public class PartidoServiceController {

    // O certo não é fazer com if... é com try.. catch() ? ( alterar | excluir | deletar )

    @Autowired
    private PartidoDAO partidoDAO;

    @PostMapping("/incluir")
    public ResponseEntity<Object> incluir(@RequestBody Partido partido) {
        try {
            if(partido.getNome() != null && !partido.getNome().isEmpty()) {
                partido.setId(0L);
                partidoDAO.save(partido);
                return new ResponseEntity<>(partido, HttpStatus.CREATED);
            } else {
                // Retorna caso passo nome vazio, impesso de salvar campos vazio
                // de nome.
                return new ResponseEntity<>(new Partido(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new Partido(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/alterar")
    public ResponseEntity<Partido> alterar(@RequestBody Partido novoPartido) {
        var id = novoPartido.getId();
        return partidoDAO.findById(id)
                .map(partido -> {
                    partido.setNome(novoPartido.getNome());
                    partidoDAO.save(partido);
                    return new ResponseEntity<>(partido, HttpStatus.OK);
                }).orElse(new ResponseEntity<>(new Partido(), HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        try {
            partidoDAO.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
        *** Coloquei mesmo nome da rota listar todos e buscar, porque se não passar nada
        como paramentro no buscar irá cair no lista que traz todos.
     */
    @GetMapping("/buscar-todos")
    public ResponseEntity<Object> buscarTodos(@RequestParam(value="filtro") String filtro) {
        return new ResponseEntity<>(partidoDAO.findWithFilter(filtro.toUpperCase()), HttpStatus.OK);
    }

//    @GetMapping("/buscar-todos")
//    public ResponseEntity<Object> buscarTodos() {
//        return new ResponseEntity<>(partidoDAO.findAll(), HttpStatus.OK);
//    }

    // Poderia ter feito com (@RequestParam(value="id") Long id) -->
    // OU @PathVariable
    // Ai tem passar /listar?id=1"
    @GetMapping("/buscar-um")
    public ResponseEntity<Object> buscarUm(@RequestParam(value="id") Long id) {
        return new ResponseEntity<>(partidoDAO.findById(id)
                .orElse(new Partido()), HttpStatus.OK);
    }

    /*
        *** Ver se é desta forma, criei uma rota vazia caso o usuário não informe nenhum filtro
        assim no caso irá retornar todos, por chamo o métodos listarTodos() .
        * Posso solucionar ao inves de pegar por (@PathVariable(value="nome") String nome) pego
        * por Parametro e se tiver vazio eu trago todos, conforme abaixo
    */
//    @GetMapping("/listarfiltro/")
//    public ResponseEntity<Object> listarFiltroVazio() {
//        return listarTodos();
//    }

    @GetMapping("/buscar-filtro")
    public ResponseEntity<Object> buscaFiltro(@RequestParam(value="nome") String nome) {
        var busca = partidoDAO.findWithFilter(nome);
        if(nome.isEmpty()) {
            return new ResponseEntity<>(partidoDAO.findAll(), HttpStatus.NOT_FOUND);
        } else if (busca.isEmpty()) {
            return new ResponseEntity<>(new Partido(), HttpStatus.NOT_FOUND);
        } else {
            return  new ResponseEntity<>(busca, HttpStatus.OK);
        }
    }

}
