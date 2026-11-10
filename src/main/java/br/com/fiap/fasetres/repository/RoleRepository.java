package br.com.fiap.fasetres.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.fasetres.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
