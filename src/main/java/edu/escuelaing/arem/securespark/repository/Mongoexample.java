package edu.escuelaing.arem.securespark.repository;

import com.mongodb.client.MongoDatabase;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


public class Mongoexample {


        public static void main(String[] args) throws NoSuchAlgorithmException {
            MongoDatabase database = MongoUtil.getDB();
            UserDAO userDAO = new UserDAO(database);

            // Create a new user
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest("juan123".getBytes(StandardCharsets.UTF_8));
            String sha256 = DatatypeConverter.printHexBinary(digest).toLowerCase();
            userDAO.addUser("juansan@mail.com", sha256);

            userDAO.findUserByEmail("juansan@mail.com");


        }

}
