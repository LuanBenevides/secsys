package br.com.modelar.sislogmobile.repository;

import br.com.modelar.sislogmobile.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Optional<Usuario> findByLogin(String Login);
}
