package co.edu.udea.infocovid.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombreCompleto;

    @Column(name = "correo", nullable = false, length = 100, unique = true)
    private String correo;

    @Column(name = "contrasena", nullable = false, length = 50)
    private String contrasena;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "celular")
    private String celular;

    @Column(name = "fk_ciudad_cliente")
    private Long fkCiudadCliente;

    @Column(name = "nombre_de_usuario")
    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_ciudad_cliente", insertable = false, updatable = false)
    private Ciudad ciudadCliente;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Suscripcion> usuarioList = new ArrayList<Suscripcion>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Long getFkCiudadCliente() {
        return fkCiudadCliente;
    }

    public void setFkCiudadCliente(Long fkCiudadCliente) {
        this.fkCiudadCliente = fkCiudadCliente;
    }

    public Ciudad getCiudadCliente() {
        return ciudadCliente;
    }

    public void setCiudadCliente(Ciudad ciudadCliente) {
        this.ciudadCliente = ciudadCliente;
    }

    public List<Suscripcion> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Suscripcion> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
