package com.ecys.entidades;

/**
 *
 * @author ecys
 */
public class DetalleAlquiler {

    private String codDetalleAlquiler;
    private String codAlquiler;
    private String codEquipo;
    private String nombreEquipo;
    private Double precio;
    private Double horas;
    private Double subTotal;
    private String estadoDetAlq;

    public DetalleAlquiler() {
    }

    public DetalleAlquiler(String codDetalleAlquiler, String codAlquiler, String codEquipo, Double precio, Double horas, Double subTotal, String estadoDetAlq) {
        this.codDetalleAlquiler = codDetalleAlquiler;
        this.codAlquiler = codAlquiler;
        this.codEquipo = codEquipo;
        this.precio = precio;
        this.horas = horas;
        this.subTotal = subTotal;
        this.estadoDetAlq = estadoDetAlq;
    }

    public String getCodDetalleAlquiler() {
        return codDetalleAlquiler;
    }

    public String getCodAlquiler() {
        return codAlquiler;
    }

    public String getCodEquipo() {
        return codEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public Double getPrecio() {
        return precio;
    }

    public Double getHoras() {
        return horas;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public String getEstadoDetAlq() {
        return estadoDetAlq;
    }

    public void setCodDetalleAlquiler(String codDetalleAlquiler) {
        this.codDetalleAlquiler = codDetalleAlquiler;
    }

    public void setCodAlquiler(String codAlquiler) {
        this.codAlquiler = codAlquiler;
    }

    public void setCodEquipo(String codEquipo) {
        this.codEquipo = codEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public void setEstadoDetAlq(String estadoDetAlq) {
        this.estadoDetAlq = estadoDetAlq;
    }

    @Override
    public String toString() {
        return "DetalleAlquiler{" + "codDetalleAlquiler=" + codDetalleAlquiler + ", codAlquiler=" + codAlquiler + ", codEquipo=" + codEquipo + ", nombreEquipo=" + nombreEquipo + ", precio=" + precio + ", horas=" + horas + ", subTotal=" + subTotal + ", estadoDetAlq=" + estadoDetAlq + '}';
    }

}
