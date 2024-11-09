package com.adsonsa.controlefinanca.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adsonsa.controlefinanca.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		// cenário
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		// ação
		boolean result = repository.existsByEmail("usuario@email.com");
		// verificação
		Assertions.assertThat(result).isTrue();
	}

	@Test
	public void deveRetornarFalsoQuandoNaoCadastroEmail() {
		// ação
		boolean result = repository.existsByEmail("usuario@email.com");
		// verificação
		Assertions.assertThat(result).isFalse();
	}
	// persistir usuario na base de dados
	@Test
	public void devePersistirUnUsuarionaBaseDeDados() {
		// cenario
		Usuario usuario = criarUsuario();
		// Ação
		Usuario usuarioSalvo = repository.save(usuario);
		
		// verificacao
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}
	
	@Test
	public void deveBusacarUsuarioPorEmail() {
		// cenario
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		// verificacao
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");
		
		Assertions.assertThat(result.isPresent()).isTrue();
		
	}
	
	@Test
	public void deveRetornaVazioAoUsuarioPorEmailNaBase() {
		// verificacao
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");
		
		Assertions.assertThat(result.isPresent()).isFalse();
		
	}
	
	// criando usuario para instaciar
	public static Usuario criarUsuario() {
		return Usuario.builder()
		  .nome("usuario")
		  .email("usuario@email.com")
		  .senha("senha")
		  .build();
	}

}
