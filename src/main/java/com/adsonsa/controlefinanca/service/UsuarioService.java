package com.adsonsa.controlefinanca.service;

import com.adsonsa.controlefinanca.model.entity.Usuario;

public interface UsuarioService {
	
	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);

}
