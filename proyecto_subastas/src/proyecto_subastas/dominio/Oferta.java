package proyecto_subastas.dominio;

/**
 * Clase que representa una oferta económica presentada por un coleccionista en una subasta.
 *
 * @author Steven Mendez Jimenez
 * @version 2.0
 * @see Subasta
 * @see Coleccionista
 */
public class Oferta {

    /** Nombre completo del coleccionista que realiza la oferta. */
    private String nombreOferente;

    /** Puntuación de reputación del oferente en el momento de la oferta. */
    private double puntuacionOferente;

    /** Precio económico ofertado por los objetos de la subasta. */
    private double precioOfertado;

    /** Constructor por defecto. */
    public Oferta() {}

    /**
     * Constructor completo que inicializa todos los atributos de la oferta.
     *
     * @param nombreOferente      Nombre del coleccionista que oferta.
     * @param puntuacionOferente  Puntuación de reputación del oferente.
     * @param precioOfertado      Monto económico de la oferta.
     */
    public Oferta(String nombreOferente, double puntuacionOferente, double precioOfertado) {
        this.nombreOferente = nombreOferente;
        this.puntuacionOferente = puntuacionOferente;
        this.precioOfertado = precioOfertado;
    }

    // ── Getters / Setters

    public String getNombreOferente() { return nombreOferente; }
    public void setNombreOferente(String nombreOferente) { this.nombreOferente = nombreOferente; }

    public double getPuntuacionOferente() { return puntuacionOferente; }
    public void setPuntuacionOferente(double puntuacionOferente) { this.puntuacionOferente = puntuacionOferente; }

    public double getPrecioOfertado() { return precioOfertado; }
    public void setPrecioOfertado(double precioOfertado) { this.precioOfertado = precioOfertado; }

    // ── equals

    /**
     * Dos ofertas son iguales si el mismo oferente propone el mismo precio.
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si nombre del oferente y precio coinciden.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Oferta)) return false;
        Oferta otra = (Oferta) obj;
        return Double.compare(this.precioOfertado, otra.precioOfertado) == 0
                && this.nombreOferente != null && this.nombreOferente.equals(otra.nombreOferente);
    }

    // ── toString

    /**
     * Retorna una representación textual de la oferta con sus datos principales.
     *
     * @return Cadena con nombre del oferente, puntuación y monto ofertado.
     */
    @Override
    public String toString() {
        return "Oferente: " + nombreOferente
                + " | Puntuación: " + puntuacionOferente
                + " | Monto: $" + precioOfertado;
    }
}

