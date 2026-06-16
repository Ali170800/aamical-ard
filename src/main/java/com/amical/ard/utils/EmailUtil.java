package com.amical.ard.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EmailUtil {

    private static final String API_KEY = System.getenv("BREVO_API_KEY");
    private static final String SENDER = System.getenv("BREVO_SENDER");

    public static void envoyerEmail(String destinataire, String sujet, String message) {

        try {
            String json = """
            {
              "sender": {
                "email": "%s"
              },
              "to": [
                {
                  "email": "%s"
                }
              ],
              "subject": "%s",
              "htmlContent": "%s"
            }
            """.formatted(
                    SENDER,
                    destinataire,
                    sujet,
                    message.replace("\n", "<br>")
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("accept", "application/json")
                    .header("api-key", API_KEY)
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("📩 Réponse Brevo : " + response.body());

        } catch (Exception e) {
            System.err.println("❌ Erreur envoi email : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
