package com.ecys.entidades;

/**
 *
 * @author ecys
 */
public class Equipo {

    private String codEquipo;
    private String nombre;
    private String marca;
    private String modelo;
    private String placa;
    private String categoria;
    private Double horometro;
    private Integer kilometraje;
    private String anioFabricacion;    
    private String operativo;
    private String observaciones;
    private String descripcion;
    private String estadoEquipo;

    public Equipo() {
    }

    public String getCodEquipo() {
        return codEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getCategoria() {
        return categoria;
    }

    public Double getHorometro() {
        return horometro;
    }

    public Integer getKilometraje() {
        return kilometraje;
    }

    public String getAnioFabricacion() {
        return anioFabricacion;
    }

    public String getEstadoEquipo() {
        return estadoEquipo;
    }

    public String getOperativo() {
        return operativo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setCodEquipo(String codEquipo) {
        this.codEquipo = codEquipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setHorometro(Double horometro) {
        this.horometro = horometro;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    public void setAnioFabricacion(String anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }

    public void setEstadoEquipo(String estadoEquipo) {
        this.estadoEquipo = estadoEquipo;
    }

    public void setOperativo(String operativo) {
        this.operativo = operativo;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Equipo{" + "codEquipo=" + codEquipo + ", nombre=" + nombre + ", marca=" + marca + ", modelo=" + modelo + ", placa=" + placa + ", categoria=" + categoria + ", horometro=" + horometro + ", kilometraje=" + kilometraje + ", anioFabricacion=" + anioFabricacion + ", estadoEquipo=" + estadoEquipo + ", operativo=" + operativo + ", observaciones=" + observaciones + ", descripcion=" + descripcion + '}';
    }

}
