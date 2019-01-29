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
            JsonElement element = object.get(keyArray.get(i));
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
//
            JsonArray versesArray = questionJson.getAsJsonArray("verses");
            ArrayList<BibleVerse> bibleVerseArrayList = new ArrayList();
            boolean someBoolean = false;

            if (versesArray != null){
                for(int a =0; a < versesArray.size(); a++ ) {
                    JsonElement verseElement = versesArray.get(a);
                    BibleVerse verseObject = new BibleVerse();
                    JsonObject verseJsonObject = verseElement.getAsJsonObject();
//                    JsonArray jsonVerseArray = verseJsonObject.getAsJsonArray();
                    JsonElement bookElement = verseJsonObject.get("book");
                    JsonElement chapterElement = verseJsonObject.get("chapter");
                    JsonElement verseNumberElement = verseJsonObject.get("verse");
                    JsonElement scriptureElement = verseJsonObject.get("scripture");
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
                questions.add(questionObject);
            }
        }
        return questions;
    }

    public static JsonObject convertObjectToJson(QuestionObject questionObject) {
        JsonObject mainJson = new JsonObject();
        String question = questionObject.getQuestion();
        if (question.equals("")) {
            return null;
        } else {
            mainJson.addProperty("in_use", "");
            mainJson.addProperty("question", question);
            String answer = questionObject.getAnswer();
            if (questionObject.getAnswer() == null) {
                answer = "";
            }

            mainJson.addProperty("answer", answer);
            String study = questionObject.getStudy();
            if (study == null) {
                study = "";
            }

            mainJson.addProperty("study", study);
            boolean pastorDonApproval;
            boolean pastorPaulApproval;
            boolean pastorTonyApproval;
            boolean ricardo_carballo;
            if (questionObject.getApproval() == null) {
                pastorDonApproval = false;
                pastorPaulApproval = false;
                pastorTonyApproval = false;
                ricardo_carballo = false;
                Map<String, Boolean> approvals = new HashMap();
                approvals.put("pastor_don", pastorDonApproval);
                approvals.put("pastor_tony", pastorPaulApproval);
                approvals.put("pastor_paul", pastorTonyApproval);
                approvals.put("ricardo_carballo", ricardo_carballo);
                questionObject.addApprovals(approvals);
            }

            pastorDonApproval = questionObject.getApproval().get("pastor_don");
            pastorPaulApproval = questionObject.getApproval().get("pastor_paul");
            pastorTonyApproval = questionObject.getApproval().get("pastor_tony");
            ricardo_carballo = questionObject.getApproval().get("ricardo_carballo");
            JsonObject approvalPastorDon = new JsonObject();
            approvalPastorDon.addProperty("pastor_don", pastorDonApproval);
            JsonObject approvalPastorTony = new JsonObject();
            approvalPastorTony.addProperty("pastor_paul", pastorPaulApproval);
            JsonObject approvalPastorPaul = new JsonObject();
            approvalPastorPaul.addProperty("pastor_tony", pastorTonyApproval);
            JsonObject approvalRicardoCarballo = new JsonObject();
            approvalRicardoCarballo.addProperty("ricardo_carballo", ricardo_carballo);
            JsonArray approvalArray = new JsonArray();
            approvalArray.add(approvalPastorDon);
            approvalArray.add(approvalPastorTony);
            approvalArray.add(approvalPastorPaul);
            approvalArray.add(approvalRicardoCarballo);
            mainJson.add("approvals", approvalArray);

            ArrayList<BibleVerse> verses = null;
            if (questionObject.getVerses() == null) {
                verses = new ArrayList();
                BibleVerse verse = new BibleVerse();
                verse.addVerseScripture("");
                verse.addVerseTitle("");
                verses.add(verse);
            } else {
                verses = questionObject.getVerses();
            }

            if (verses.size() > 0) {
                JsonArray verseArray = new JsonArray();

                for(int i = 0; i < verses.size(); ++i) {
                    JsonObject jsonObject = new JsonObject();
                    BibleVerse bibleVerse = verses.get(i);
                    String book = bibleVerse.getBook();
                    String chapter = bibleVerse.getChapter();
                    String verse = bibleVerse.getVerse();
                    String scripture = bibleVerse.getScripture();

//                    JsonArray array = new JsonArray();
//                    array.add(book);
//                    array.add(chapter);
//                    array.add(verse);
//                    array.add(scripture);

                    jsonObject.addProperty("book", book);
                    jsonObject.addProperty("chapter", chapter);
                    jsonObject.addProperty("verse", verse);
                    jsonObject.addProperty("scripture", scripture);
                    verseArray.add(jsonObject);
                }

                mainJson.add("verses", verseArray);
            }
            return mainJson;
        }
    }
}
