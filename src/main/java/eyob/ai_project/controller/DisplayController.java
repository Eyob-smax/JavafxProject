package eyob.ai_project.controller;

import com.google.gson.*;
import eyob.ai_project.DTO.RequestBody;
import eyob.ai_project.service.FetchQuestions;
import eyob.ai_project.service.utilities.Utils;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


import java.util.concurrent.atomic.AtomicBoolean;

public class DisplayController implements Utils {
    @FXML
    public MFXButton back_to_home;

    @FXML
    private VBox optionsContainer;

    @FXML
    private Label questionLabel;

    @FXML
    private Stage stage;

    @FXML
    private MFXButton nextButton;

    @FXML
    private MFXButton prevButton;

    private JsonArray questionData;
    private int currentIndex = 0;

    private final RequestBody requestBody = RequestBody.getInstance();

    private int finalScore = 0;

    @FXML
    public void initialize() {
        FetchQuestions fetchQuestions = new FetchQuestions(requestBody.getTopic(), requestBody.getDifficulty());
        questionData = fetchQuestions.returnQuestions();

        if (questionData != null && questionData.size() > 0) {
            displayQuestion(currentIndex);
        }

        nextButton.setOnAction(e -> {
            if (currentIndex < questionData.size() - 1) {
                currentIndex++;
                displayQuestion(currentIndex);
            }
        });

        prevButton.setOnAction(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                displayQuestion(currentIndex);
            }
        });

        optionsContainer.setAlignment(Pos.CENTER);
    }

    private void displayQuestion(int index) {
        optionsContainer.getChildren().clear();
        JsonObject questionObj = questionData.get(index).getAsJsonObject();
        String question = questionObj.get("question").getAsString();
        JsonArray answers = questionObj.getAsJsonArray("answers");
        String correctAnswer = questionObj.get("correctAnswer").getAsString();

        // Use Text and TextFlow for the question
        Text questionText = new Text((index + 1) + ". " + question);
        questionText.setStyle(" -fx-font-size: 18px; -fx-font-weight: bold;");
        TextFlow questionFlow = new TextFlow(questionText);
        questionFlow.setTextAlignment(TextAlignment.JUSTIFY);
        questionFlow.setPrefWidth(600);
        questionFlow.setPadding(new Insets(10));

        questionLabel.setGraphic(questionFlow);
        questionLabel.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        AtomicBoolean answered = new AtomicBoolean(false);

        for (JsonElement answerElement : answers) {
            String answerText = answerElement.getAsString();

            Text optionText = new Text(answerText);
            optionText.setStyle("-fx-font-size: 16px;");
            TextFlow optionFlow = new TextFlow(optionText);
            optionFlow.setTextAlignment(TextAlignment.LEFT);
            optionFlow.setMaxWidth(700);

            Button optionButton = new Button();
            optionButton.setGraphic(optionFlow);
            optionButton.setWrapText(true);
            optionButton.setMaxWidth(600);
            optionButton.setStyle("-fx-background-color: #e0e0ff; -fx-padding: 10;");
            optionButton.setAlignment(Pos.CENTER);
            optionButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            optionButton.setOnAction(e -> {
                if (answered.get()) return;
                answered.set(true);

                boolean isCorrect = checkAnswer(answerText, correctAnswer);
                if (isCorrect) {
                    finalScore++;
                    requestBody.setFinalScore(finalScore);
                    optionButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                } else {
                    optionButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                }

                optionsContainer.getChildren().forEach(node -> {
                    Button btn = (Button) node;
                    TextFlow btnFlow = (TextFlow) btn.getGraphic();
                    String btnText = ((Text) btnFlow.getChildren().get(0)).getText();
                    String processed = btnText.split(" ")[0];
                    if (processed.equals(correctAnswer)) {
                        btn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                    }
                    btn.setDisable(true);
                });

                if (currentIndex == questionData.size() - 1) {
                    Platform.runLater(() -> {
                        try {
                            Thread.sleep(1000);
                            stage = (Stage) optionButton.getScene().getWindow();
                            changeScene("/eyob/ai_project/finalScore.fxml");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                }
            });

            optionsContainer.getChildren().add(optionButton);
        }
    }

    private boolean checkAnswer(String selectedAnswer, String correctAnswer) {
        String processedAnswer = selectedAnswer.split(" ")[0];
        return processedAnswer.equals(correctAnswer);
    }

    public void backToHome() {
        try {
            stage = (Stage) back_to_home.getScene().getWindow();
            changeScene("/eyob/ai_project/input.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Can't perform an action!");
            alert.setTitle("Something went wrong, try again!");
            alert.setHeaderText("Unexpected error!");
            alert.show();
        }
    }

    @Override
    public void changeScene(String display) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(display));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
    }
}
