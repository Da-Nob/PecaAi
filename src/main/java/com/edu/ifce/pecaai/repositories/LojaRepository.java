package com.edu.ifce.pecaai.repositories;

import com.edu.ifce.pecaai.entities.Loja;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface LojaRepository extends JpaRepository<Loja, Long> {
    Optional<Loja> findByCnpj(String cnpj);
}