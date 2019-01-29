package mainlayout;

import interfaces.ControllerInterface;
import interfaces.DatabaseConnectObserver;
import interfaces.FilterInterface;
import javafx.concurrent.Service;
import pojo.QuestionObject;
import pojo.SendQuestion;
import services.GetQuestionsService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BibleQuestionModel {

    private ArrayList<QuestionObject> questions = new ArrayList();
    private List<QuestionObject> keyWordFilter = new ArrayList();
    private ArrayList<DatabaseConnectObserver> observers = new ArrayList();
    private ControllerInterface controller;
    private FilterInterface filterInterface;

    public BibleQuestionModel(Controller controller, FilterInterface filterInterface) {
        this.controller = controller;
        this.filterInterface = filterInterface;
    }

    public void attach(DatabaseConnectObserver observer) {
        observers.add(observer);
    }

    public void getQuestionsFromFirebase() {
        Service<ArrayList<QuestionObject>> service = new GetQuestionsService();
        service.setOnSucceeded(e -> {
            questions = service.getValue();
            if (questions.size()>0){
                notifyAllObservers(true);
            }
        });
        service.restart();
    }

    public void notifyAllObservers(boolean conneted) {
        Iterator var2 = this.observers.iterator();

        while(var2.hasNext()) {
            DatabaseConnectObserver databaseObserver = (DatabaseConnectObserver)var2.next();
            databaseObserver.updateQuestionRetrieved(conneted);
        }

    }

    public ArrayList<QuestionObject> getQuestionsArrayList() {
        return questions;
    }

    public void sendQuestionToDatabase(QuestionObject questionObject) {
        Service<Boolean> service = new SendQuestion(questionObject);
        service.setOnSucceeded(e ->{
            boolean connected = service.getValue();
            if (connected){
                getQuestionsFromFirebase();
            }else {
                //TODO add code if question insert is unsuccessful
            }

        });
        service.restart();
    }
}

