package co.edu.udea.infocovid.service;
import co.edu.udea.infocovid.entity.Usuario;
import co.edu.udea.infocovid.exception.BusinessException;
import co.edu.udea.infocovid.mapper.UsuarioMapper;
import co.edu.udea.infocovid.repository.UsuarioRepository;
import co.edu.udea.infocovid.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
@Service
@Transactional
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final Messages messages;
    @Autowired
    private JavaMailSender mailSender;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, Messages messages) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.messages = messages;
    }

    public Usuario guardarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioConsulta = usuarioRepository.findByCorreo(usuario.getCorreo());
        if(usuarioConsulta.isPresent()){
            throw new BusinessException(messages.get("usuario.correo.duplicado"));
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        if (Objects.isNull(usuario.getId())) {
            throw new BusinessException(messages.get("usuario.id.vacio"));
        }
        consultarPorId(usuario.getId());
        return usuarioRepository.save(usuario);
    }


    public void eliminarUsuario(Long id) {
        consultarPorId(id);
        usuarioRepository.deleteById(id);
    }

    public Usuario consultarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new BusinessException(messages.get("usuario.id.no_encontrado")));
    }

    public Usuario buscarUsuario(String correo, String contrasena) {
        Usuario usuarioConsulta = usuarioRepository.findByCorreo(correo).orElseThrow(
                () -> new BusinessException(messages.get("usuario.correo.no_encontrado")));
        if (!usuarioConsulta.getContrasena().equals(contrasena)) {
            throw new BusinessException(messages.get("usuario.contrasena.no_valida"));
        }
        return usuarioConsulta;
    }

    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new BusinessException(messages.get("usuario.correo.no_encontrado")));
    }
    
    public void reenviarClavePorEmail(String correo) {
        SimpleMailMessage message = new SimpleMailMessage();
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
        if (!usuario.isPresent()) {
            throw new BusinessException(messages.get("recuperar.clave.error.usuario.no.existe"));
        }
        if (Objects.nonNull(usuario.get().getContrasena())) {
            message.setFrom(correo);
            message.setTo(correo);
            message.setSubject ("Asunto: Recupera tu contraseña");
            message.setText ("Tu contraseña para iniciar sesión en INFOVID es: " + usuario.get().getContrasena());

            mailSender.send(message);
        }
    }
}
