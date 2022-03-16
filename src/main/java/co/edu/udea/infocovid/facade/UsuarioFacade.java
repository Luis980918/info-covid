package co.edu.udea.infocovid.facade;

import co.edu.udea.infocovid.entity.Usuario;
import co.edu.udea.infocovid.mapper.UsuarioMapper;
import co.edu.udea.infocovid.modelo.UsuarioDTO;
import co.edu.udea.infocovid.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

@Service
@Transactional
public class UsuarioFacade {
    private static Logger logger = LoggerFactory.getLogger(UsuarioFacade.class);
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioFacade(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }
    
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        return usuarioMapper.toDto(usuarioService.guardarUsuario(usuarioMapper.toEntity(usuarioDTO)));
    }

    public UsuarioDTO verificarInformacionInicioSesion(String correo, String contrasena) {
        Usuario usuario = usuarioService.buscarUsuario(correo, contrasena);
        return usuarioMapper.toDto(usuario);
    }
}
