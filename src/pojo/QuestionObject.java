package pojo;

import java.util.ArrayList;
import java.util.Map;

public class QuestionObject {
    private String questionId;
    private String answer;
    private Map<String, Boolean> approval;
    private String question;
    private String study;
    private String inUse = "";
    private ArrayList<BibleVerse> verses;

    public QuestionObject() {
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getStudy() {
        return this.study;
    }

    public String getInUse() {
        return this.inUse;
    }

    public void setInUse(String inUse) {
        this.inUse = inUse;
    }

    public ArrayList<BibleVerse> getVerses() {
        return this.verses;
    }

    public void addQuestion(String question) {
        this.question = question;
    }

    public void addAnswer(String answer) {
        this.answer = answer;
    }

    public void addVerses(ArrayList<BibleVerse> verses) {
        this.verses = verses;
    }

    public void addStudy(String study) {
        this.study = study;
    }

    public void addQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return this.question;
    }

    public void addApprovals(Map<String, Boolean> approvals) {
        this.approval = approvals;
    }

    public Map<String, Boolean> getApproval() {
        return this.approval;
    }
}
