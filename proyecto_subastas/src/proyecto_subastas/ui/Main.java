package proyecto_subastas.ui;

import proyecto_subastas.control.ControladorSubasta;
import proyecto_subastas.control.ControladorUsuario;
import proyecto_subastas.dominio.ObjetoSubasta;
import proyecto_subastas.dominio.Usuario;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Clase principal de la interfaz gráfica de la plataforma de subastas.
 * <p>
 * Implementa el menú de usuario mediante cuadros de diálogo (JOptionPane) y
 * delega toda la lógica de negocio a los controladores correspondientes.
 * La interfaz <b>no instancia objetos de negocio directamente</b>.
 * </p>
 *
 * <p>Novedades del Avance 2:</p>
 * <ul>
 *   <li>Opción de Inicio de Sesión en el menú.</li>
 *   <li>Validación de existencia de moderador al arrancar.</li>
 * </ul>
 *
 * @author Steven Mendez Jimenez
 * @version 2.0
 * @see ControladorUsuario
 * @see ControladorSubasta
 */
public class Main {

    /** Controlador encargado de la gestión de usuarios. */
    private static ControladorUsuario ctrlUsuario = new ControladorUsuario();

    /** Controlador encargado de la gestión de subastas y ofertas. */
    private static ControladorSubasta ctrlSubasta = new ControladorSubasta();

    /** Usuario con sesión activa (null si no hay sesión iniciada). */
    private static Usuario usuarioActivo = null;

    /**
     * Método principal de la aplicación.
     * Verifica si existe un moderador registrado; si no, solicita su registro.
     * Luego despliega el menú principal en un ciclo hasta salir.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {

        //  verificar existencia de moderador
        validarModerador();

        String opcion = "";
        while (!opcion.equals("0")) {

            String sesionInfo = usuarioActivo != null
                    ? "  Sesión activa: " + usuarioActivo.getNombreCompleto() + "\n\n"
                    : "  Sin sesión activa\n\n";

            opcion = JOptionPane.showInputDialog(
                    "══════════════════════════════\n"
                            + "   PLATAFORMA DE SUBASTAS\n"
                            + "══════════════════════════════\n"
                            + sesionInfo
                            + "1. Registro de usuarios\n"
                            + "2. Listado de usuarios\n"
                            + "3. Creación de subastas\n"
                            + "4. Listado de subastas\n"
                            + "5. Creación de ofertas\n"
                            + "6. Listado de ofertas\n"
                            + "7. Inicio de sesión\n"
                            + "8. Verificar moderador\n"
                            + "0. Salir\n\n"
                            + "Seleccione una opción:"
            );

            if (opcion == null) break;

            switch (opcion.trim()) {
                case "1": menuRegistroUsuarios();                  break;
                case "2": mostrar(ctrlUsuario.listarUsuarios());   break;
                case "3": menuCrearSubasta();                      break;
                case "4": mostrar(ctrlSubasta.listarSubastas());   break;
                case "5": menuCrearOferta();                       break;
                case "6": mostrar(ctrlSubasta.listarOfertas());    break;
                case "7": menuInicioSesion();                      break;
                case "8": menuVerificarModerador();                break;
                case "0": mostrar("¡Hasta luego!");                break;
                default:  mostrar("Opción no válida. Intente de nuevo.");
            }
        }
    }

    /**
     * Verifica la existencia del moderador al iniciar la aplicación.
     * Si no existe ninguno, fuerza su registro antes de continuar.
     * (Regla de negocio #1 y #2)
     */
    private static void validarModerador() {
        if (!ctrlUsuario.existeModerador()) {
            mostrar(" No hay moderador registrado en el sistema\nDebe registrar uno antes de continuar");
            boolean listo = false;
            while (!listo) {
                String nombre  = JOptionPane.showInputDialog("Nombre completo del moderador:");
                if (nombre == null) continue;
                String id      = JOptionPane.showInputDialog("Número de cédula:");
                String correo  = JOptionPane.showInputDialog("Correo electrónico:");
                String contra  = JOptionPane.showInputDialog("Contraseña:");
                String anioStr = JOptionPane.showInputDialog("Año de nacimiento (ej: 1985):");

                try {
                    int anioNac = Integer.parseInt(anioStr);
                    String resultado = ctrlUsuario.registrarModerador(nombre, id, anioNac, contra, correo);
                    mostrar(resultado);
                    if (!resultado.startsWith("ERROR")) listo = true;
                } catch (NumberFormatException e) {
                    mostrar("ERROR: El año de nacimiento debe ser un número");
                }
            }
        }
    }

    /**
     * Opción 8 del menú: muestra si existe moderador registrado.
     */
    private static void menuVerificarModerador() {
        if (ctrlUsuario.existeModerador()) {
            mostrar("Existe un moderador registrado en el sistema");
        } else {
            mostrar(" No existe moderador registrado");
        }
    }

    /**
     * Opción 7 del menú: inicio de sesión por ID y contraseña
     */
    private static void menuInicioSesion() {
        String id     = JOptionPane.showInputDialog("Su número de identificación:");
        if (id == null) return;
        String contra = JOptionPane.showInputDialog("Contraseña:");
        if (contra == null) return;

        String resultado = ctrlUsuario.iniciarSesion(id, contra);
        if (!resultado.startsWith("ERROR")) {
            usuarioActivo = ctrlUsuario.buscarPorId(id);
            mostrar(" Sesión iniciada correctamente.\nBienvenido/a, " + usuarioActivo.getNombreCompleto() + ".");
        } else {
            mostrar(resultado);
        }
    }

    /**
     * Despliega el submenú de registro de usuarios
     */
    private static void menuRegistroUsuarios() {
        String opcion = JOptionPane.showInputDialog(
                " REGISTRO DE USUARIOS \n\n"
                        + "1. Registrar Vendedor\n"
                        + "2. Registrar Coleccionista\n"
                        + "0. Volver\n\n"
                        + "Seleccione:"
        );
        if (opcion == null) return;
        switch (opcion.trim()) {
            case "1": registrarVendedor();       break;
            case "2": registrarColeccionista();  break;
            case "0":                            break;
            default:  mostrar("Opción no válida");
        }
    }

    /** Solicita los datos necesarios para registrar un vendedor. */
    private static void registrarVendedor() {
        try {
            String nombre  = JOptionPane.showInputDialog("Nombre completo:");
            String id      = JOptionPane.showInputDialog("Número de identificación:");
            String correo  = JOptionPane.showInputDialog("Correo electrónico:");
            String contra  = JOptionPane.showInputDialog("Contraseña:");
            String dir     = JOptionPane.showInputDialog("Dirección:");
            String anioStr = JOptionPane.showInputDialog("Año de nacimiento (ej: 1995):");
            int anioNac = Integer.parseInt(anioStr);
            mostrar(ctrlUsuario.registrarVendedor(nombre, id, anioNac, contra, correo, dir));
        } catch (NumberFormatException e) {
            mostrar("ERROR: El año de nacimiento debe ser un número");
        }
    }

    /** Solicita los datos necesarios para registrar un coleccionista */
    private static void registrarColeccionista() {
        try {
            String nombre  = JOptionPane.showInputDialog("Nombre completo:");
            String id      = JOptionPane.showInputDialog("Número de identificación:");
            String correo  = JOptionPane.showInputDialog("Correo electrónico:");
            String contra  = JOptionPane.showInputDialog("Contraseña:");
            String dir     = JOptionPane.showInputDialog("Dirección:");
            String anioStr = JOptionPane.showInputDialog("Año de nacimiento (ej: 1998):");
            int anioNac = Integer.parseInt(anioStr);
            mostrar(ctrlUsuario.registrarColeccionista(nombre, id, anioNac, contra, correo, dir));
        } catch (NumberFormatException e) {
            mostrar("ERROR: El año de nacimiento debe ser un número.");
        }
    }

    /** Solicita los datos para crear una nueva subasta */
    private static void menuCrearSubasta() {
        try {
            String idCreador = JOptionPane.showInputDialog("Su ID de usuario (creador):");
            if (idCreador == null) return;

            Usuario creador = ctrlUsuario.buscarPorId(idCreador);
            if (creador == null) {
                mostrar("ERROR: No existe un usuario con ese ID");
                return;
            }

            String fechaVenc = JOptionPane.showInputDialog("Fecha de vencimiento (ej: 30/06/2026):");
            String precioStr = JOptionPane.showInputDialog("Precio mínimo ($):");
            double precioMinimo = Double.parseDouble(precioStr);

            ArrayList<ObjetoSubasta> objetos = new ArrayList<>();
            String agregarMas = "s";

            while (agregarMas != null && agregarMas.equalsIgnoreCase("s")) {
                String nombreObj = JOptionPane.showInputDialog("Nombre del objeto:");
                String descObj   = JOptionPane.showInputDialog("Descripción:");
                String anioStr   = JOptionPane.showInputDialog("Año de compra (ej: 2015):");

                String estadoOpc = JOptionPane.showInputDialog(
                        "Estado del objeto:\n"
                                + "1. Nuevo\n"
                                + "2. Usado\n"
                                + "3. Antiguo sin abrir\n\nSeleccione:"
                );

                String estado;
                switch (estadoOpc != null ? estadoOpc.trim() : "") {
                    case "1": estado = "Nuevo";            break;
                    case "2": estado = "Usado";            break;
                    case "3": estado = "Antiguo sin abrir"; break;
                    default:  estado = "Desconocido";      break;
                }

                int anioCompra = Integer.parseInt(anioStr);
                ObjetoSubasta obj = ctrlSubasta.crearObjeto(nombreObj, descObj, estado, anioCompra);
                objetos.add(obj);

                agregarMas = JOptionPane.showInputDialog("¿Agregar otro objeto? (s/n)");
            }

            mostrar(ctrlSubasta.crearSubasta(creador, objetos, precioMinimo, fechaVenc));
        } catch (NumberFormatException e) {
            mostrar("ERROR: Ingrese un valor numérico válido");
        }
    }

    /** Solicita los datos para registrar una oferta en una subasta existente */
    private static void menuCrearOferta() {
        try {
            mostrar("Subastas disponibles:\n\n" + ctrlSubasta.listarSubastas());

            String idSubastaStr = JOptionPane.showInputDialog("ID de la subasta en la que desea ofertar:");
            if (idSubastaStr == null) return;

            String idOferente = JOptionPane.showInputDialog("Su ID de usuario:");
            if (idOferente == null) return;

            Usuario oferente = ctrlUsuario.buscarPorId(idOferente);
            if (oferente == null) {
                mostrar("ERROR: No existe un usuario con ese ID.");
                return;
            }

            String montoStr = JOptionPane.showInputDialog("Monto a ofertar ($):");
            double monto = Double.parseDouble(montoStr);
            int idSubasta = Integer.parseInt(idSubastaStr);

            mostrar(ctrlSubasta.registrarOferta(idSubasta, oferente, monto));
        } catch (NumberFormatException e) {
            mostrar("ERROR: Ingrese un valor numérico válido");
        }
    }

    /**
     * Muestra un mensaje al usuario mediante un cuadro de diálogo.
     *
     * @param mensaje Texto a mostrar en el cuadro de diálogo.
     */
    private static void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}