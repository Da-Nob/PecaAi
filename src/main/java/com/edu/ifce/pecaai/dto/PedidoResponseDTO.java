// dto/PedidoResponseDTO.java
package com.edu.ifce.pecaai.dto;

import com.edu.ifce.pecaai.entities.Pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PedidoResponseDTO(
        Long id,
        LocalDateTime dataHora,
        Long lojaId,
        Long clienteId,
        List<PedidoItemResponseDTO> itens
) {
    public static PedidoResponseDTO fromEntity(Pedido pedido) {
        List<PedidoItemResponseDTO> itensDTO = pedido.getItens() == null
                ? List.of()
                : pedido.getItens().stream()
                    .map(PedidoItemResponseDTO::fromEntity)
                    .collect(Collectors.toList());

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getDataHora(),
                pedido.getLoja() != null ? pedido.getLoja().getId() : null,
                pedido.getUsuario() != null ? pedido.getUsuario().getId() : null,
                itensDTO
        );
    }
}