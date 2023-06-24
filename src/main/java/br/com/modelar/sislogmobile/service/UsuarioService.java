package br.com.modelar.sislogmobile.service;

import br.com.modelar.sislogmobile.exception.BusinessException;
import br.com.modelar.sislogmobile.exception.NoContentException;
import br.com.modelar.sislogmobile.model.Usuario;
import br.com.modelar.sislogmobile.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario listarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new NoContentException("Não foi possível alterar o usuario ou a senha"));
    }

    public Usuario salvar(Usuario usuario) {
        if(usuario.getId() != null) {
            throw new BusinessException("O ID é diferente de nulo na inclusão. O ID precisa estar vazio...");
        }
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        Usuario save = usuarioRepository.save(usuario);
        return save;
    }
    public Usuario atualizar(Usuario usuario) {
        Usuario usuarioNoBanco = this.listarPorId(usuario.getId());
        if(usuarioNoBanco.getId().equals(usuario.getId())) {
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        } else {
            throw new BusinessException("Os ID's para alteração não conferem");
        }
    }

    public ResponseEntity atualizarSenha(Usuario usuario) {
        Optional<Usuario> usuarioDB = usuarioRepository.findById(usuario.getId());

        if (!usuarioDB.isPresent()) {
            return ResponseEntity.ok("Não foi possível alterar a senha.");
        }
        if(usuarioDB.get().getId().equals(usuario.getId())) {
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Senha atualizada: " + usuario);
        }else {
            return ResponseEntity.ok("Não foi possível alterar a senha.");
        }
    }

    public Usuario deletar(Long id) {
        Usuario usuarioNoBanco = listarPorId(id);
        usuarioRepository.delete(usuarioNoBanco);
        return usuarioNoBanco;
    }

    public ResponseEntity<Boolean> validarSenha(String login, String password) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByLogin(login);

        if(optionalUsuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        Usuario usuario = optionalUsuario.get();
        Boolean valid = encoder.matches(password, usuario.getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }
}
