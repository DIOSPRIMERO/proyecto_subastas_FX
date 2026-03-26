package proyecto_subastas.javafx_forms;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Aplicación JavaFX que muestra el formulario de inicio de sesión
 * y el formulario de registro de clientes de la plataforma de subastas.
 *
 * @author Steven Mendez Jimenez
 * @version 1.0
 */
public class MainFX extends Application {

    private Stage ventana;

    @Override
    public void start(Stage primaryStage) {
        this.ventana = primaryStage;
        ventana.setTitle("Plataforma de Subastas");
        ventana.setResizable(false);
        ventana.setScene(crearEscenaLogin());
        ventana.show();
    }

    // ── ESCENA: INICIO DE SESIÓN ──────────────────────────────────────────────

    private Scene crearEscenaLogin() {

        Label titulo = new Label("Plataforma de Subastas");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titulo.setTextFill(Color.web("#1a237e"));

        Label subtitulo = new Label("Inicio de Sesión");
        subtitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        subtitulo.setTextFill(Color.web("#5c6bc0"));

        VBox encabezado = new VBox(4, titulo, subtitulo);
        encabezado.setAlignment(Pos.CENTER);
        encabezado.setPadding(new Insets(0, 0, 15, 0));

        Label lblId = new Label("Número de identificación:");
        TextField txtId = new TextField();
        txtId.setPromptText("Ej: 1-2345-6789");
        txtId.setPrefWidth(250);

        Label lblContrasena = new Label("Contraseña:");
        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setPromptText("Ingrese su contraseña");

        Button btnIngresar = new Button("Ingresar");
        btnIngresar.setPrefWidth(120);
        btnIngresar.setStyle("-fx-background-color: #1a237e; -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-cursor: hand;");

        Button btnRegistrarse = new Button("Registrarse");
        btnRegistrarse.setPrefWidth(120);
        btnRegistrarse.setStyle("-fx-background-color: transparent; -fx-border-color: #1a237e; "
                + "-fx-text-fill: #1a237e; -fx-cursor: hand;");

        btnRegistrarse.setOnAction(e -> ventana.setScene(crearEscenaRegistro()));

        HBox botones = new HBox(10, btnIngresar, btnRegistrarse);
        botones.setAlignment(Pos.CENTER_RIGHT);
        botones.setPadding(new Insets(10, 0, 0, 0));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(lblId,         0, 0);
        grid.add(txtId,         0, 1);
        grid.add(lblContrasena, 0, 2);
        grid.add(txtContrasena, 0, 3);
        grid.add(botones,       0, 4);

        VBox contenedor = new VBox(encabezado, grid);
        contenedor.setPadding(new Insets(30));
        contenedor.setStyle("-fx-background-color: white;");

        BorderPane raiz = new BorderPane(contenedor);
        raiz.setStyle("-fx-background-color: #e8eaf6;");
        raiz.setPadding(new Insets(20));

        return new Scene(raiz, 380, 310);
    }

    // ── ESCENA: REGISTRO DE CLIENTES ──────────────────────────────────────────

    private Scene crearEscenaRegistro() {

        Label titulo = new Label("Plataforma de Subastas");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titulo.setTextFill(Color.web("#1a237e"));

        Label subtitulo = new Label("Registro de Cliente");
        subtitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        subtitulo.setTextFill(Color.web("#5c6bc0"));

        VBox encabezado = new VBox(4, titulo, subtitulo);
        encabezado.setAlignment(Pos.CENTER);
        encabezado.setPadding(new Insets(0, 0, 15, 0));

        Label lblNombre        = new Label("Nombre completo:");
        TextField txtNombre    = new TextField();
        txtNombre.setPromptText("Ej: Juan Pérez Rodríguez");

        Label lblId            = new Label("Número de identificación:");
        TextField txtId        = new TextField();
        txtId.setPromptText("Ej: 1-2345-6789");

        Label lblCorreo        = new Label("Correo electrónico:");
        TextField txtCorreo    = new TextField();
        txtCorreo.setPromptText("usuario@correo.com");

        Label lblDireccion     = new Label("Dirección:");
        TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Ciudad, Provincia");

        Label lblAnio          = new Label("Año de nacimiento:");
        TextField txtAnio      = new TextField();
        txtAnio.setPromptText("Ej: 1995");

        Label lblContrasena    = new Label("Contraseña:");
        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setPromptText("Mínimo 6 caracteres");

        Label lblTipo          = new Label("Tipo de usuario:");
        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("Vendedor", "Coleccionista");
        cmbTipo.setValue("Vendedor");
        cmbTipo.setPrefWidth(200);

        Label lblIntereses     = new Label("Intereses (solo coleccionista):");
        TextField txtIntereses = new TextField();
        txtIntereses.setPromptText("Ej: Monedas, Arte, Figuras");
        txtIntereses.setDisable(true);

        cmbTipo.setOnAction(e -> {
            boolean esColeccionista = "Coleccionista".equals(cmbTipo.getValue());
            txtIntereses.setDisable(!esColeccionista);
        });

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.setPrefWidth(120);
        btnRegistrar.setStyle("-fx-background-color: #1a237e; -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-cursor: hand;");

        Button btnVolver = new Button("Volver al Login");
        btnVolver.setPrefWidth(120);
        btnVolver.setStyle("-fx-background-color: transparent; -fx-border-color: #1a237e; "
                + "-fx-text-fill: #1a237e; -fx-cursor: hand;");

        btnVolver.setOnAction(e -> ventana.setScene(crearEscenaLogin()));

        HBox botones = new HBox(10, btnRegistrar, btnVolver);
        botones.setAlignment(Pos.CENTER_RIGHT);
        botones.setPadding(new Insets(10, 0, 0, 0));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);

        grid.add(lblNombre,     0, 0);  grid.add(txtNombre,     1, 0);
        grid.add(lblId,         0, 1);  grid.add(txtId,         1, 1);
        grid.add(lblCorreo,     0, 2);  grid.add(txtCorreo,     1, 2);
        grid.add(lblDireccion,  0, 3);  grid.add(txtDireccion,  1, 3);
        grid.add(lblAnio,       0, 4);  grid.add(txtAnio,       1, 4);
        grid.add(lblContrasena, 0, 5);  grid.add(txtContrasena, 1, 5);
        grid.add(lblTipo,       0, 6);  grid.add(cmbTipo,       1, 6);
        grid.add(lblIntereses,  0, 7);  grid.add(txtIntereses,  1, 7);
        grid.add(botones,       0, 8, 2, 1);

        VBox contenedor = new VBox(encabezado, grid);
        contenedor.setPadding(new Insets(25));
        contenedor.setStyle("-fx-background-color: white;");

        BorderPane raiz = new BorderPane(contenedor);
        raiz.setStyle("-fx-background-color: #e8eaf6;");
        raiz.setPadding(new Insets(20));

        return new Scene(raiz, 500, 520);
    }

    /**
     * Punto de entrada de la JVM para lanzar la aplicación JavaFX.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
