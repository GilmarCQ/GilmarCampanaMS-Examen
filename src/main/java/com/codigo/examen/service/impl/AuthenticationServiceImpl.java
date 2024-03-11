package com.codigo.examen.service.impl;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.request.SignInRequest;
import com.codigo.examen.request.SignUpRequest;
import com.codigo.examen.response.AuthenticationResponse;
import com.codigo.examen.service.AuthenticationService;
import com.codigo.examen.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public Usuario signUpUser(SignUpRequest signUpRequest) {
        Set<Rol> assignedRoles = new HashSet<>();
        Optional<Rol> rol = rolRepository.findById(0L);
        if (!rol.isPresent()) {
            new Exception("No se encontro el rol");
        }
        assignedRoles.add(rol.get());
        Usuario usuario = new Usuario();
        usuario.setEmail(signUpRequest.getEmail());
        usuario.setUsername(signUpRequest.getUsername());
        usuario.setTelefono(signUpRequest.getTelefono());
        usuario.setRoles(assignedRoles);
        usuario.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario signUpAdmin(SignUpRequest signUpRequest) {
        Set<Rol> assignedRoles = new HashSet<>();
        Optional<Rol> rol = rolRepository.findById(1L);
        if (!rol.isPresent()) {
            new Exception("No se encontro el rol");
        }
        assignedRoles.add(rol.get());
        Usuario usuario = new Usuario();
        usuario.setEmail(signUpRequest.getEmail());
        usuario.setUsername(signUpRequest.getUsername());
        usuario.setTelefono(signUpRequest.getTelefono());
        usuario.setRoles(assignedRoles);
        usuario.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public AuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),signInRequest.getPassword()));
        var user = usuarioRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Nombre de usuario no válido"));

        var jwt = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwt);
        return authenticationResponse;
    }
}
