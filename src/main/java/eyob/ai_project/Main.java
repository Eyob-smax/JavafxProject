package eyob.ai_project;

import eyob.ai_project.controller.InputController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("input.fxml"));
            Parent pane = fxmlLoader.load();
            Scene scene = new Scene(pane);

            try {
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
            } catch (NullPointerException e) {
                System.err.println("Stylesheet not found: " + e.getMessage());
            }

            try {
                InputController controller = fxmlLoader.getController();
                controller.setStage(stage);
            } catch (Exception e) {
                System.err.println("Failed to initialize controller: " + e.getMessage());
            }
            stage.setTitle("AI Question Generator");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
