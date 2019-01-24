package services;

import firebase_connect.FirebaseHelper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pojo.QuestionObject;
import utils.JSONConverter;

import java.util.ArrayList;

public class GetQuestionsService extends Service<ArrayList<QuestionObject>> {
    @Override
    protected Task<ArrayList<QuestionObject>> createTask() {
        return new Task<ArrayList<QuestionObject>>() {
            protected ArrayList<QuestionObject> call() throws Exception {
                String value = FirebaseHelper.getQuestions("bible-it", "test").getRawBody();
                ArrayList<QuestionObject> questionsArray = JSONConverter.jsonToQuestionArray(value);
                return questionsArray;
            }
        };
    }
}
