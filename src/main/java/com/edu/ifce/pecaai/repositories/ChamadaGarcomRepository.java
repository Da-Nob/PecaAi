package com.edu.ifce.pecaai.repositories;

import com.edu.ifce.pecaai.entities.ChamadaGarcom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadaGarcomRepository extends JpaRepository<ChamadaGarcom, Long> {
    List<ChamadaGarcom> findByLojaIdAndStatusAtendidoFalse(Long lojaId);
}