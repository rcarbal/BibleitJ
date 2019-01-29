package mainlayout;

import interfaces.ControllerInterface;
import interfaces.DatabaseConnectObserver;
import interfaces.FilterInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import pojo.BibleVerse;
import pojo.QuestionObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable, DatabaseConnectObserver, ControllerInterface, FilterInterface {
    @FXML
    public TextField question_field;
    @FXML
    public Separator separator;
    @FXML
    public ListView verse_listview;
    @FXML
    public Button add_verse_array;
    @FXML
    public TextField book_textField;
    @FXML
    public TextField chapter_textfiled;
    @FXML
    public TextArea scripture_textarea;
    @FXML
    public TextField verse_textfiled;
    @FXML
    public Button clear_verse_field;
    @FXML
    public Button submit_question;
    @FXML
    public TextArea answer_field;
    public Separator separator2;
    @FXML
    public TextArea study_textarea;
    @FXML
    public Button clear_question;
    @FXML
    private ListView<String> questions_listview;
    private BibleQuestionModel model;
    private ObservableList list;
    private ObservableList verseList;
    private QuestionObject newQuestion;
    private QuestionObject selectedQuestionToOpen;
    private ArrayList<BibleVerse> verseArray;
    private int selectedIndex;
    private int questionIndexSelected;

    public Controller() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        separator.setOrientation(Orientation.VERTICAL);
        separator2.setOrientation(Orientation.VERTICAL);
        list = FXCollections.observableArrayList();
        verseList = FXCollections.observableArrayList();
        verseArray = new ArrayList<>();
        newQuestion = new QuestionObject();
        model = new BibleQuestionModel(this, this);
        addClickListeners();
    }

    private void addClickListeners() {
        model.attach(this);
        model.getQuestionsFromFirebase();
    }

    @FXML
    private void confirmAddQuestion() {
        if (this.question_field.getText().equals("")) {
            this.updateQuestionUi(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Submit Question");
            String s = "Click OK to submit question";
            alert.setContentText(s);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (this.selectedQuestionToOpen != null) {
//                    this.selectedQuestionToOpen.addQuestion(this.question_field.getText());
//                    this.selectedQuestionToOpen.addAnswer(this.answer_field.getText());
//                    this.selectedQuestionToOpen.addVerses(this.verseArray);
//                    this.selectedQuestionToOpen.addStudy(this.study_textarea.getText());
//                    this.model.updateQuestionToDatabase(this.selectedQuestionToOpen);
                } else {
                    QuestionObject questionObject = new QuestionObject();
                    questionObject.addQuestion(this.question_field.getText());
                    questionObject.addAnswer(this.answer_field.getText());
                    questionObject.addVerses(this.verseArray);
                    questionObject.addStudy(this.study_textarea.getText());
                    model.sendQuestionToDatabase(questionObject);
                }
            }
        }

    }

    public void addVerseToListView(MouseEvent mouseEvent) {
        BibleVerse bibleVerse = new BibleVerse();
        String book = book_textField.getText();
        bibleVerse.setBook(book);
        String chapter = chapter_textfiled.getText();
        bibleVerse.setChapter(chapter);
        String verse = verse_textfiled.getText();
        bibleVerse.setVerse(verse);
        String scripture = scripture_textarea.getText();
        bibleVerse.setScripture(scripture);

        verseArray.add(bibleVerse);
        verseList.add(scripture);
        verse_listview.getItems().clear();
        verse_listview.getItems().addAll(verseList);
        clearVerseSection();


    }

    private void clearVerseSection() {
        book_textField.clear();
        chapter_textfiled.clear();
        scripture_textarea.clear();
        verse_textfiled.clear();
    }


    public void handleQuestionMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            int index = questions_listview.getSelectionModel().getSelectedIndex();
            if (selectedQuestionToOpen != null) {
                selectedQuestionToOpen = null;
            }
            selectedQuestionToOpen = model.getQuestionsArrayList().get(index);
            loadSelectedQuestion(selectedQuestionToOpen);
        }
    }

    private void loadSelectedQuestion(QuestionObject selectedQUestionToOpen) {
        question_field.setText(selectedQUestionToOpen.getQuestion());
        answer_field.setText(selectedQUestionToOpen.getAnswer());
        study_textarea.setText(selectedQUestionToOpen.getStudy());
        verse_listview.getItems().clear();

        addVerseToListViewfromSelectedQuestion();

    }

    private void addVerseToListViewfromSelectedQuestion() {
        verse_listview.getItems().clear();
        verseArray.clear();
        verseList.clear();
        if (selectedQuestionToOpen != null) {
            Iterator verseIterator = selectedQuestionToOpen.getVerses().iterator();
            while (verseIterator.hasNext()) {
                BibleVerse bibleVerse = (BibleVerse) verseIterator.next();
                String book = bibleVerse.getBook();
                String chapter = bibleVerse.getChapter();
                String scripture = bibleVerse.getScripture();
                String verse = bibleVerse.getVerse();
                verseList.add(scripture);
                clearVerseSection();
            }

            verse_listview.getItems().clear();
            verse_listview.getItems().addAll(verseList);
        }
    }


    public void handleMouseClick(MouseEvent mouseEvent) {
    }


    @Override
    public void updateQuestionUi(Boolean var1) {

    }

    @Override
    public void questionAddedSuccessfully(boolean var1) {

    }

    @Override
    public void updateQuestionRetrieved(boolean var1) {
        if (var1) {
            question_field.clear();
            verse_listview.getItems().clear();
            list.removeAll(list);
            Iterator var2 = this.model.getQuestionsArrayList().iterator();
            while (var2.hasNext()) {
                QuestionObject questionObject = (QuestionObject) var2.next();
                list.add(questionObject.getQuestion());
            }
            questions_listview.getItems().clear();
            questions_listview.setStyle("-fx-font-size: 2.0em ;");
            questions_listview.getItems().addAll(this.list);
            answer_field.clear();
            study_textarea.clear();
            verseArray.clear();
            verseList.clear();

        }

    }

    @Override
    public void updateFilterWithList(boolean var1) {

    }

    @Override
    public void updateQuestionsNoFilter() {

    }

    public void clearQuestion(MouseEvent mouseEvent) {
        question_field.clear();
        answer_field.clear();
        verse_listview.getItems().clear();
        verseList.clear();
        verseArray.clear();
        study_textarea.clear();
        selectedQuestionToOpen = null;
        questionIndexSelected = -1;

    }

    public void cleaVerse(MouseEvent mouseEvent) {
        book_textField.clear();
        chapter_textfiled.clear();
        scripture_textarea.clear();
        verse_textfiled.clear();
    }
}
