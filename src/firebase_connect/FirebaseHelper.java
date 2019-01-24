package firebase_connect;

import firebase_utils.error.JacksonUtilityException;
import firebase_utils.model.FirebaseResponse;
import firebase_utils.service.Firebase;
import net.thegreshams.firebase4j.error.FirebaseException;

import java.io.UnsupportedEncodingException;



public class FirebaseHelper {
    public static FirebaseResponse getQuestions(String s, String test) {
        String firebase_baseUrl = "https://bible-it.firebaseio.com/";
        FirebaseResponse response = null;
        Firebase firebase = null;

        try {
            firebase = new Firebase(firebase_baseUrl);
            response = firebase.get();
        } catch (UnsupportedEncodingException | JacksonUtilityException | FirebaseException var6) {
            var6.printStackTrace();
        }

        System.out.println(response);
        System.out.println("\n");
        return response;
    }
}
