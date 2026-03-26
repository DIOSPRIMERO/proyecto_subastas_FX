package proyecto_subastas.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Clase que representa una subasta publicada en la plataforma.
 * <p>
 * Una subasta agrupa uno o más objetos ({@link ObjetoSubasta}) ofrecidos por
 * un usuario creador ({@link Usuario}), con un precio mínimo de aceptación y
 * una fecha de vencimiento.
 * </p>
 *
 * @author Steven Mendez Jimenez
 * @version 2.0
 * @see ObjetoSubasta
 * @see Oferta
 * @see Usuario
 */
public class Subasta {

    /** Contador estático para asignar identificadores únicos a cada subasta. */
    private static int contadorId = 1;

    /** Identificador único de la subasta. */
    private int id;

    /** Usuario (vendedor o coleccionista) que creó la subasta. */
    private Usuario creador;

    /** Puntuación del creador al momento de crear la subasta. */
    private double puntuacionCreador;

    /** Lista de objetos incluidos en la subasta. */
    private ArrayList<ObjetoSubasta> objetos;

    /** Lista de ofertas registradas en la subasta. */
    private ArrayList<Oferta> ofertas;

    /** Precio mínimo que debe superar cualquier oferta para ser válida. */
    private double precioMinimo;

    /** Estado actual de la subasta: "Activa" o "Vencida". */
    private String estado;

    /** Fecha y hora exacta de vencimiento de la subasta. */
    private LocalDateTime fechaVencimiento;

    /**
     * Constructor por defecto.
     * Crea una subasta con ID autoincremental, listas vacías y estado "Activa".
     */
    public Subasta() {
        this.id = contadorId++;
        this.objetos = new ArrayList<>();
        this.ofertas = new ArrayList<>();
        this.estado = "Activa";
    }

    /**
     * Constructor completo que inicializa todos los atributos de la subasta.
     *
     * @param creador          Usuario que crea la subasta (vendedor o coleccionista).
     * @param objetos          Lista de objetos ofrecidos en la subasta.
     * @param precioMinimo     Precio mínimo de aceptación de ofertas.
     * @param fechaVencimiento Fecha y hora exacta de vencimiento.
     */
    public Subasta(Usuario creador, ArrayList<ObjetoSubasta> objetos,
                   double precioMinimo, LocalDateTime fechaVencimiento) {
        this.id = contadorId++;
        this.creador = creador;
        this.objetos = objetos;
        this.ofertas = new ArrayList<>();
        this.precioMinimo = precioMinimo;
        this.estado = "Activa";
        this.fechaVencimiento = fechaVencimiento;

        // Captura la puntuación del creador al momento de crear la subasta
        if (creador instanceof Vendedor) {
            this.puntuacionCreador = ((Vendedor) creador).getPuntuacion();
        } else if (creador instanceof Coleccionista) {
            this.puntuacionCreador = ((Coleccionista) creador).getPuntuacion();
        } else {
            this.puntuacionCreador = 0;
        }
    }

    /**
     * Calcula el tiempo restante para que la subasta venza.
     * Retorna un array con: [días, horas, minutos, segundos].
     * Si la subasta ya venció, todos los valores son 0 y el estado se marca como "Vencida".
     *
     * @return long[] con {días, horas, minutos, segundos} restantes.
     */
    public long[] calcularTiempoRestante() {
        if (fechaVencimiento == null) return new long[]{0, 0, 0, 0};

        LocalDateTime ahora = LocalDateTime.now();
        if (ahora.isAfter(fechaVencimiento)) {
            this.estado = "Vencida";
            return new long[]{0, 0, 0, 0};
        }

        long totalSegundos = ChronoUnit.SECONDS.between(ahora, fechaVencimiento);
        long dias    = totalSegundos / 86400;
        long horas   = (totalSegundos % 86400) / 3600;
        long minutos = (totalSegundos % 3600) / 60;
        long segundos = totalSegundos % 60;
        return new long[]{dias, horas, minutos, segundos};
    }

    /**
     * Retorna el tiempo restante como cadena legible.
     *
     * @return Cadena con formato "Xd Xh Xm Xs" o "Vencida".
     */
    public String tiempoRestanteFormateado() {
        long[] t = calcularTiempoRestante();
        if (t[0] == 0 && t[1] == 0 && t[2] == 0 && t[3] == 0) return "Vencida";
        return t[0] + "d " + t[1] + "h " + t[2] + "m " + t[3] + "s";
    }

    // ── Getters / Setters

    public int getId() { return id; }

    public Usuario getCreador() { return creador; }
    public void setCreador(Usuario creador) { this.creador = creador; }

    public double getPuntuacionCreador() { return puntuacionCreador; }
    public void setPuntuacionCreador(double puntuacionCreador) { this.puntuacionCreador = puntuacionCreador; }

    public ArrayList<ObjetoSubasta> getObjetos() { return objetos; }
    public void setObjetos(ArrayList<ObjetoSubasta> objetos) { this.objetos = objetos; }

    public ArrayList<Oferta> getOfertas() { return ofertas; }
    public void setOfertas(ArrayList<Oferta> ofertas) { this.ofertas = ofertas; }

    public double getPrecioMinimo() { return precioMinimo; }
    public void setPrecioMinimo(double precioMinimo) { this.precioMinimo = precioMinimo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    // ── equals

    /**
     * Dos subastas son iguales si tienen el mismo ID.
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si el ID coincide.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Subasta)) return false;
        Subasta otra = (Subasta) obj;
        return this.id == otra.id;
    }

    // ── toString

    /**
     * Retorna una representación textual de la subasta con sus datos principales.
     *
     * @return Cadena con ID, creador, puntuación, precio mínimo, estado, tiempo restante y cantidades.
     */
    @Override
    public String toString() {
        return "Subasta #" + id
                + " | Creador: " + creador.getNombreCompleto()
                + " | Puntuación creador: " + puntuacionCreador
                + " | Precio mínimo: $" + precioMinimo
                + " | Estado: " + estado
                + " | Tiempo restante: " + tiempoRestanteFormateado()
                + " | Vence: " + fechaVencimiento
                + " | Objetos: " + objetos.size()
                + " | Ofertas: " + ofertas.size();
    }
}
