package pojo;

import com.google.gson.JsonObject;
import firebase_connect.FirebaseHelper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import utils.JSONConverter;

import java.text.SimpleDateFormat;

public class SendQuestion extends Service<Boolean> {
    private QuestionObject questionObject;

    public SendQuestion(QuestionObject questionObject) {
        this.questionObject = questionObject;
    }

    protected Task<Boolean> createTask() {
        return new Task<>() {
            protected Boolean call() {
                new SimpleDateFormat("MM/dd/yyhhmm");
                String questionId = String.valueOf(System.currentTimeMillis());
                JsonObject jsonObject = JSONConverter.convertObjectToJson(SendQuestion.this.questionObject);
                if (jsonObject == null) {
                    return false;
                } else {
                    boolean connected = FirebaseHelper.sendQuestionToDatabase(jsonObject, questionId);
                    return connected;
                }
            }
        };
    }
}
