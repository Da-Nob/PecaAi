package com.edu.ifce.pecaai.exceptions;

import java.time.LocalDateTime;

public record ErroResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {}