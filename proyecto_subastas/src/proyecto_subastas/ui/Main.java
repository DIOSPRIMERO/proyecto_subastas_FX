package proyecto_subastas.ui;

import proyecto_subastas.control.ControladorSubasta;
import proyecto_subastas.control.ControladorUsuario;
import proyecto_subastas.dominio.ObjetoSubasta;
import proyecto_subastas.dominio.OrdenAdjudicacion;
import proyecto_subastas.dominio.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Main {

    private static ControladorUsuario ctrlUsuario = new ControladorUsuario();
    private static ControladorSubasta ctrlSubasta = new ControladorSubasta();
    private static Usuario usuarioActivo = null;
    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_DATETIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
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
                            + "0. Salir\n\nSeleccione una opción:");
            if (opcion == null) break;
            switch (opcion.trim()) {
                case "1": menuRegistroUsuarios();                break;
                case "2": mostrar(ctrlUsuario.listarUsuarios()); break;
                case "3": menuCrearSubasta();                    break;
                case "4": mostrar(ctrlSubasta.listarSubastas()); break;
                case "5": menuCrearOferta();                     break;
                case "6": mostrar(ctrlSubasta.listarOfertas());  break;
                case "7": menuInicioSesion();                    break;
                case "8": menuVerificarModerador();              break;
                case "0": mostrar("¡Hasta luego!");              break;
                default:  mostrar("Opción no válida.");
            }
        }
    }

    private static void validarModerador() {
        if (!ctrlUsuario.existeModerador()) {
            mostrar("No hay moderador registrado. Debe registrar uno antes de continuar.");
            boolean listo = false;
            while (!listo) {
                String nombre   = JOptionPane.showInputDialog("Nombre completo del moderador:");
                if (nombre == null) continue;
                String id       = JOptionPane.showInputDialog("Número de cédula:");
                String correo   = JOptionPane.showInputDialog("Correo electrónico:");
                String contra   = JOptionPane.showInputDialog("Contraseña:");
                String fechaStr = JOptionPane.showInputDialog("Fecha de nacimiento (dd/MM/yyyy):");
                try {
                    LocalDate fecha = LocalDate.parse(fechaStr, FMT_FECHA);
                    String resultado = ctrlUsuario.registrarModerador(nombre, id, fecha, contra, correo);
                    mostrar(resultado);
                    if (!resultado.startsWith("ERROR")) listo = true;
                } catch (DateTimeParseException e) {
                    mostrar("ERROR: Formato de fecha inválido. Use dd/MM/yyyy.");
                }
            }
        }
    }

    private static void menuVerificarModerador() {
        mostrar(ctrlUsuario.existeModerador()
                ? "Existe un moderador registrado en el sistema."
                : "No existe moderador registrado.");
    }

    private static void menuInicioSesion() {
        String id = JOptionPane.showInputDialog("Número de identificación:");
        if (id == null) return;
        String contra = JOptionPane.showInputDialog("Contraseña:");
        if (contra == null) return;
        String resultado = ctrlUsuario.iniciarSesion(id, contra);
        if (!resultado.startsWith("ERROR")) {
            usuarioActivo = ctrlUsuario.buscarPorId(id);
            mostrar("Sesión iniciada. Bienvenido/a, " + usuarioActivo.getNombreCompleto() + ".");
        } else {
            mostrar(resultado);
        }
    }

    private static void menuRegistroUsuarios() {
        String opcion = JOptionPane.showInputDialog(
                "REGISTRO DE USUARIOS\n\n1. Registrar Vendedor\n2. Registrar Coleccionista\n0. Volver\n\nSeleccione:");
        if (opcion == null) return;
        switch (opcion.trim()) {
            case "1": registrarVendedor();      break;
            case "2": registrarColeccionista(); break;
            case "0":                           break;
            default:  mostrar("Opción no válida.");
        }
    }

    private static void registrarVendedor() {
        try {
            String nombre   = JOptionPane.showInputDialog("Nombre completo:");
            String id       = JOptionPane.showInputDialog("Número de identificación:");
            String correo   = JOptionPane.showInputDialog("Correo electrónico:");
            String contra   = JOptionPane.showInputDialog("Contraseña:");
            String dir      = JOptionPane.showInputDialog("Dirección:");
            String fechaStr = JOptionPane.showInputDialog("Fecha de nacimiento (dd/MM/yyyy):");
            LocalDate fecha = LocalDate.parse(fechaStr, FMT_FECHA);
            mostrar(ctrlUsuario.registrarVendedor(nombre, id, fecha, contra, correo, dir));
        } catch (DateTimeParseException e) {
            mostrar("ERROR: Formato de fecha inválido. Use dd/MM/yyyy.");
        }
    }

    private static void registrarColeccionista() {
        try {
            String nombre   = JOptionPane.showInputDialog("Nombre completo:");
            String id       = JOptionPane.showInputDialog("Número de identificación:");
            String correo   = JOptionPane.showInputDialog("Correo electrónico:");
            String contra   = JOptionPane.showInputDialog("Contraseña:");
            String dir      = JOptionPane.showInputDialog("Dirección:");
            String fechaStr = JOptionPane.showInputDialog("Fecha de nacimiento (dd/MM/yyyy):");
            LocalDate fecha = LocalDate.parse(fechaStr, FMT_FECHA);
            mostrar(ctrlUsuario.registrarColeccionista(nombre, id, fecha, contra, correo, dir));
        } catch (DateTimeParseException e) {
            mostrar("ERROR: Formato de fecha inválido. Use dd/MM/yyyy.");
        }
    }

    private static void menuCrearSubasta() {
        try {
            String idCreador = JOptionPane.showInputDialog("Su ID de usuario (creador):");
            if (idCreador == null) return;
            Usuario creador = ctrlUsuario.buscarPorId(idCreador);
            if (creador == null) { mostrar("ERROR: No existe un usuario con ese ID."); return; }

            String fechaStr = JOptionPane.showInputDialog("Fecha y hora de vencimiento (dd/MM/yyyy HH:mm):");
            LocalDateTime fechaVenc = LocalDateTime.parse(fechaStr, FMT_DATETIME);

            String precioStr = JOptionPane.showInputDialog("Precio mínimo ($):");
            double precioMinimo = Double.parseDouble(precioStr);

            ArrayList<ObjetoSubasta> objetos = new ArrayList<>();
            String agregarMas = "s";
            while (agregarMas != null && agregarMas.equalsIgnoreCase("s")) {
                String nombreObj     = JOptionPane.showInputDialog("Nombre del objeto:");
                String descObj       = JOptionPane.showInputDialog("Descripción:");
                String fechaCompraStr = JOptionPane.showInputDialog("Fecha de compra (dd/MM/yyyy):");
                LocalDate fechaCompra = LocalDate.parse(fechaCompraStr, FMT_FECHA);
                String estadoOpc = JOptionPane.showInputDialog(
                        "Estado:\n1. Nuevo\n2. Usado\n3. Antiguo sin abrir\n\nSeleccione:");
                String estado;
                switch (estadoOpc != null ? estadoOpc.trim() : "") {
                    case "1": estado = "Nuevo";             break;
                    case "2": estado = "Usado";             break;
                    case "3": estado = "Antiguo sin abrir"; break;
                    default:  estado = "Desconocido";       break;
                }
                objetos.add(ctrlSubasta.crearObjeto(nombreObj, descObj, estado, fechaCompra));
                agregarMas = JOptionPane.showInputDialog("¿Agregar otro objeto? (s/n)");
            }
            mostrar(ctrlSubasta.crearSubasta(creador, objetos, precioMinimo, fechaVenc));
        } catch (DateTimeParseException e) {
            mostrar("ERROR: Formato de fecha inválido.");
        } catch (NumberFormatException e) {
            mostrar("ERROR: Ingrese un valor numérico válido.");
        }
    }

    private static void menuCrearOferta() {
        try {
            mostrar("Subastas disponibles:\n\n" + ctrlSubasta.listarSubastas());
            String idSubastaStr = JOptionPane.showInputDialog("ID de la subasta:");
            if (idSubastaStr == null) return;
            String idOferente = JOptionPane.showInputDialog("Su ID de usuario:");
            if (idOferente == null) return;
            Usuario oferente = ctrlUsuario.buscarPorId(idOferente);
            if (oferente == null) { mostrar("ERROR: No existe un usuario con ese ID."); return; }
            String montoStr = JOptionPane.showInputDialog("Monto a ofertar ($):");
            double monto  = Double.parseDouble(montoStr);
            int idSubasta = Integer.parseInt(idSubastaStr);
            mostrar(ctrlSubasta.registrarOferta(idSubasta, oferente, monto));
        } catch (NumberFormatException e) {
            mostrar("ERROR: Ingrese un valor numérico válido.");
        }
    }

    private static void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}