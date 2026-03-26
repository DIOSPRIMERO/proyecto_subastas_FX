package proyecto_subastas.dominio;

import java.time.LocalDate;
import java.time.Period;

/**
 * Clase abstracta que representa un usuario genérico dentro de la plataforma de subastas.
 * <p>
 * Todo usuario posee datos básicos de identificación y acceso.
 * Las subclases concretas ({@link Moderador}, {@link Vendedor}, {@link Coleccionista})
 * amplían esta clase con atributos y comportamientos específicos según su rol.
 * </p>
 *
 * @author Steven Mendez Jimenez
 * @version 2.0
 */
public abstract class Usuario {

    /** Nombre completo del usuario. */
    private String nombreCompleto;

    /** Número de identificación único del usuario. */
    private String identificacion;

    /**
     * Fecha de nacimiento completa del usuario (día, mes, año).
     * Se usa {@link LocalDate} para calcular la edad exacta.
     */
    private LocalDate fechaNacimiento;

    /** Contraseña de acceso a la plataforma. */
    private String contrasena;

    /** Correo electrónico del usuario. */
    private String correoElectronico;

    /** Constructor por defecto. */
    public Usuario() {}

    /**
     * Constructor completo que inicializa todos los atributos del usuario.
     *
     * @param nombreCompleto    Nombre completo del usuario.
     * @param identificacion    Número de identificación del usuario.
     * @param fechaNacimiento   Fecha de nacimiento completa.
     * @param contrasena        Contraseña de acceso.
     * @param correoElectronico Correo electrónico del usuario.
     */
    public Usuario(String nombreCompleto, String identificacion,
                   LocalDate fechaNacimiento, String contrasena, String correoElectronico) {
        this.nombreCompleto = nombreCompleto;
        this.identificacion = identificacion;
        this.fechaNacimiento = fechaNacimiento;
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
    }

    /**
     * Calcula la edad exacta del usuario en años completos a partir de su fecha de nacimiento.
     *
     * @return Edad en años.
     */
    public int calcularEdad() {
        if (fechaNacimiento == null) return 0;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    /**
     * Verifica si el usuario es mayor de edad (18 años o más).
     *
     * @return {@code true} si la edad calculada es mayor o igual a 18.
     */
    public boolean esMayorDeEdad() {
        return calcularEdad() >= 18;
    }

    // ── Getters / Setters

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    // ── equals

    /**
     * Dos usuarios son iguales si comparten el mismo número de identificación.
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si la identificación coincide.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Usuario)) return false;
        Usuario otro = (Usuario) obj;
        return this.identificacion != null && this.identificacion.equals(otro.identificacion);
    }

    // ── toString

    /**
     * Retorna una representación textual del usuario con sus datos principales.
     *
     * @return Cadena con nombre, identificación, edad y correo.
     */
    @Override
    public String toString() {
        return "Nombre: " + nombreCompleto
                + " | ID: " + identificacion
                + " | Edad: " + calcularEdad() + " años"
                + " | Correo: " + correoElectronico;
    }
}
