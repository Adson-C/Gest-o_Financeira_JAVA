package com.adsonsa.controlefinanca.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adsonsa.controlefinanca.exception.RegraNegocioException;
import com.adsonsa.controlefinanca.model.entity.Usuario;
import com.adsonsa.controlefinanca.model.repository.UsuarioRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@Autowired
	UsuarioService service;

	@Autowired
	UsuarioRepository repository;

	@Test
	public void validarEmail() {
		try {
			// cenário
			repository.deleteAll();
			// ação
			service.validarEmail("email@example.com");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void deveLancarErroseExistirEmailJaCadastrado() {
		try {
			// cenário
			Usuario usuario = Usuario.builder().nome("usuario").email("email@example.com").build();
			// ação
			repository.save(usuario);
			// ação
			service.validarEmail("email@example.com");
		}catch (RegraNegocioException e) {
			e.printStackTrace();
		}
	}
}
