package com.edu.ifce.pecaai.controller;

import com.edu.ifce.pecaai.dto.ChamadaGarcomRequestDTO;
import com.edu.ifce.pecaai.dto.ChamadaGarcomResponseDTO;
import com.edu.ifce.pecaai.entities.ChamadaGarcom;
import com.edu.ifce.pecaai.services.ChamadaGarcomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chamadas")
public class ChamadaGarcomController {

    @Autowired
    private ChamadaGarcomService chamadaGarcomService;

    @GetMapping
    public ResponseEntity<List<ChamadaGarcomResponseDTO>> listarTodas() {
        List<ChamadaGarcomResponseDTO> lista = chamadaGarcomService.listarTodas().stream()
                .map(ChamadaGarcomResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);

    }
    @GetMapping("/pendentes/loja/{lojaId}")
    public ResponseEntity<List<ChamadaGarcomResponseDTO>> listarPendentesPorLoja(@PathVariable Long lojaId) {
        List<ChamadaGarcomResponseDTO> lista = chamadaGarcomService.listarPendentesPorLoja(lojaId).stream()
                .map(ChamadaGarcomResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<ChamadaGarcomResponseDTO> criar(@RequestBody ChamadaGarcomRequestDTO dto) {
        ChamadaGarcom salva = chamadaGarcomService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ChamadaGarcomResponseDTO.fromEntity(salva));
    }

    @PutMapping("/{id}/atender")
    public ResponseEntity<ChamadaGarcomResponseDTO> atender(@PathVariable Long id) {
        ChamadaGarcom atendida = chamadaGarcomService.atenderChamada(id);
        return ResponseEntity.ok(ChamadaGarcomResponseDTO.fromEntity(atendida));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        chamadaGarcomService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}