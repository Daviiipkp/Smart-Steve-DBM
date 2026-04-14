package org.daviipkp.smartstevedbm;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;

public class App {

    private static HttpClient client;
    public static void main(String[] args ) {
        
        client = HttpClient.newBuilder().authenticator(new Authenticator() {
           @Override
           protected PasswordAuthentication getPasswordAuthentication() {
               
               return new PasswordAuthentication("daviipkp", "x".toCharArray());
           } 
        }).connectTimeout(Duration.ofSeconds(10)).build();
        
        printRequest(client, "https://obsidian.daviipkp.org/_all_dbs", "daviipkp:x");
    }

    private static void printRequest(HttpClient client, String URL) {
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(URL)).header("Accept", "application/json").build();
        HttpResponse<String> resp;
        try {
            resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response : " + resp.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private static void printRequest(HttpClient client, String URL, String auth) {
        HttpRequest req = HttpRequest
        .newBuilder()
        .uri(URI.create(URL))
        .header("Accept", "application/json")
        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8)))
        .build();
        HttpResponse<String> resp;
        System.out.println(req.headers());
        try {
            resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response : " + resp.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
