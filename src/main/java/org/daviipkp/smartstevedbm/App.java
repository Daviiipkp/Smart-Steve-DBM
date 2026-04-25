package org.daviipkp.smartstevedbm;

import org.lightcouch.CouchDbClient;

import io.javalin.Javalin;

public class App {

    private static Javalin server;

    private static CouchDbClient sofa;

    private static String databaseType = "couchdb";

    private static final int PORT = 8005;

    public static void main(String[] args ) {
        System.out.println("Starting...");
        setupServer();
        try {
            checkDatabase();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void checkDatabase() throws InterruptedException {
        if(databaseType == "couchdb") {
            System.out.println("Database type is couchdb but no credentials were set. Set it up!");
            Thread.sleep(5000);
            checkDatabase();
        }
    }

    public static void setupServer() {
        server = Javalin.create(config -> {
            config.routes.post("/databasetype", ctx -> {
                System.out.println(ctx.body());
            });
            
        }).start(PORT);


        System.out.println("Server is running on port " + PORT);
        
    }

}
