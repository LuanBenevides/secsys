package br.com.modelar.sislogmobile.service;

import br.com.modelar.sislogmobile.data.DetalhesUsuarioData;
import br.com.modelar.sislogmobile.model.Usuario;
import br.com.modelar.sislogmobile.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DetalhesUsuarioServiceImpl implements UserDetailsService {

    private final UsuarioRepository repository;

    public DetalhesUsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findByLogin(username);
        if(username.isEmpty()) {
            throw new UsernameNotFoundException("Usuário [" + username + "] não encontrado");
        }
        return new DetalhesUsuarioData(usuario);
    }
}
