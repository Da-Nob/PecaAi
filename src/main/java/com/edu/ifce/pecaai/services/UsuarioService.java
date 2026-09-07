package com.edu.ifce.pecaai.services;

import com.edu.ifce.pecaai.dto.UsuarioRequestDTO;
import com.edu.ifce.pecaai.entities.Usuario;
import com.edu.ifce.pecaai.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.ifce.pecaai.entities.UserRole;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }

    public Usuario salvar(UsuarioRequestDTO dto) {
    Usuario usuario = new Usuario();
    usuario.setNome(dto.nome());
    usuario.setEmail(dto.email());
    usuario.setSenha(dto.senha());
    usuario.setRole(UserRole.valueOf(dto.tipo().toUpperCase()));
    return usuarioRepository.save(usuario);
}

    public void deletar(Long id) {
        buscarPorId(id);
        usuarioRepository.deleteById(id);
    }
}