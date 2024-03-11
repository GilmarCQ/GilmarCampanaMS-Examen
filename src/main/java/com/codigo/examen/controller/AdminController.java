package com.codigo.examen.controller;


import com.codigo.examen.entity.Usuario;
import com.codigo.examen.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ms-examen/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UsuarioService usuarioService;

    @PutMapping
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(id,usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        return usuarioService.deleteUsuario(id);
    }

}
