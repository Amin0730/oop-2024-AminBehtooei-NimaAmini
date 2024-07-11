package org.example.Tools;

import com.google.gson.Gson;
import org.example.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserJsonFileWriter {
    private static final ArrayList<User> users = new ArrayList<>();

    public static void writeUsersToFile(List<User> userList,String FILE_PATH) {
        users.addAll(userList);
        try {
            FileWriter fileWriter = new FileWriter(FILE_PATH);
            fileWriter.write('[');
            for (User user : users) {
                fileWriter.write(new Gson().toJson(user.activeCards) + ',');
            }
            fileWriter.write(']') ;
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
