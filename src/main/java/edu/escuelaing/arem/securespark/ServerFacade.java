package edu.escuelaing.arem.securespark;

import static spark.Spark.*;
/**
 * Hello world!
 *
 */
public class HelloWorld
{
    public static void main( String[] args )
    {
        staticFileLocation("/public");
        secure("certificados/ecikeystore.p12", "123456", null, null);
        port(getPort());
        get("/hello", (req, res) -> "Hello World");
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 45000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
