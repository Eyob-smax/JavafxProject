package eyob.ai_project.DTO;

import java.util.Objects;

public class RequestBody {
    private String topic;
    private String difficulty;
    private int finalScore = 0;

    private static RequestBody instance;

    private RequestBody() {
    }
    public static RequestBody getInstance() {
        if(Objects.isNull(instance)){
            RequestBody.instance = new RequestBody();
            return instance;
        }else{
            return  instance;
        }
    }
    public String getTopic() {
        return topic;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void reset(){
        this.finalScore = 0;
    }
}
