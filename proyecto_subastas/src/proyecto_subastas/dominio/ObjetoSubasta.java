package proyecto_subastas.dominio;

import java.time.LocalDate;
import java.time.Period;

/**
 * Clase que representa un objeto ofrecido dentro de una subasta en la plataforma.
 * <p>
 * Cada objeto tiene un nombre, descripción, estado de conservación y fecha de compra.
 * La antigüedad se calcula de forma exacta en años, meses y días a partir de la fecha de compra.
 * </p>
 *
 * <p>Los estados posibles de un objeto son:</p>
 * <ul>
 *   <li><b>Nuevo</b>: Objeto sin uso.</li>
 *   <li><b>Usado</b>: Objeto que ha sido utilizado.</li>
 *   <li><b>Antiguo sin abrir</b>: Objeto antiguo que no ha sido abierto o usado.</li>
 * </ul>
 *
 * @author Steven Mendez Jimenez
 * @version 2.0
 */
public class ObjetoSubasta {

    /** Nombre descriptivo del objeto. */
    private String nombre;

    /** Descripción detallada del objeto. */
    private String descripcion;

    /** Estado de conservación del objeto. */
    private String estado;

    /** Fecha exacta en la que fue comprado el objeto (día, mes, año). */
    private LocalDate fechaCompra;

    /** Constructor por defecto. */
    public ObjetoSubasta() {}

    /**
     * Constructor completo que inicializa todos los atributos del objeto.
     *
     * @param nombre      Nombre del objeto.
     * @param descripcion Descripción detallada del objeto.
     * @param estado      Estado de conservación ("Nuevo", "Usado", "Antiguo sin abrir").
     * @param fechaCompra Fecha exacta en que fue adquirido el objeto.
     */
    public ObjetoSubasta(String nombre, String descripcion, String estado, LocalDate fechaCompra) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCompra = fechaCompra;
    }

    /**
     * Calcula la antigüedad exacta del objeto como un {@link Period}
     * que incluye años, meses y días desde la fecha de compra hasta hoy.
     *
     * @return {@link Period} con la antigüedad exacta, o {@code Period.ZERO} si la fecha es nula.
     */
    public Period calcularAntiguedad() {
        if (fechaCompra == null) return Period.ZERO;
        return Period.between(fechaCompra, LocalDate.now());
    }

    /**
     * Retorna la antigüedad del objeto como cadena legible: "X año(s), Y mes(es), Z día(s)".
     *
     * @return Cadena con la antigüedad formateada.
     */
    public String antiguedadFormateada() {
        Period p = calcularAntiguedad();
        return p.getYears() + " año(s), " + p.getMonths() + " mes(es), " + p.getDays() + " día(s)";
    }

    // ── Getters / Setters

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }

    // ── equals

    /**
     * Dos objetos son iguales si tienen el mismo nombre y fecha de compra.
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si nombre y fecha de compra coinciden.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof ObjetoSubasta)) return false;
        ObjetoSubasta otro = (ObjetoSubasta) obj;
        return this.nombre != null && this.nombre.equals(otro.nombre)
                && this.fechaCompra != null && this.fechaCompra.equals(otro.fechaCompra);
    }

    // ── toString

    /**
     * Retorna una representación textual del objeto con sus datos principales.
     *
     * @return Cadena con nombre, estado y antigüedad exacta.
     */
    @Override
    public String toString() {
        return "Objeto: " + nombre
                + " | Estado: " + estado
                + " | Antigüedad: " + antiguedadFormateada();
    }
}
