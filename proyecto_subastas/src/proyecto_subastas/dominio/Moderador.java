package proyecto_subastas.dominio;

import java.time.LocalDate;

/**
 * Clase que representa al moderador de la plataforma de subastas.
 * <p>
 * Solo puede existir <b>un único moderador</b> registrado. Por las reglas de negocio,
 * el moderador no puede crear subastas ni realizar ofertas en la plataforma.
 * Debe ser mayor de edad para registrarse.
 * </p>
 *
 * @author Steven Mendez Jimenez
 * @version 2.0
 * @see Usuario
 */
public class Moderador extends Usuario {

    /** Constructor por defecto. */
    public Moderador() {}

    /**
     * Constructor completo que inicializa todos los atributos del moderador.
     *
     * @param nombreCompleto    Nombre completo del moderador.
     * @param identificacion    Número de identificación del moderador.
     * @param fechaNacimiento   Fecha de nacimiento del moderador.
     * @param contrasena        Contraseña de acceso a la plataforma.
     * @param correoElectronico Correo electrónico del moderador.
     */
    public Moderador(String nombreCompleto, String identificacion,
                     LocalDate fechaNacimiento, String contrasena, String correoElectronico) {
        super(nombreCompleto, identificacion, fechaNacimiento, contrasena, correoElectronico);
    }

    /**
     * Dos moderadores son iguales si comparten la misma identificación.
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si la identificación coincide.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Retorna una representación textual del moderador.
     *
     * @return Cadena con el prefijo [MODERADOR] seguido de los datos del usuario.
     */
    @Override
    public String toString() {
        return "[MODERADOR] " + super.toString();
    }
}
