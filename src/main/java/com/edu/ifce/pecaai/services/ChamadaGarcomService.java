package com.edu.ifce.pecaai.services;

import com.edu.ifce.pecaai.dto.ChamadaGarcomRequestDTO;
import com.edu.ifce.pecaai.entities.ChamadaGarcom;
import com.edu.ifce.pecaai.entities.Loja;
import com.edu.ifce.pecaai.repositories.ChamadaGarcomRepository;
import com.edu.ifce.pecaai.repositories.LojaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChamadaGarcomService {

    @Autowired
    private ChamadaGarcomRepository chamadaGarcomRepository;

    @Autowired
    private LojaRepository lojaRepository;

    public List<ChamadaGarcom> listarTodas() {
        return chamadaGarcomRepository.findAll();
    }

    public List<ChamadaGarcom> listarPendentesPorLoja(Long lojaId) {
        return chamadaGarcomRepository.findByLojaIdAndStatusAtendidoFalse(lojaId);
    }

    public ChamadaGarcom buscarPorId(Long id) {
        return chamadaGarcomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamada não encontrada com o ID: " + id));
    }

    public ChamadaGarcom salvar(ChamadaGarcomRequestDTO dto) {
        Loja loja = lojaRepository.findById(dto.lojaId())
                .orElseThrow(() -> new RuntimeException("Loja não encontrada com ID: " + dto.lojaId()));

        ChamadaGarcom chamada = new ChamadaGarcom();
        chamada.setNumeroMesa(dto.numeroMesa());
        chamada.setStatusAtendido(false);
        chamada.setLoja(loja);

        return chamadaGarcomRepository.save(chamada);
    }

    public ChamadaGarcom atenderChamada(Long id) {
        ChamadaGarcom chamada = buscarPorId(id);
        chamada.setStatusAtendido(true);
        return chamadaGarcomRepository.save(chamada);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        chamadaGarcomRepository.deleteById(id);
    }
}