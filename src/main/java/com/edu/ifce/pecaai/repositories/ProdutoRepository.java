package com.edu.ifce.pecaai.repositories;

import com.edu.ifce.pecaai.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByLojaId(Long lojaId);
}