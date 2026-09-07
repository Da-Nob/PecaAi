package com.edu.ifce.pecaai.services;

import com.edu.ifce.pecaai.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails usuario = repository.findByEmail(username);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + username);
        }
        
        return usuario;
    }
}