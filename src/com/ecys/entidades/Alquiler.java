package com.ecys.entidades;

import java.sql.Date;

/**
 *
 * @author ecys
 */
public class Alquiler {

    private String codAlquiler;
    private String codContratista;
    private String contratista;
    private String codUsuario;
    private Date fecha;
    private String tipoDocumento;
    private Integer numeroDocumento;
    private Integer numeroSerie;
    private Double subTotal;
    private Double igv;
    private Double total;
    private String estadoAlquiler;

    public Alquiler() {
    }

    public Alquiler(String codAlquiler, String codContratista, String contratista, String codUsuario, Date fecha, String tipoDocumento, Integer numeroDocumento, Integer numeroSerie, Double subTotal, Double igv, Double total, String estadoAlquiler) {
        this.codAlquiler = codAlquiler;
        this.codContratista = codContratista;
        this.contratista = contratista;
        this.codUsuario = codUsuario;
        this.fecha = fecha;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.numeroSerie = numeroSerie;
        this.subTotal = subTotal;
        this.igv = igv;
        this.total = total;
        this.estadoAlquiler = estadoAlquiler;
    }

    public String getCodAlquiler() {
        return codAlquiler;
    }

    public String getCodContratista() {
        return codContratista;
    }

    public String getContratista() {
        return contratista;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public Integer getNumeroDocumento() {
        return numeroDocumento;
    }

    public Integer getNumeroSerie() {
        return numeroSerie;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public Double getIgv() {
        return igv;
    }

    public Double getTotal() {
        return total;
    }

    public String getEstadoAlquiler() {
        return estadoAlquiler;
    }

    public void setCodAlquiler(String codAlquiler) {
        this.codAlquiler = codAlquiler;
    }

    public void setCodContratista(String codContratista) {
        this.codContratista = codContratista;
    }

    public void setContratista(String contratista) {
        this.contratista = contratista;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setNumeroDocumento(Integer numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setNumeroSerie(Integer numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setEstadoAlquiler(String estadoAlquiler) {
        this.estadoAlquiler = estadoAlquiler;
    }

    @Override
    public String toString() {
        return "Alquiler{" + "codAlquiler=" + codAlquiler + ", codContratista=" + codContratista + ", contratista=" + contratista + ", codUsuario=" + codUsuario + ", fecha=" + fecha + ", tipoDocumento=" + tipoDocumento + ", numeroDocumento=" + numeroDocumento + ", numeroSerie=" + numeroSerie + ", subTotal=" + subTotal + ", igv=" + igv + ", total=" + total + ", estadoAlquiler=" + estadoAlquiler + '}';
    }

}
