package br.com.modelar.sislogmobile.controller;

import br.com.modelar.sislogmobile.model.Usuario;
import br.com.modelar.sislogmobile.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Usuario>> listarTodos() {

        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Usuario> listarPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.listarPorId(id));
    }

    @PostMapping("/salvar")
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.salvar(usuario));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.atualizar(usuario));
    }

    @PutMapping("/atualizarsenha")
    public ResponseEntity<Usuario> atualizarSenha(@RequestBody Usuario usuario) {
        return service.atualizarSenha(usuario);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Usuario> deletar(@PathVariable Long id) {
        return ResponseEntity.ok(service.deletar(id));
    }

    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login,
                                                @RequestParam String password) {
        return service.validarSenha(login, password);
    }

}
