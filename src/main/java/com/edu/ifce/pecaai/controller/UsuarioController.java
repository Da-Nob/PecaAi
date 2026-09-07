package com.edu.ifce.pecaai.controller;

import com.edu.ifce.pecaai.dto.UsuarioDTO;
import com.edu.ifce.pecaai.dto.UsuarioRequestDTO;
import com.edu.ifce.pecaai.entities.Usuario;
import com.edu.ifce.pecaai.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> lista = usuarioService.listarTodos().stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(UsuarioDTO.fromEntity(usuarioService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioRequestDTO dto) {
        Usuario salvo = usuarioService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioDTO.fromEntity(salvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}