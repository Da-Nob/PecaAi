package com.edu.ifce.pecaai.repositories;

import com.edu.ifce.pecaai.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByLojaId(Long lojaId);
}