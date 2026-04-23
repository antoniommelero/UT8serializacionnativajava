/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;


/**
 *
 * @author Antonio
 */
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String telefono;
    private String email;
    
    /**
     * Constructor por defecto necesario para la serialización.
     */
    public Cliente() {
    }
    
    public Cliente(String nombre, String telefono, String email) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }
    
    // Getters
    public String getNombre() {
        return nombre;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return String.format("%-20s %-9s %-30s", nombre, telefono, email);
    }
}
