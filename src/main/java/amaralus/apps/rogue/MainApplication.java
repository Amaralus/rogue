package amaralus.apps.rogue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Rogue");
        primaryStage.setResizable(false);

        Pane pane = new TextFlow();
        Scene scene = new Scene(pane, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
