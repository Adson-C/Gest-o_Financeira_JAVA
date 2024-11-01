package com.adsonsa.controlefinanca.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adsonsa.controlefinanca.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
//	Optional<Usuario> findByEmail(String email);
	// verifica se o email existe
	boolean existsByEmail(String email);

}
