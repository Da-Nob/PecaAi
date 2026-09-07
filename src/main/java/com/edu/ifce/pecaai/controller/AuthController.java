package com.edu.ifce.pecaai.controller;

import com.edu.ifce.pecaai.dto.AuthenticationDTO;
import com.edu.ifce.pecaai.dto.LoginResponseDTO;
import com.edu.ifce.pecaai.dto.UsuarioRequestDTO;
import com.edu.ifce.pecaai.entities.UserRole;
import com.edu.ifce.pecaai.entities.Usuario;
import com.edu.ifce.pecaai.repositories.UsuarioRepository;
import com.edu.ifce.pecaai.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UsuarioRequestDTO data) {
        if (this.repository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario newUser = new Usuario();
        newUser.setNome(data.nome());
        newUser.setEmail(data.email());
        newUser.setSenha(encryptedPassword);
        newUser.setRole(UserRole.valueOf(data.tipo().toUpperCase()));

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}