package amaralus.apps.rogue;

import amaralus.apps.rogue.services.GameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

import static javafx.scene.control.Alert.AlertType.ERROR;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final Pane pane = new TextFlow();
    private final Scene scene = new Scene(pane, 1143, 540);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Rogue");
        primaryStage.setResizable(false);

        primaryStage.setScene(scene);
        pane.setStyle("-fx-background-color: #051C25");

        new GameController(this);
        primaryStage.show();
    }

    @Override
    public void stop() {
        Platform.exit();
    }

    public void showAlert(Exception e) {
        Alert alert = new Alert(ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(e.getMessage());

        VBox stackTraceArea = new VBox();
        stackTraceArea.getChildren().addAll(
                new Label("Причина:"),
                new TextArea(getStackTrace(e))
        );

        alert.getDialogPane().setContent(stackTraceArea);
        alert.showAndWait();
    }

    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public Pane getPane() {
        return pane;
    }

    public Scene getScene() {
        return scene;
    }
}
