package br.com.fiap.fasetres.service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.fiap.fasetres.entity.UsuarioAutenticado;
import br.com.fiap.fasetres.repository.UsuarioRepository;

@Service 
public class UserDetailsServiceImpl implements UserDetailsService {

     
    private final UsuarioRepository usuarioRepository;

    
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    
    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        return usuarioRepository.findUsuarioByNome(nome)
                .map(UsuarioAutenticado::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + nome));
    }

}
