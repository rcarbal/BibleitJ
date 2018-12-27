package mainlayout;

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
import java.util.ResourceBundle;

public class Controller implements Initializable {
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
    private ListView<String> listview;
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
//        model = new BibleQuestionModel(this, this);
        addClickListeners();
    }

    private void addClickListeners() {
    }


    public void handleQuestionMouseClick(MouseEvent mouseEvent) {
    }

    public void handleMouseClick(MouseEvent mouseEvent) {
    }


}
