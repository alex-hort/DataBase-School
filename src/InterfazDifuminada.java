import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class InterfazDifuminada extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear un botón
        Button button = new Button("Haz clic aquí");

        // Crear un efecto de difuminado
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(10); // Ajusta el radio del difuminado según lo desees

        // Aplicar el efecto de difuminado al botón
        button.setEffect(blur);

        // Crear un diseño de StackPane para la escena
        StackPane root = new StackPane();
        root.getChildren().add(button);

        // Crear la escena
        Scene scene = new Scene(root, 300, 250);
        // Configurar la escena en el escenario (Stage)
        primaryStage.setTitle("Interfaz Difuminada en JavaFX");
        primaryStage.setScene(scene);

        // Mostrar el escenario
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

