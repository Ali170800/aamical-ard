
        package com.amical.ard.servlets;

import com.amical.ard.dao.CaravaneDAO;
import com.amical.ard.entites.Caravane;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Properties;

@WebServlet("/etudiant/payer-caravane")
public class PayerCaravaneServlet extends HttpServlet {

    private String masterKey;
    private String privateKey;
    private String publicKey;
    private String token;

    private String successUrl;
    private String cancelUrl;
    private String callbackUrl;

    @Override
    public void init() throws ServletException {

        try {

            Properties properties =
                    new Properties();

            InputStream input =
                    getClass()
                            .getClassLoader()
                            .getResourceAsStream(
                                    "application.properties"
                            );

            if (input == null) {

                throw new ServletException(
                        "Fichier application.properties introuvable"
                );
            }

            properties.load(input);

            // =========================
            // CLÉS PAYDUNYA
            // =========================

            masterKey =
                    properties.getProperty(
                            "paydunya.master.key"
                    );

            privateKey =
                    properties.getProperty(
                            "paydunya.private.key"
                    );

            publicKey =
                    properties.getProperty(
                            "paydunya.public.key"
                    );

            token =
                    properties.getProperty(
                            "paydunya.token"
                    );

            // =========================
            // URLS
            // =========================

            successUrl =
                    properties.getProperty(
                            "paydunya.success.url"
                    );

            cancelUrl =
                    properties.getProperty(
                            "paydunya.cancel.url"
                    );

            callbackUrl =
                    properties.getProperty(
                            "paydunya.callback.url"
                    );

            System.out.println(
                    "========== CONFIG PAYDUNYA =========="
            );

            System.out.println(
                    "MASTER KEY : "
                            + masterKey
            );

            System.out.println(
                    "PRIVATE KEY : "
                            + privateKey
            );

            System.out.println(
                    "PUBLIC KEY : "
                            + publicKey
            );

            System.out.println(
                    "TOKEN : "
                            + token
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException(
                    "Erreur chargement configuration PayDunya",
                    e
            );
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // =========================
        // SESSION ÉTUDIANT
        // =========================

        Etudiant etudiant =
                (Etudiant)
                        session.getAttribute(
                                "etudiantConnecte"
                        );

        if (etudiant == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/connexionEtudiant.jsp"
            );

            return;
        }

        // =========================
        // ID CARAVANE
        // =========================

        String caravaneIdStr =
                request.getParameter(
                        "caravaneId"
                );

        EntityManager em =
                JpaUtil
                        .getEntityManagerFactory()
                        .createEntityManager();

        try {

            // =========================
            // DAO CARAVANE
            // =========================

            CaravaneDAO caravaneDAO =
                    new CaravaneDAO(em);

            Caravane caravane =
                    caravaneDAO.trouverParId(
                            Integer.parseInt(
                                    caravaneIdStr
                            )
                    );

            if (caravane == null) {

                session.setAttribute(
                        "erreur",
                        "Caravane introuvable"
                );

                response.sendRedirect(
                        request.getContextPath()
                                + "/etudiant/caravanes"
                );

                return;
            }

            // =====================================================
            // FACTURE PAYDUNYA
            // =====================================================

            JSONObject invoice =
                    new JSONObject();

            invoice.put(
                    "total_amount",
                    caravane.getMontant()
                            .doubleValue()
            );

            invoice.put(
                    "description",
                    "Paiement caravane AERD"
            );

            // =====================================================
            // STORE
            // =====================================================

            JSONObject store =
                    new JSONObject();

            store.put(
                    "name",
                    "Amicale AERD"
            );

            store.put(
                    "website_url",
                    "https://sanctity-unsent-giddiness.ngrok-free.dev/amical-ard"
            );

            store.put(
                    "return_url",
                    successUrl
            );

            store.put(
                    "cancel_url",
                    cancelUrl
            );

            store.put(
                    "callback_url",
                    callbackUrl
            );

            // =====================================================
            // CUSTOM DATA
            // =====================================================

            JSONObject customData =
                    new JSONObject();

            customData.put(
                    "etudiant_id",
                    etudiant.getId()
            );

            customData.put(
                    "caravane_id",
                    caravane.getId()
            );

            // =====================================================
            // JSON FINAL PAYDUNYA
            // =====================================================

            JSONObject payload =
                    new JSONObject();

            payload.put(
                    "invoice",
                    invoice
            );

            payload.put(
                    "store",
                    store
            );

            payload.put(
                    "custom_data",
                    customData
            );

            // =====================================================
            // DEBUG JSON
            // =====================================================

            System.out.println(
                    "========== JSON ENVOYÉ =========="
            );

            System.out.println(
                    payload.toString(2)
            );

            // =====================================================
            // REQUÊTE HTTP PAYDUNYA
            // =====================================================

            HttpClient client =
                    HttpClient.newHttpClient();

            HttpRequest req =
                    HttpRequest.newBuilder()

                            .uri(
                                    URI.create(
                                            "https://app.paydunya.com/api/v1/checkout-invoice/create"
                                    )
                            )

                            .header(
                                    "Content-Type",
                                    "application/json"
                            )

                            .header(
                                    "PAYDUNYA-MASTER-KEY",
                                    masterKey
                            )

                            .header(
                                    "PAYDUNYA-PRIVATE-KEY",
                                    privateKey
                            )

                            .header(
                                    "PAYDUNYA-PUBLIC-KEY",
                                    publicKey
                            )

                            .header(
                                    "PAYDUNYA-TOKEN",
                                    token
                            )

                            .POST(
                                    HttpRequest.BodyPublishers.ofString(
                                            payload.toString()
                                    )
                            )

                            .build();

            HttpResponse<String> resp =
                    client.send(
                            req,
                            HttpResponse.BodyHandlers.ofString()
                    );

            // =====================================================
            // DEBUG RÉPONSE
            // =====================================================

            System.out.println(
                    "========== RÉPONSE PAYDUNYA =========="
            );

            System.out.println(
                    resp.body()
            );

            JSONObject result =
                    new JSONObject(
                            resp.body()
                    );

            // =====================================================
            // SUCCÈS
            // =====================================================

            if (
                    result.has("response_code")
                            &&
                            "00".equals(
                                    result.getString(
                                            "response_code"
                                    )
                            )
            ) {

                // =================================================
                // URL DE PAIEMENT CORRECTE
                // =================================================

                String paymentUrl =
                        result.getString(
                                "response_url"
                        );

                System.out.println(
                        "PAYMENT URL = "
                                + paymentUrl
                );

                // =================================================
                // REDIRECTION PAYDUNYA
                // =================================================

                response.sendRedirect(
                        paymentUrl
                );

            } else {

                System.out.println(
                        "ERREUR PAYDUNYA = "
                                + result
                );

                session.setAttribute(
                        "erreur",
                        "PayDunya : "
                                + result.optString(
                                "description"
                        )
                );

                response.sendRedirect(
                        request.getContextPath()
                                + "/etudiant/caravanes"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            session.setAttribute(
                    "erreur",
                    "Erreur : "
                            + e.getMessage()
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/etudiant/caravanes"
            );

        } finally {

            em.close();
        }
    }
}

