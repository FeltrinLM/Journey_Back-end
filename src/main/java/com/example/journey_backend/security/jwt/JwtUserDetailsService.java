package com.example.journey_backend.security.jwt;

import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.repository.UsuarioRepository;
import com.example.journey_backend.security.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário no banco com base no nome
        Usuario usuario = usuarioRepository.findByNome(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // Retorna uma instância de CustomUserDetails para o Spring Security
        return new CustomUserDetails(usuario);
    }
}
