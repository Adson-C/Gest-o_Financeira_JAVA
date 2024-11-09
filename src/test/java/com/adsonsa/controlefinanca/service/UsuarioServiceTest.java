package com.adsonsa.controlefinanca.service;

import com.adsonsa.controlefinanca.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adsonsa.controlefinanca.exception.ErroAutenticacaoExecption;
import com.adsonsa.controlefinanca.exception.RegraNegocioException;
import com.adsonsa.controlefinanca.model.repository.UsuarioRepository;
import com.adsonsa.controlefinanca.service.impl.UsuarioServiceImpl;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@SpyBean
	UsuarioServiceImpl service;
	@MockBean
	UsuarioRepository repository;

	@Test
	public void deveSalvarUsuario() {
		// cenario
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder().id(1l).nome("nome").email("email@email.com").senha("senha").build();

		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		// acao
		Usuario usuarioSalvo = service.salvarUsuario(new Usuario());

		//vereficacao
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
	}
	// Nao deve salvar usuario com email ja cadastrado
	@Test
	public void naoDeveSalvarUmUsuarioComEmailCadastrado() {
		String email = "email@email.com";
		// cenario
		Usuario usuario = Usuario.builder().email(email).build();
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		// acao
		try {
			service.salvarUsuario(usuario);
		}catch (RegraNegocioException e) {
			e.printStackTrace();
		}

		// verificacao
		Mockito.verify(repository, Mockito.never()).save(Mockito.any(Usuario.class));
	}

	@Test
	public void deveAutenticarUmUsuarioComSucesso() {
		String email = "email@email.com";
		String senha = "senha";

		// cenario
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		// acao
		Usuario result = service.autenticar(email, senha);

		// verificacao
		Assertions.assertThat(result).isNotNull();
	}


	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradocomEmailInformado() {
		// cenario
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		// acao
		Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "senha"));
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacaoExecption.class).hasMessage("Usuario não encontrado para email informado");
	}

	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		String email = "email@email.com";
		String senha = "senha";
		// cenario
		Usuario usuario = Usuario.builder().email(email).senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		// acao
		Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "123" ));
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacaoExecption.class).hasMessage("Senha inválida");
	}


	@Test
	public void validarEmail() {
			// cenário
			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
			
			// ação
			service.validarEmail("email@example.com");
	}
	@Test
	public void deveLancarErroseExistirEmailJaCadastrado() {
		
		try {
			// cenário
			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
			// ação
			service.validarEmail("email@example.com");
			
		}catch (RegraNegocioException e) {
			e.printStackTrace();
		}
		
		
	}
}
