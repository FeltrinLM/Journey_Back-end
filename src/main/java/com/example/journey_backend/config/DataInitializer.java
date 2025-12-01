package com.example.journey_backend.config;

// Importamos a classe Usuario correta
import com.example.journey_backend.model.Usuario;
import com.example.journey_backend.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica se o banco está vazio
            if (usuarioRepository.count() == 0) {
                System.out.println("---------------------------------------------");
                System.out.println("BANCO VAZIO DETECTADO. CRIANDO ADMIN PADRÃO...");

                Usuario admin = new Usuario();

                admin.setNome("admin_master");
                admin.setSenha(passwordEncoder.encode("admin123"));

                // --- A CORREÇÃO ESTÁ AQUI ---
                // Definimos o tipo como ADMINISTRADOR (obrigatório por causa do @NotNull)
                admin.setTipo(Usuario.TipoUsuario.ADMINISTRADOR);

                // Salvando no banco
                usuarioRepository.save(admin);

                System.out.println("USUÁRIO CRIADO: admin_master / admin123");
                System.out.println("Tipo: ADMINISTRADOR");
                System.out.println("---------------------------------------------");
            }
        };
    }
}