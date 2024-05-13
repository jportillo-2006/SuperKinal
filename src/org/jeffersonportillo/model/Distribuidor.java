/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jeffersonportillo.model;

public class Distribuidor {
    private int distribuidorId;
    private String nombre;
    private String direccion;
    private String nit;
    private String telefono;
    private String web;

    public Distribuidor() {
    }

    public Distribuidor(int distribuidorId, String nombre, String direccion, String nit, String telefono, String web) {
        this.distribuidorId = distribuidorId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.nit = nit;
        this.telefono = telefono;
        this.web = web;
    }
    
    public int getDistribuidorId() {
        return distribuidorId;
    }

    public void setDistribuidorId(int distribuidorId) {
        this.distribuidorId = distribuidorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Override
    public String toString() {
        return "{Id: " + distribuidorId + '}' + " { Nombre: " + nombre + '}' +" { Web: " + web + '}';
    }   
}
