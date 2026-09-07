package com.edu.ifce.pecaai.controller;

import com.edu.ifce.pecaai.dto.PedidoRequestDTO;
import com.edu.ifce.pecaai.dto.PedidoResponseDTO;
import com.edu.ifce.pecaai.entities.Pedido;
import com.edu.ifce.pecaai.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        List<PedidoResponseDTO> lista = pedidoService.listarTodos().stream()
                .map(PedidoResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/loja/{lojaId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorLoja(@PathVariable Long lojaId) {
        List<PedidoResponseDTO> lista = pedidoService.listarPorLoja(lojaId).stream()
                .map(PedidoResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(PedidoResponseDTO.fromEntity(pedido));
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(@RequestBody PedidoRequestDTO dto) {
        Pedido pedidoSalvo = pedidoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoResponseDTO.fromEntity(pedidoSalvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}