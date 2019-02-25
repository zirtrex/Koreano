package com.ecys.entidades;

/**
 *
 * @author ecys
 */
public class DetallePersona {

    private String codDetallePersona;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String sexo;
    private String telefono;
    private String email;
    private String fechaIngreso;
    private String experiencia;
    private String estadoDetPer;

    public DetallePersona() {
    }

    public DetallePersona(String codDetallePersona, String nombres, String apellidos, String direccion, String sexo, String telefono, String email, String fechaIngreso, String experiencia, String estadoDetPer) {
        this.codDetallePersona = codDetallePersona;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.sexo = sexo;
        this.telefono = telefono;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.experiencia = experiencia;
        this.estadoDetPer = estadoDetPer;
    }

    public String getCodDetallePersona() {
        return codDetallePersona;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getSexo() {
        return sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public String getEstadoDetPer() {
        return estadoDetPer;
    }

    public void setCodDetallePersona(String codDetallePersona) {
        this.codDetallePersona = codDetallePersona;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public void setEstadoDetPer(String estadoDetPer) {
        this.estadoDetPer = estadoDetPer;
    }

    @Override
    public String toString() {
        return "DetallePersona{" + "codDetallePersona=" + codDetallePersona + ", nombres=" + nombres + ", apellidos=" + apellidos + ", direccion=" + direccion + ", sexo=" + sexo + ", telefono=" + telefono + ", email=" + email + ", fechaIngreso=" + fechaIngreso + ", experiencia=" + experiencia + ", estadoDetPer=" + estadoDetPer + '}';
    }

}
