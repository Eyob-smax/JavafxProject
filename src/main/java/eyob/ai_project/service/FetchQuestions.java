package eyob.ai_project.service;

import com.google.gson.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.*;

public class FetchQuestions {
    private final Gson gson = new Gson();
    private JsonArray questions;
    private JsonArray options;
    private JsonArray correctAnswers;
    private final JsonArray formattedQuestions = new JsonArray();
    private boolean isFormatted = false;

    public FetchQuestions(String topic, String difficulty) {
        HttpClient client = HttpClient.newHttpClient();
        Map<String, String> requestData = new HashMap<>();
        requestData.put("topic", topic);
        requestData.put("difficulty", difficulty);
        String bodyJson = gson.toJson(requestData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/generate"))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(bodyJson))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("Server responded with error status: " + response.statusCode());
                return;
            }
            JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();
            if (!responseObject.has("success") || !responseObject.get("success").getAsBoolean()) {
                System.err.println("Error from server: " + responseObject.get("message").getAsString());
                return;
            }
            JsonObject generatedData = responseObject.getAsJsonObject("generatedData");
            if (generatedData == null || generatedData.isEmpty()) {
                System.err.println("Generated data is missing or empty.");
                return;
            }
            questions = generatedData.getAsJsonArray("questions");
            options = generatedData.getAsJsonArray("answers");
            correctAnswers = generatedData.getAsJsonArray("correctAnswers");

            if (questions == null || options == null || correctAnswers == null) {
                System.err.println("Incomplete data received from server.");
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch questions from server:");
            e.printStackTrace();
        }
    }

    private void constructFormattedQuestions(JsonArray questions, JsonArray answers, JsonArray correctAnswers) {
        for (int i = 0; i < questions.size(); i++) {
            JsonObject questionData = new JsonObject();
            questionData.addProperty("question", questions.get(i).getAsString());
            questionData.add("answers", answers.get(i).getAsJsonArray());
            questionData.addProperty("correctAnswer", correctAnswers.get(i).getAsString());
            formattedQuestions.add(questionData);
        }
        isFormatted = true;
    }

    public JsonArray returnQuestions() {
        if (questions == null || options == null || correctAnswers == null) {
            System.err.println("Question data is incomplete or could not be fetched.");
            return null;
        }
        if (!isFormatted) {
            constructFormattedQuestions(questions, options, correctAnswers);
        }
        return formattedQuestions;
    }
}
