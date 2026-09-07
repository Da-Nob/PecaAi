package com.edu.ifce.pecaai.controller;

import com.edu.ifce.pecaai.dto.ProdutoRequestDTO;
import com.edu.ifce.pecaai.entities.Produto;
import com.edu.ifce.pecaai.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/loja/{lojaId}")
    public ResponseEntity<List<Produto>> listarPorLoja(@PathVariable Long lojaId) {
        return ResponseEntity.ok(produtoService.listarPorLoja(lojaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody ProdutoRequestDTO dto) {
        Produto produtoSalvo = produtoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}