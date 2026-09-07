package com.edu.ifce.pecaai.dto;

import java.util.List;

public record PedidoRequestDTO(Long clienteId, Long lojaId, List<PedidoItemRequestDTO> itens) {}