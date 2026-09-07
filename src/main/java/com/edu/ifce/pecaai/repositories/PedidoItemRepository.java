package com.edu.ifce.pecaai.repositories;

import com.edu.ifce.pecaai.entities.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    List<PedidoItem> findByPedidoId(Long pedidoId);
}