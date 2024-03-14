package edu.escuelaing.arem.securespark;

import static spark.Spark.*;
/**
 * Hello world!
 *
 */
public class ServerFacade
{
    private static final String LOGIN_SERVICE_URL = "https://loginservice:46001/checkCredentials";
    private static final String PALINDROME_SERVICE_URL = "https://ec2-3-89-106-186.compute-1.amazonaws.com:35002/palindrome";
    public static void main( String[] args )
    {
        staticFileLocation("/public");
        secure("certificados/ecikeystore.p12", "123456", null, null);
        port(getPort());

        get("/hello", (req, res) -> "Hello World");

        get("/checkLogin", (req, res) -> {
            String answer = SecureURLReader.invokeService(LOGIN_SERVICE_URL + "?email="+ req.queryParams("email") + "&pwd=" + req.queryParams("pwd"),"myTrustStore.p12", "123456");
            if (answer.contains("401 Unauthorized")) {
                res.status(401);
                return false;
            }
            return true;
        });

        get("/palindrome", (req, res) -> {
            return SecureURLReader.invokeService(PALINDROME_SERVICE_URL + "?ispalindrome="+ req.queryParams("ispalindrome"), "myTrustStore.p12", "123456");
        });

    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 46000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
