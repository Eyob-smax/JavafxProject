module eyob.ai_project {
    requires MaterialFX;
    requires java.net.http;
    requires com.google.gson;
    requires jdk.jshell;


    opens eyob.ai_project to javafx.fxml;
    exports eyob.ai_project;
    exports eyob.ai_project.controller;
    opens eyob.ai_project.controller to javafx.fxml;
}