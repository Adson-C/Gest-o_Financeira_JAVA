package com.adsonsa.controlefinanca.api.resource;


import com.adsonsa.controlefinanca.api.dto.UsuarioDTO;
import com.adsonsa.controlefinanca.exception.ErroAutenticacaoExecption;
import com.adsonsa.controlefinanca.exception.RegraNegocioException;
import com.adsonsa.controlefinanca.model.entity.Usuario;
import com.adsonsa.controlefinanca.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

    private UsuarioService service;

    // autenticar usuario
    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {
        try {
            Usuario usuarioAtenticar = service.autenticar(dto.getEmail(), dto.getSenha());
            return ResponseEntity.ok().body(usuarioAtenticar);
        }catch (ErroAutenticacaoExecption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public UsuarioResource(UsuarioService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();
        try {
            Usuario usuarioSalvo = service.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}











