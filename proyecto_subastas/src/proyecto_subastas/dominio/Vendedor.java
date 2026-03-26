package proyecto_subastas.dominio;

import java.time.LocalDate;

/**
 * Clase que representa a un vendedor registrado en la plataforma de subastas.
 * <p>
 * El vendedor puede crear subastas con sus objetos, pero <b>no puede realizar ofertas</b>.
 * Debe ser mayor de edad para registrarse.
 * </p>
 *
 * @author Steven Mendez Jimenez
 * @version 2.0
 * @see Usuario
 */
public class Vendedor extends Usuario {

    /** Puntuación de reputación del vendedor dentro de la plataforma. */
    private double puntuacion;

    /** Dirección de domicilio del vendedor. */
    private String direccion;

    /**
     * Constructor por defecto.
     * Crea una instancia de Vendedor con puntuación inicial en 0.
     */
    public Vendedor() {
        this.puntuacion = 0;
    }

    /**
     * Constructor completo que inicializa todos los atributos del vendedor.
     *
     * @param nombreCompleto    Nombre completo del vendedor.
     * @param identificacion    Número de identificación del vendedor.
     * @param fechaNacimiento   Fecha de nacimiento del vendedor.
     * @param contrasena        Contraseña de acceso a la plataforma.
     * @param correoElectronico Correo electrónico del vendedor.
     * @param puntuacion        Puntuación de reputación inicial del vendedor.
     * @param direccion         Dirección de domicilio del vendedor.
     */
    public Vendedor(String nombreCompleto, String identificacion,
                    LocalDate fechaNacimiento, String contrasena, String correoElectronico,
                    double puntuacion, String direccion) {
        super(nombreCompleto, identificacion, fechaNacimiento, contrasena, correoElectronico);
        this.puntuacion = puntuacion;
        this.direccion = direccion;
    }

    // ── Getters / Setters

    public double getPuntuacion() { return puntuacion; }
    public void setPuntuacion(double puntuacion) { this.puntuacion = puntuacion; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    // ── equals

    /**
     * Dos vendedores son iguales si comparten la misma identificación.
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
     * Retorna una representación textual del vendedor con sus datos completos.
     *
     * @return Cadena con el prefijo [VENDEDOR], los datos del usuario, puntuación y dirección.
     */
    @Override
    public String toString() {
        return "[VENDEDOR] " + super.toString()
                + " | Puntuación: " + puntuacion
                + " | Dirección: " + direccion;
    }
}
