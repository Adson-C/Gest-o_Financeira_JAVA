package com.adsonsa.controlefinanca.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
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
