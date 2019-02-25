package com.ecys.entidades;

/**
 *
 * @author ecys
 */
public class DetalleEmpresa {

    private String codDetalleEmpresa;
    private String razonSocial;
    private String ruc;
    private String telefono;
    private String email;
    private String direccion;
    private String estadoDetEmp;

    public DetalleEmpresa() {
    }

    public DetalleEmpresa(String codDetalleEmpresa, String razonSocial, String ruc, String telefono, String email, String direccion, String estadoDetEmp) {
        this.codDetalleEmpresa = codDetalleEmpresa;
        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.estadoDetEmp = estadoDetEmp;
    }

    public String getCodDetalleEmpresa() {
        return codDetalleEmpresa;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEstadoDetEmp() {
        return estadoDetEmp;
    }

    public void setCodDetalleEmpresa(String codDetalleEmpresa) {
        this.codDetalleEmpresa = codDetalleEmpresa;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEstadoDetEmp(String estadoDetEmp) {
        this.estadoDetEmp = estadoDetEmp;
    }

    @Override
    public String toString() {
        return "DetalleEmpresa{" + "codDetalleEmpresa=" + codDetalleEmpresa + ", razonSocial=" + razonSocial + ", ruc=" + ruc + ", telefono=" + telefono + ", email=" + email + ", direccion=" + direccion + ", estadoDetEmp=" + estadoDetEmp + '}';
    }

}
