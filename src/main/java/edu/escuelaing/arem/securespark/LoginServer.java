package edu.escuelaing.arem.securespark;

import com.mongodb.client.MongoDatabase;
import edu.escuelaing.arem.securespark.repository.MongoUtil;
import edu.escuelaing.arem.securespark.repository.UserDAO;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;

import static spark.Spark.*;

public class LoginServer {
    public static void main( String[] args )
    {
        secure("certificados/ecikeystorelogin.p12", "234567", null, null);
        port(getPort());
        MongoDatabase database = MongoUtil.getDB();
        UserDAO userDAO = new UserDAO(database);

        // Habilitar CORS para todas las solicitudes

        get("/checkCredentials", (req, res) -> {
            Date savingTime = new Date();
            res.type("application/json");
            if (userDAO.findUserByEmail(req.queryParams("email")) == null) {
                res.status(401);
                return false;
            } else {
                String pwDb = userDAO.findUserPasswordByEmail(req.queryParams("email"));
                MessageDigest md = MessageDigest.getInstance("SHA-256");

                byte[] digestQuery = md.digest(req.queryParams("pwd").getBytes(StandardCharsets.UTF_8));
                String hashQuery = DatatypeConverter.printHexBinary(digestQuery).toLowerCase();


                if (pwDb.equals(hashQuery)) {
                    return true;
                } else {
                    res.status(401);
                    return false;
                }
            }
        });


    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 46001;
    }
}
