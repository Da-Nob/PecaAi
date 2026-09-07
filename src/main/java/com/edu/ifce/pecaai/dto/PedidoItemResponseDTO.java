package com.edu.ifce.pecaai.dto;

import com.edu.ifce.pecaai.entities.PedidoItem;

public record PedidoItemResponseDTO(Long id, Long produtoId, String produtoNome, Integer quantidade) {
    public static PedidoItemResponseDTO fromEntity(PedidoItem item) {
        return new PedidoItemResponseDTO(
            item.getId(),
            item.getProduto() != null ? item.getProduto().getId() : null,
            item.getProduto() != null ? item.getProduto().getNome() : null,
            item.getQuantidade()
        );
    }
}
