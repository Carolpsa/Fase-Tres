package br.com.fiap.fasetres.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.fasetres.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findUsuarioByNome(String nome);
}
