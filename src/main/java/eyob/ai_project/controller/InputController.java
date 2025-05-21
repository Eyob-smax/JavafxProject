package eyob.ai_project.controller;

import eyob.ai_project.DTO.RequestBody;
import eyob.ai_project.controller.ComboItem.ComboBox;
import eyob.ai_project.service.utilities.Utils;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InputController implements Initializable, Utils {

    public MFXTextField topicInput;
    public MFXComboBox<ComboBox> difficultyComboBox;
    public MFXButton generateBtn;

    @FXML
    private VBox loadingOverlay;

    @FXML
    private StackPane mainPane;

    private Stage stage;
    private String difficulty = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        difficultyComboBox.setItems(FXCollections.observableArrayList(
                new ComboBox("1", "Easy"),
                new ComboBox("2", "Medium"),
                new ComboBox("3", "Difficult")
        ));
        difficultyComboBox.setValue(new ComboBox("2", "Medium"));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void generateQuestions() {
        String topic = topicInput.getText();
        stage = (Stage) difficultyComboBox.getScene().getWindow();
        String difficultyLevel = difficulty;

        if (difficultyLevel.isEmpty() || topic.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Topic or difficulty fields should not be empty!");
            alert.setTitle("Invalid data");
            alert.setHeaderText("Incomplete data");
            alert.show();
        } else {
            loadingOverlay.setVisible(true); // Show loader

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(1500); // Simulate fetch time
                    return null;
                }

                @Override
                protected void succeeded() {
                    Platform.runLater(() -> {
                        try {
                            loadingOverlay.setVisible(false);
                            RequestBody requestBody = RequestBody.getInstance();
                            requestBody.setTopic(topic);
                            requestBody.setDifficulty(difficulty);
                            changeScene("/eyob/ai_project/display.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            };

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void selectDifficulty(ActionEvent actionEvent) {
        ComboBox item = difficultyComboBox.getSelectedItem();
        difficultyComboBox.setPromptText(item.getLabel());
        difficulty = item.getLabel();
    }

    @Override
    public void changeScene(String display) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(display));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }
}
