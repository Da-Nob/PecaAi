package com.edu.ifce.pecaai.repositories;

import com.edu.ifce.pecaai.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Método que o Spring Security vai usar para buscar o usuário no login
    UserDetails findByEmail(String email);
}