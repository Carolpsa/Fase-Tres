package br.com.fiap.fasetres.entity;

import java.util.stream.Collectors;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioAutenticado implements UserDetails {

    Usuario usuario;

    public UsuarioAutenticado(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String getUsername() {
        return usuario.getNome();
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    return usuario.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNome()))
            .collect(Collectors.toSet());
}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } 
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }   

    @Override
    public boolean isEnabled() {    
        return usuario.isAtivo();
    }


}
