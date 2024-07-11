package org.example.Tools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Collections;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class RandomCardSelector {
    public static ArrayList <String> randomMakerActive() {
        try {
            String json1 = new String(Files.readAllBytes(Paths.get("ActiveCards.json")));
            ArrayList <String> stringsOfActiveCards;
            stringsOfActiveCards = new Gson().fromJson(json1,new TypeToken<List<String>>(){}.getType());
            return selectRandomMembers(stringsOfActiveCards,15);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList <String> randomMakerPassive(){
        try {
            String json2 = new String(Files.readAllBytes(Paths.get("PassiveCards.json")));
            ArrayList <String> stringsOfPassiveCards;
            stringsOfPassiveCards = new Gson().fromJson(json2,new TypeToken<List<String>>(){}.getType());
            return selectRandomMembers(stringsOfPassiveCards,5);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<String> selectRandomMembers(ArrayList<String> allMembers, int count) {
        Collections.shuffle(allMembers);
        return new ArrayList<>(allMembers.subList(0, count));
    }
}
