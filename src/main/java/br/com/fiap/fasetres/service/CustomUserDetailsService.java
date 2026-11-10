package br.com.fiap.fasetres.service;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.fiap.fasetres.entity.Usuario;
import br.com.fiap.fasetres.repository.UsuarioRepository;

public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findUsuarioByNome(nome).orElseThrow(() -> new UsernameNotFoundException("User not found with nome: " + nome));
        var roles = usuario.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getNome()))
        .collect(Collectors.toList());
        return User.builder()
                .username(usuario.getNome())
                .password(usuario.getSenha())
                .disabled(!usuario.isAtivo())
                .authorities(roles)
                .build();
    }

}
