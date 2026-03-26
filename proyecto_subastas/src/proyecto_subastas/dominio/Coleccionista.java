package proyecto_subastas.dominio;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase que representa a un coleccionista registrado en la plataforma de subastas.
 * <p>
 * El coleccionista es el único tipo de usuario que puede realizar ofertas en subastas.
 * También puede crear subastas, pero únicamente con objetos que estén registrados
 * en su colección personal. No puede ofertar en subastas que él mismo haya creado.
 * </p>
 *
 * @author Steven Mendez Jimenez
 * @version 2.0
 * @see Usuario
 * @see ObjetoSubasta
 */
public class Coleccionista extends Usuario {

    /** Puntuación de reputación del coleccionista dentro de la plataforma. */
    private double puntuacion;

    /** Dirección de domicilio del coleccionista. */
    private String direccion;

    /** Lista de categorías o temas de interés del coleccionista. */
    private ArrayList<String> intereses;

    /** Colección de objetos que pertenecen al coleccionista. */
    private ArrayList<ObjetoSubasta> coleccion;

    /**
     * Constructor por defecto.
     * Crea una instancia de Coleccionista con puntuación en 0 y listas vacías.
     */
    public Coleccionista() {
        this.puntuacion = 0;
        this.intereses = new ArrayList<>();
        this.coleccion = new ArrayList<>();
    }

    /**
     * Constructor completo que inicializa todos los atributos del coleccionista.
     *
     * @param nombreCompleto    Nombre completo del coleccionista.
     * @param identificacion    Número de identificación del coleccionista.
     * @param fechaNacimiento   Fecha de nacimiento del coleccionista.
     * @param contrasena        Contraseña de acceso a la plataforma.
     * @param correoElectronico Correo electrónico del coleccionista.
     * @param puntuacion        Puntuación de reputación inicial.
     * @param direccion         Dirección de domicilio del coleccionista.
     */
    public Coleccionista(String nombreCompleto, String identificacion,
                         LocalDate fechaNacimiento, String contrasena, String correoElectronico,
                         double puntuacion, String direccion) {
        super(nombreCompleto, identificacion, fechaNacimiento, contrasena, correoElectronico);
        this.puntuacion = puntuacion;
        this.direccion = direccion;
        this.intereses = new ArrayList<>();
        this.coleccion = new ArrayList<>();
    }

    // ── Getters / Setters

    public double getPuntuacion() { return puntuacion; }
    public void setPuntuacion(double puntuacion) { this.puntuacion = puntuacion; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public ArrayList<String> getIntereses() { return intereses; }
    public void setIntereses(ArrayList<String> intereses) { this.intereses = intereses; }

    public ArrayList<ObjetoSubasta> getColeccion() { return coleccion; }
    public void setColeccion(ArrayList<ObjetoSubasta> coleccion) { this.coleccion = coleccion; }

    /**
     * Agrega un objeto a la colección personal del coleccionista.
     *
     * @param objeto Objeto a agregar a la colección.
     */
    public void agregarAColeccion(ObjetoSubasta objeto) {
        this.coleccion.add(objeto);
    }

    // ── equals

    /**
     * Dos coleccionistas son iguales si comparten la misma identificación.
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si la identificación coincide.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    // ── toString

    /**
     * Retorna una representación textual del coleccionista con sus datos completos.
     *
     * @return Cadena con el prefijo [COLECCIONISTA], datos del usuario, puntuación y dirección.
     */
    @Override
    public String toString() {
        return "[COLECCIONISTA] " + super.toString()
                + " | Puntuación: " + puntuacion
                + " | Dirección: " + direccion;
    }
}
