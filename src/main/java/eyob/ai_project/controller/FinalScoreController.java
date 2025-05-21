package eyob.ai_project.controller;

import eyob.ai_project.DTO.RequestBody;
import eyob.ai_project.service.utilities.Utils;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FinalScoreController implements Initializable, Utils {

    @FXML
    private Label finalScoreLabel;

    @FXML
    private MFXButton restartBtn;

    @FXML
    private MFXButton backBtn;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int score = RequestBody.getInstance().getFinalScore();
        finalScoreLabel.setText("Your Final Score: " + score);
    }

    @FXML
    public void handleRestart(ActionEvent event) throws IOException {
        stage = (Stage) restartBtn.getScene().getWindow();
        RequestBody.getInstance().reset();
        changeScene("/eyob/ai_project/input.fxml");
    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        stage = (Stage) backBtn.getScene().getWindow();
        changeScene("/eyob/ai_project/display.fxml");
    }

    @Override
    public void changeScene(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }
}
