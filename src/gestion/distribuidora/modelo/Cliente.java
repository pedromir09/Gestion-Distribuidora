/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion.distribuidora.modelo;

/**
 *
 * @author Administrador
 */
public class Cliente {

    private int id;                // número entero, automático (lo da la base)
    private String nombreApellido; // texto
    private String direccion;      // texto
    private String cuit;           // lo guardamos como texto por los guiones (-)
    private String telefono;       // este tambien tiene guiones y espacios ejemplo 223 456-7890 

    //Constructor: recibe los 5 datos
    public Cliente(int id, String nombreApellido, String direccion, String cuit, String telefono) {
        this.id = id;
        this.nombreApellido = nombreApellido;
        this.direccion = direccion;
        this.cuit = cuit;
        this.telefono = telefono;
    }
    //Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
