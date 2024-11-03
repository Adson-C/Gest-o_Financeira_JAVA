package com.adsonsa.controlefinanca.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adsonsa.controlefinanca.exception.ErroAutenticacaoExecption;
import com.adsonsa.controlefinanca.exception.RegraNegocioException;
import com.adsonsa.controlefinanca.model.entity.Usuario;
import com.adsonsa.controlefinanca.model.repository.UsuarioRepository;
import com.adsonsa.controlefinanca.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	private UsuarioRepository repository;
	
	@Autowired
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		// verificar se usuario existe
		if (!usuario.isPresent()) {
			throw new ErroAutenticacaoExecption("Usuario não encontrado para email informado");
		}
		// verificar se a senha confere
		if (!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacaoExecption("Senha inválida");
		}
		
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		// verifica se o email existe
		boolean existe = repository.existsByEmail(email);
		if (existe) {
			throw new RegraNegocioException("Email ja existe");
		}
		
	}

}
