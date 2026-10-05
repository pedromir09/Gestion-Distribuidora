/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion.distribuidora.modelo;

/**
 *
 * @author Administrador
 */
public class Producto {

    private String codigo;  // el código del producto
    private String nombre;  // el nombre del producto 
    private int stock;      // cantidad de unidades disponibles 
    private double precio;  // precio de venta 

// Constructor: recibe los 4 datos y arma el producto
    public Producto(String codigo, String nombre, int stock, double precio) {
        this.codigo = codigo;   // guardo el código recibido en el atributo del objeto
        this.nombre = nombre;   // ídem con el nombre
        this.stock = stock;     // ídem con el stock
        this.precio = precio;   // ídem con el precio
    }
//Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCodigo() {   // puerta de salida: devuelve el código
        return codigo;
    }

    public void setCodigo(String codigo) {   // puerta de entrada: cambia el código
        this.codigo = codigo;
    }
}
