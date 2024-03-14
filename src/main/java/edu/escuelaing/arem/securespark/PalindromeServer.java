package edu.escuelaing.arem.securespark;

import static spark.Spark.*;
import static spark.Spark.get;

public class PalindromeServer {
    public static void main(String... args) {
        secure("certificados/ecikeystorepal.p12", "345678", null, null);
        port(getPort());
        get("/palindrome", (req, res) -> {
            String value = req.queryParams("ispalindrome");
            String theOtherWay = new StringBuffer(value).reverse().toString();
            if (value.equals(theOtherWay)) {
                System.out.println(value + " is a palindrome");
                return value + " is a palindrome";
            }
            System.out.println(value + " is not a palindrome");
            return value + " is not a palindrome";
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 46002;
    }
}
