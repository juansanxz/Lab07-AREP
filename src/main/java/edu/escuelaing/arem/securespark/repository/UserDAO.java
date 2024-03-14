package edu.escuelaing.arem.securespark.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class UserDAO {

    private final MongoCollection<Document> logsCollection;

    public UserDAO(MongoDatabase database) {
        this.logsCollection = database.getCollection("logs");
    }

    public void addUser(String userEmail, String pwd) {
        Document newUser = new Document("email", userEmail)
                .append("password", pwd);
        logsCollection.insertOne(newUser);
    }

    public String findUserByEmail(String userEmail) {
        List<String> jsonLogs = new ArrayList<>();
        FindIterable<Document> logs = logsCollection.find(eq("email", userEmail));

        for (Document log : logs) {
            jsonLogs.add(log.toJson());
            System.out.println(log.toJson());
        }
        if (jsonLogs.size() == 0) {
            return null;
        }
        return jsonLogs.get(0);
    }

    public String findUserPasswordByEmail(String userEmail) {
        List<String> jsonLogs = new ArrayList<>();
        FindIterable<Document> users = logsCollection.find(eq("email", userEmail));
        String password = "";
        for (Document user : users) {
            password = user.getString("password");
            System.out.println(password);
        }
        return password;
    }
}
