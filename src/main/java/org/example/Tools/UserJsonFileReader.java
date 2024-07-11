package org.example.Tools;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UserJsonFileReader {
    public static ArrayList<User> readUsersFromFile(String FILE_PATH) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            ArrayList<User> users = new Gson().fromJson(json, new TypeToken<ArrayList<User>>(){}.getType());
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
