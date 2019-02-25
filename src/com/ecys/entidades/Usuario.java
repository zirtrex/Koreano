package com.ecys.entidades;

import com.ecys.negocio.BolUsuario;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ecys
 */
public class Usuario {

    private String codUsuario;
    private String codDetallePersona;
    private String nombreUsuario;
    private String claveUsuario;
    private String rol;
    private String estadoUsuario;

    private static final Map<String, Usuario> USERS = new HashMap<String, Usuario>();

   public static Usuario of(String nombreUsuario) {
        Usuario user = USERS.get(nombreUsuario);
        if (user == null) {
            BolUsuario bolUsuario = new BolUsuario();           
            user = bolUsuario.fetchUsuario(nombreUsuario);
            USERS.put(nombreUsuario, user);
        }
        return user;
    }

    private Usuario(String username) {
        this.nombreUsuario = username;
    }

    public Usuario() {
    }

    public Usuario(String codUsuario, String codDetallePersona, String nombreUsuario, String claveUsuario, String rol, String estadoUsuario) {
        this.codUsuario = codUsuario;
        this.codDetallePersona = codDetallePersona;
        this.nombreUsuario = nombreUsuario;
        this.claveUsuario = claveUsuario;
        this.rol = rol;
        this.estadoUsuario = estadoUsuario;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public String getCodDetallePersona() {
        return codDetallePersona;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public String getRol() {
        return rol;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public void setCodDetallePersona(String codDetallePersona) {
        this.codDetallePersona = codDetallePersona;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" + "codUsuario=" + codUsuario + ", codDetallePersona=" + codDetallePersona + ", nombreUsuario=" + nombreUsuario + ", claveUsuario=" + claveUsuario + ", rol=" + rol + ", estadoUsuario=" + estadoUsuario + '}';
    }

}
