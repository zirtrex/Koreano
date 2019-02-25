package com.ecys.entidades;

/**
 *
 * @author ecys
 */
public class Contratista extends DetalleEmpresa {

    private String codContratista;
    private String nombreEncargado;
    private String tipoEmpresa;
    private String estadoContratista;

    public Contratista() {
    }

    public Contratista(String codContratista, String nombreEncargado, String tipoEmpresa, String estadoContratista, String codDetalleEmpresa, String razonSocial, String ruc, String telefono, String email, String direccion, String estadoDetEmp) {
        super(codDetalleEmpresa, razonSocial, ruc, telefono, email, direccion, estadoDetEmp);
        this.codContratista = codContratista;
        this.nombreEncargado = nombreEncargado;
        this.tipoEmpresa = tipoEmpresa;
        this.estadoContratista = estadoContratista;
   }    

    public String getCodContratista() {
        return codContratista;
    }

    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public String getTipoEmpresa() {
        return tipoEmpresa;
    }

    public String getEstadoContratista() {
        return estadoContratista;
    }

    public void setCodContratista(String codContratista) {
        this.codContratista = codContratista;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }

    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public void setEstadoContratista(String estadoContratista) {
        this.estadoContratista = estadoContratista;
    }

    @Override
    public String toString() {
        return super.toString() + ", Contratista{" + "codContratista=" + codContratista + ", tipoEmpresa=" + tipoEmpresa + ", estadoContratista=" + estadoContratista + '}';
    }

}