/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;

import dao.ClienteDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import modelo.Cliente;


/**
 *
 * @author Antonio
 */
public class GestionCliente implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Cliente> clientes;
    
    // transient es para que no se serialice (no es un dato de dominio)
    private transient ClienteDAO dao;
    
    // constructor con inyección de dependencias
    public GestionCliente(ClienteDAO dao) {
        this.dao = dao;
        this.clientes = new ArrayList<>();
    }
    public GestionCliente() {
        this.clientes = new ArrayList<>();
    }
    
    // como el atributo es transient es necesario para reasignar tras deserializacion
    public void setDao(ClienteDAO dao) {
        this.dao = dao;
    }
    
    // se mantiene el método pero delega la existencia en el DAO inyectado
    public void guardarDatos(String rutaArchivo) {
        if (dao != null) {
            dao.guardarDatos(this, rutaArchivo);
        } else {
            System.err.println("No se puede guardar: DAO no inicializado.");
        }
    }
    public boolean agregarCliente(Cliente cliente) {
        if (buscarPorEmail(cliente.getEmail()) == -1) {
            clientes.add(cliente);
            return true;
        }
        return false;
    }
    
    public int buscarPorEmail(String email) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getEmail().equalsIgnoreCase(email)) {
                return i;
            }
        }
        return -1;
    }
    
    public Cliente buscarPorIndice(int indice) {
        if (indice >= 0 && indice < clientes.size()) {
            return clientes.get(indice);
        }
        return null;
    }
    
    public boolean borrarCliente(int indice) {
        if (indice >= 0 && indice < clientes.size()) {
            clientes.remove(indice);
            return true;
        }
        return false;
    }
    
    public int getNumeroClientes() {
        return clientes.size();
    }
    
    @Override
    public String toString() {
        if (clientes.isEmpty()) {
            return "No hay clientes registrados.";
        }
        
        StringBuilder salida = new StringBuilder();
        salida.append(String.format("%-20s %-9s %-30s%n", 
                "Nombre", "Telefono", "email"));
        salida.append(String.format("%-20s %-9s %-30s%n", 
                "-".repeat(20), "-".repeat(9), "-".repeat(20)));
        
        for (Cliente cliente : clientes) {
            salida.append(cliente.toString()).append("\n");
        }
        
        return salida.toString();
    }
    
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    
}
