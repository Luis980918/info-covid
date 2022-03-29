package co.edu.udea.infocovid.controller;

import co.edu.udea.infocovid.facade.UsuarioFacade;
import co.edu.udea.infocovid.mapper.UsuarioMapper;
import co.edu.udea.infocovid.modelo.UsuarioDTO;
import co.edu.udea.infocovid.util.Messages;
import co.edu.udea.infocovid.util.StandardResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioFacade usuarioFacade;
    private final UsuarioMapper usuarioMapper;
    private final Messages messages;

    public UsuarioController(UsuarioFacade usuarioFacade, UsuarioMapper usuarioMapper, Messages messages) {
        this.usuarioFacade = usuarioFacade;
        this.usuarioMapper = usuarioMapper;
        this.messages = messages;
    }

    @PostMapping()
    @ApiOperation(value = "Permite crear un usuario", response = UsuarioDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Se guardó el usuario exitosamente"),
            @ApiResponse(code = 400, message = "La petición es inválida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<StandardResponse<UsuarioDTO>> guardarUsuario(@RequestBody UsuarioDTO user) {
        UsuarioDTO userDto = usuarioFacade.crearUsuario(user);
        return ResponseEntity.ok(new StandardResponse<>(StandardResponse.StatusStandardResponse.OK, userDto));    
    }

    @PutMapping("actualizar-usuario")
    @ApiOperation(value = "Permite actualizar un usuario", response = UsuarioDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Se actualizó el usuario exitosamente"),
            @ApiResponse(code = 400, message = "La petición es inválida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<StandardResponse<UsuarioDTO>> actualizarUsuario(@RequestBody UsuarioDTO user) {
        UsuarioDTO userDto = usuarioFacade.actualizar(user);
        return ResponseEntity.ok(new StandardResponse<>(StandardResponse.StatusStandardResponse.OK, userDto));
    }

    @GetMapping("iniciar-sesion/{correo}/{contrasena}")
    @ApiOperation(value = "Permite verificar la información del usuario para iniciar sesión", response = UsuarioDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sesión iniciada correctamente"),
            @ApiResponse(code = 400, message = "La petición es inválida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<StandardResponse<UsuarioDTO>> buscarUsuarioPorCorreo(@PathVariable String correo, @PathVariable String contrasena) {
        UsuarioDTO usuarioDTO = usuarioFacade.verificarInformacionInicioSesion(correo, contrasena);
        return ResponseEntity.ok(new StandardResponse<>(StandardResponse.StatusStandardResponse.OK, usuarioDTO));
    }

    @PostMapping("recuperar-clave/{correo}")
    @ApiOperation(value = "Permite recuperar la clave establecida por el usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Se envió el correo de verificación correctamente"),
            @ApiResponse(code = 400, message = "La petición es inválida"),
            @ApiResponse(code = 500, message = "Error interno al procesar la respuesta")})
    public ResponseEntity<StandardResponse> reenviarClavePorCorreo(@PathVariable String correo) {
        try {
            usuarioFacade.reenviarClavePorEmail(correo);
            return ResponseEntity.ok(new StandardResponse<>(StandardResponse.StatusStandardResponse.OK));
        }catch (Exception e) {
            return new ResponseEntity<>(new StandardResponse<>(StandardResponse.StatusStandardResponse.ERROR,
                    e.getMessage()), HttpStatus.CONFLICT);
        }
    }
    
}
