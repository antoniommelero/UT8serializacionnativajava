/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package principal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import gestion.GestionCliente;
import modelo.Cliente;

/**
 *
 * @author Antonio
 */
public class AplicacionClientes {

    /**
     * @param args the command line arguments
     */
    private static GestionCliente gestion = new GestionCliente();
    private static Scanner teclado = new Scanner(System.in);
    private static final String ARCHIVO_DATOS = "clientes.dat";

    public static void main(String[] args) {
        cargarDatos();
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero();
            switch (opcion) {
                case 1 ->
                    agregarCliente();
                case 2 ->
                    listarClientes();
                case 3 ->
                    borrarCliente();
                case 4 ->
                    exportarListado();
                case 5 -> {
                    guardarDatos();
                    System.out.println("Saliendo del sistema");
                }
                default ->
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 5);

        teclado.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n   Menu de opciones");
        System.out.println("1. Agregar cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Borrar cliente");
        System.out.println("4. Exportar listado a archivo");
        System.out.println("5. Salir");
        System.out.print("Elige una opcion: ");
    }

    private static int leerEntero() {
        while (!teclado.hasNextInt()) {
            System.out.print("Entrada no valida. Introduce un numero: ");
            teclado.next();
        }
        int valor = teclado.nextInt();
        teclado.nextLine();
        return valor;
    }

    private static void agregarCliente() {
        System.out.println("\nNuevo cliente");

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Telefono: ");
        String telefono = teclado.nextLine();

        System.out.print("Email: ");
        String email = teclado.nextLine();

        Cliente nuevo = new Cliente(nombre, telefono, email);

        if (gestion.agregarCliente(nuevo)) {
            System.out.println("Cliente agregado correctamente.");
        } else {
            System.out.println("Ya existe un cliente con ese email.");
        }
    }

    private static void listarClientes() {
        System.out.println("\n Listado de clientes");
        System.out.println(gestion.toString());
    }

    private static void borrarCliente() {
        if (gestion.getNumeroClientes() == 0) {
            System.out.println("No hay clientes para borrar.");
            return;
        }
        System.out.println("\nEliminar cliente");
        System.out.println("Seleccione el cliente a eliminar:");
        ArrayList<Cliente> clientes = gestion.getClientes();
        for (int i = 0; i < clientes.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, clientes.get(i).toString());
        }
        System.out.print("Numero de cliente a borrar (0 para cancelar): ");
        int opcion = leerEntero();
        if (opcion == 0) {
            System.out.println("Operacion cancelada.");
            return;
        }

        int indice = opcion - 1;

        if (gestion.borrarCliente(indice)) {
            System.out.println("Cliente eliminado correctamente.");
        } else {
            System.out.println("Numero de cliente no valido.");
        }
    }

    private static void exportarListado() {
        System.out.println("\nListado a archivo");
        System.out.print("Nombre del archivo de salida: ");
        String nombreArchivo = teclado.nextLine();

        try (PrintWriter listado = new PrintWriter(nombreArchivo)) {
            listado.println("Listado de clientes");
            listado.println("-------------------");
            listado.println(String.format("%-20s %-9s %-30s", 
                "Nombre", "Telefono", "email"));
            listado.println(String.format("%-20s %-9s %-30s", 
                "-".repeat(20), "-".repeat(9), "-".repeat(20)));

            for (Cliente cliente : gestion.getClientes()) {
                listado.printf("%s%n", cliente.toString());
            }

            System.out.println("Listado exportado correctamente a: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al exportar el listado: " + e.getMessage());
        }
    }

    private static void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);
        if (archivo.exists()) {
            try (ObjectInputStream ficheroDatos = new ObjectInputStream(new FileInputStream(archivo))) {
                gestion = (GestionCliente) ficheroDatos.readObject();
                System.out.println("Datos anteriores cargados correctamente.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar el archivo. Iniciando con datos nuevos.");
                gestion = new GestionCliente();
            }
        } else {
            gestion = new GestionCliente();
            System.out.println("No existe archivo de guardado. Iniciando con datos nuevos.");
        }
    }

    private static void guardarDatos() {
        try (ObjectOutputStream ficheroDatos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
            ficheroDatos.writeObject(gestion);
            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

}
