# AI Question Generator (JavaFX Desktop App)

This is a **JavaFX-based desktop application** developed as part of a class project. It uses Google's Gemini API to dynamically generate multiple-choice questions based on a topic and difficulty level entered by the user. The app is well-structured, follows best practices like the MVC architecture, and integrates several libraries and patterns to ensure scalability, maintainability, and a smooth user experience.

---

## 🚀 Project Overview

The AI Question Generator allows users to:
- Enter a topic and difficulty level
- Generate five multiple-choice questions based on the input
- Navigate between questions
- Submit answers and get a score
- Restart the quiz if desired

---

## 📁 Project Structure

ai_project_javafx/
│
├── src/
│ └── main/
│ ├── java/
│ │ └── eyob/
│ │ └── ai_project/
│ │ ├── Main.java
│ │ ├── controller/
│ │ │ └── InputController.java
│ │ ├── model/
│ │ │ ├── RequestBody.java
│ │ │ └── QuestionDTO.java
│ │ └── utils/
│ │ └── Utils.java
│ └── resources/
│ ├── input.fxml
│ ├── style.css
│ └── images/
│ └── icon.jpg
│
├── pom.xml
└── target/
└── ai_project-1.0-SNAPSHOT.jar


---

## ⚙️ Tools and Technologies Used

- **Java 21**
- **JavaFX 23.0.1**
- **Maven** – Dependency management and build tool
- **MaterialFX** – For modern, polished UI components
- **Ikonli + FontAwesome** – For beautiful icons
- **Gson** – For parsing JSON responses
- **SceneBuilder** – For designing and assembling FXML-based UIs
- **CSS** – For styling JavaFX components
- **Google Gemini API** – Backend for AI-generated questions

---

## 🧠 Design Concepts and Methodologies

I used several development techniques and architectural choices to build this app:

1. **Gson Library**  
   → Parses JSON data from the server into Java objects and arrays.

2. **Utils Interface**  
   → Contains utility methods to maintain reusability and code consistency.

3. **Singleton Pattern** for `RequestBody`  
   → Ensures the same instance is reused throughout the app, enabling consistent data flow between components.

4. **DTOs (Data Transfer Objects)**  
   → Represent the structure of data. They use private members and provide public getter/setter methods.

5. **MaterialFX Library**  
   → Provides a slick and modern user interface.

6. **FXML + SceneBuilder + CSS**  
   → Used to assemble, style, and structure the visual UI elements cleanly.

7. **Error Handling**  
   → Implemented using `try-catch` blocks in critical places like file loading and controller initialization.

8. **JavaFX Lifecycle Interfaces (e.g., Initializable)**  
   → Ensures certain code runs right after the UI loads.

9. **MVC Design Pattern**  
   - **Model**: Handles API communication and data retrieval.  
   - **View**: Built using FXML and styled with CSS.  
   - **Controller**: Manages user interactions and connects the model with the view.

10. **HTTP Communication**  
   → Built using `java.net.HttpURLConnection` to send prompts to the Gemini API and receive questions.

---

## 🔄 Application Workflow

1. User enters a topic and difficulty level.
2. Clicks on the **Generate** button.
3. App sends a prompt to the Gemini API.
4. Receives 5 dynamically generated multiple-choice questions.
5. User answers the questions.
6. The app calculates and displays the score.
7. Users can navigate between questions or restart the quiz.

---

## 🛠 How to Build & Run Locally

### ✅ Prerequisites
- JDK 21 installed and configured (`JAVA_HOME`)
- Maven installed and accessible from terminal
- JavaFX SDK downloaded

### 📦 Build

```bash
mvn clean package
