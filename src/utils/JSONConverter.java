package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import pojo.BibleVerse;
import pojo.QuestionObject;

import java.util.*;

public class JSONConverter {
    public static ArrayList<QuestionObject> jsonToQuestionArray(String value) {
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(value).getAsJsonObject();
        ArrayList<QuestionObject> questions = new ArrayList<>();
        Set<String> keys = object.keySet();
        ArrayList<String> keyArray = new ArrayList<>(keys);

        for(int i = 0; i < keyArray.size(); i++){
            QuestionObject questionObject = new QuestionObject();
            JsonElement element = object.get((String)keyArray.get(i));
            JsonObject questionJson =  element.getAsJsonObject();

            String questionKey = keyArray.get(i);
            String questionString = questionJson.get("question").getAsString();
            String answerString = questionJson.get("answer").getAsString();
            String studyString = questionJson.get("study").getAsString();
            String inUseString = questionJson.get("in_use").getAsString();
            questionObject.addQuestionId(questionKey);
            questionObject.addQuestion(questionString);
            questionObject.addAnswer(answerString);
            questionObject.addStudy(studyString);
            questionObject.setInUse(inUseString);
            JsonArray approvalArray = questionJson.getAsJsonArray("approvals");
            Map<String, Boolean> approvals = new HashMap();
            Iterator var2 = approvalArray.iterator();

            JsonObject jsonObject;
            while (var2.hasNext()){
                JsonElement jsonElement = (JsonElement)var2.next();
                jsonObject = jsonElement.getAsJsonObject();
                ArrayList<String> key = new ArrayList(jsonObject.keySet());
                String name = (String)key.get(0);

                byte someNumber = -1;
                switch (name){
                    case "ricardo_carballo":
                        someNumber = 3;
                        break;
                    case "pastor_don":
                        someNumber=2;
                        break;
                    case "pastor_paul":
                        someNumber = 1;
                        break;
                    case "pastor_tony":
                        someNumber =0;
                }

                switch(someNumber) {
                    case 2:
                        boolean pastorDon = jsonObject.get("pastor_don").getAsBoolean();
                        approvals.put("pastor_don", pastorDon);
                        break;
                    case 1:
                        jsonObject.get("pastor_paul");
                        boolean pastorPaul = jsonObject.get("pastor_paul").getAsBoolean();
                        approvals.put("pastor_paul", pastorPaul);
                        break;
                    case 0:
                        boolean pastorTony = jsonObject.get("pastor_tony").getAsBoolean();
                        approvals.put("pastor_tony", pastorTony);
                        break;
                    case 3:
                        boolean ricardoCarballo = jsonObject.get("ricardo_carballo").getAsBoolean();
                        approvals.put("ricardo_carballo", ricardoCarballo);
                }
            }

            questionObject.addApprovals(approvals);
            questions.add(questionObject);
            JsonArray versesArray = questionJson.getAsJsonArray("verses");
            ArrayList<BibleVerse> bibleVerseArrayList = new ArrayList();
            boolean someBoolean = false;

            if (versesArray != null){
                for(int a =0; a <= versesArray.size(); a++ ) {
                    JsonElement verseElement = versesArray.get(a);
                    BibleVerse verseObject = new BibleVerse();
                    JsonObject verseJsonObject = verseElement.getAsJsonObject();
                    JsonArray jsonVerseArray = verseJsonObject.getAsJsonArray();
                    JsonElement bookElement = jsonVerseArray.get(0);
                    JsonElement chapterElement = jsonVerseArray.get(1);
                    JsonElement verseNumberElement = jsonVerseArray.get(2);
                    JsonElement scriptureElement = jsonVerseArray.get(3);
                    String book = bookElement.getAsString();
                    String chapter = chapterElement.getAsString();
                    String verse = verseNumberElement.getAsString();
                    String scripture = scriptureElement.getAsString();
                    verseObject.setBook(book);
                    verseObject.setChapter(chapter);
                    verseObject.setVerse(verse);
                    verseObject.setScripture(scripture);
                    bibleVerseArrayList.add(verseObject);
                }
                questionObject.addVerses(bibleVerseArrayList);
            }
        }
        return questions;
    }
}
