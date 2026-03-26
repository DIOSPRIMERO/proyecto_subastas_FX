package proyecto_subastas.control;

import proyecto_subastas.dominio.Coleccionista;
import proyecto_subastas.dominio.Moderador;
import proyecto_subastas.dominio.ObjetoSubasta;
import proyecto_subastas.dominio.Oferta;
import proyecto_subastas.dominio.OrdenAdjudicacion;
import proyecto_subastas.dominio.Subasta;
import proyecto_subastas.dominio.Usuario;
import proyecto_subastas.dominio.Vendedor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Controlador encargado de gestionar las subastas y las ofertas de la plataforma.
 * <p>
 * Esta clase forma parte de la capa de control (lógica de negocio) y actúa
 * como intermediario entre la interfaz gráfica y los objetos del dominio.
 * Implementa todas las reglas de negocio relacionadas con la creación de subastas,
 * el registro de ofertas y la gestión de objetos subastados.
 * </p>
 *
 * <p>Reglas de negocio que aplica:</p>
 * <ul>
 *   <li>El moderador no puede crear subastas ni realizar ofertas.</li>
 *   <li>El vendedor no puede realizar ofertas.</li>
 *   <li>Una subasta debe tener al menos un objeto asociado.</li>
 *   <li>El creador de una subasta no puede ofertar en ella.</li>
 *   <li>Una oferta debe superar el precio mínimo de la subasta.</li>
 *   <li>Si el creador es un coleccionista, solo puede subastar objetos de su colección.</li>
 *   <li>Solo se pueden registrar ofertas en subastas activas.</li>
 * </ul>
 *
 * @author Steven Mendez Jimenez
 * @version 2.0
 * @see Subasta
 * @see Oferta
 * @see ObjetoSubasta
 */
public class ControladorSubasta {

    /** Lista en memoria de todas las subastas registradas en el sistema. */
    private ArrayList<Subasta> subastas;

    /**
     * Constructor por defecto.
     * Inicializa la lista de subastas vacía.
     */
    public ControladorSubasta() {
        this.subastas = new ArrayList<>();
    }

    /**
     * Crea un nuevo objeto de subasta a partir de datos primitivos.
     * <p>
     * Este método existe para que la capa de interfaz NO instancie
     * objetos de negocio directamente.
     * </p>
     *
     * @param nombre      Nombre del objeto.
     * @param descripcion Descripción del objeto.
     * @param estado      Estado del objeto ("Nuevo", "Usado", "Antiguo sin abrir").
     * @param fechaCompra Fecha exacta de compra del objeto.
     * @return Instancia de {@link ObjetoSubasta} creada con los datos proporcionados.
     */
    public ObjetoSubasta crearObjeto(String nombre, String descripcion,
                                     String estado, LocalDate fechaCompra) {
        return new ObjetoSubasta(nombre, descripcion, estado, fechaCompra);
    }

    /**
     * Crea una nueva subasta en el sistema aplicando todas las reglas de negocio.
     *
     * @param creador          Usuario que crea la subasta (debe ser vendedor o coleccionista).
     * @param objetos          Lista de objetos a incluir en la subasta.
     * @param precioMinimo     Precio mínimo de aceptación para las ofertas.
     * @param fechaVencimiento Fecha y hora límite de la subasta.
     * @return Mensaje de éxito con el ID asignado, o un mensaje de error con prefijo "ERROR:".
     */
    public String crearSubasta(Usuario creador, ArrayList<ObjetoSubasta> objetos,
                               double precioMinimo, LocalDateTime fechaVencimiento) {
        if (creador instanceof Moderador) {
            return "ERROR: El moderador no puede crear subastas.";
        }
        if (objetos == null || objetos.isEmpty()) {
            return "ERROR: La subasta debe tener al menos un objeto.";
        }
        if (creador instanceof Coleccionista) {
            Coleccionista col = (Coleccionista) creador;
            for (int i = 0; i < objetos.size(); i++) {
                if (!col.getColeccion().contains(objetos.get(i))) {
                    return "ERROR: El coleccionista solo puede subastar objetos de su colección.";
                }
            }
        }
        Subasta nueva = new Subasta(creador, objetos, precioMinimo, fechaVencimiento);
        subastas.add(nueva);
        return "Subasta #" + nueva.getId() + " creada correctamente.";
    }

    /**
     * Registra una oferta en una subasta activa, aplicando todas las reglas de negocio.
     *
     * @param idSubasta Identificador de la subasta en la que se desea ofertar.
     * @param oferente  Usuario que realiza la oferta (debe ser coleccionista).
     * @param monto     Monto económico de la oferta.
     * @return Mensaje de éxito con el monto y subasta, o un mensaje de error con prefijo "ERROR:".
     */
    public String registrarOferta(int idSubasta, Usuario oferente, double monto) {
        Subasta subasta = buscarPorId(idSubasta);
        if (subasta == null) {
            return "ERROR: No existe una subasta con ese ID.";
        }
        // Actualiza estado si ya venció
        subasta.calcularTiempoRestante();
        if (!subasta.getEstado().equals("Activa")) {
            return "ERROR: La subasta no está activa.";
        }
        if (oferente instanceof Moderador) {
            return "ERROR: El moderador no puede hacer ofertas.";
        }
        if (oferente instanceof Vendedor) {
            return "ERROR: Los vendedores no pueden hacer ofertas.";
        }
        if (subasta.getCreador().getIdentificacion().equals(oferente.getIdentificacion())) {
            return "ERROR: El creador no puede ofertar en su propia subasta.";
        }
        if (monto < subasta.getPrecioMinimo()) {
            return "ERROR: El monto es menor al precio mínimo ($" + subasta.getPrecioMinimo() + ").";
        }
        Coleccionista col = (Coleccionista) oferente;
        Oferta oferta = new Oferta(col.getNombreCompleto(), col.getPuntuacion(), monto);
        subasta.getOfertas().add(oferta);
        return "Oferta de $" + monto + " registrada en la subasta #" + idSubasta + ".";
    }

    /**
     * Genera la orden de adjudicación para una subasta, asignando al mejor oferente.
     * <p>
     * La orden solo puede generarse si la subasta tiene al menos una oferta.
     * El ganador es quien ofreció el monto más alto.
     * </p>
     *
     * @param idSubasta Identificador de la subasta a adjudicar.
     * @return La {@link OrdenAdjudicacion} generada, o {@code null} si no aplica.
     */
    public OrdenAdjudicacion generarOrden(int idSubasta) {
        Subasta subasta = buscarPorId(idSubasta);
        if (subasta == null || subasta.getOfertas().isEmpty()) return null;

        Oferta mejor = subasta.getOfertas().get(0);
        for (int i = 1; i < subasta.getOfertas().size(); i++) {
            if (subasta.getOfertas().get(i).getPrecioOfertado() > mejor.getPrecioOfertado()) {
                mejor = subasta.getOfertas().get(i);
            }
        }
        return new OrdenAdjudicacion(mejor.getNombreOferente(),
                                     subasta.getObjetos(),
                                     mejor.getPrecioOfertado());
    }

    /**
     * Busca una subasta en el sistema por su identificador único.
     *
     * @param id Identificador de la subasta a buscar.
     * @return La {@link Subasta} con ese ID, o {@code null} si no existe.
     */
    public Subasta buscarPorId(int id) {
        for (int i = 0; i < subastas.size(); i++) {
            if (subastas.get(i).getId() == id) {
                return subastas.get(i);
            }
        }
        return null;
    }

    /**
     * Genera un listado textual de todas las subastas registradas en el sistema.
     *
     * @return Cadena con la información de cada subasta, o un mensaje si no hay ninguna.
     */
    public String listarSubastas() {
        if (subastas.isEmpty()) {
            return "No hay subastas registradas.";
        }
        String lista = "";
        for (int i = 0; i < subastas.size(); i++) {
            lista += subastas.get(i).toString() + "\n";
        }
        return lista;
    }

    /**
     * Genera un listado textual de todas las ofertas registradas, agrupadas por subasta.
     *
     * @return Cadena con las ofertas de cada subasta, o un mensaje si no hay ninguna.
     */
    public String listarOfertas() {
        String lista = "";
        boolean hayOfertas = false;
        for (int i = 0; i < subastas.size(); i++) {
            Subasta s = subastas.get(i);
            if (!s.getOfertas().isEmpty()) {
                hayOfertas = true;
                lista += "-- Subasta #" + s.getId() + " --\n";
                for (int j = 0; j < s.getOfertas().size(); j++) {
                    lista += "  " + (j + 1) + ". " + s.getOfertas().get(j).toString() + "\n";
                }
            }
        }
        if (!hayOfertas) {
            return "No hay ofertas registradas.";
        }
        return lista;
    }
}
