package org.daviipkp.smartstevedbm;

import org.lightcouch.CouchDbClient;

import io.javalin.Javalin;

public class App {

    private static Javalin server;

    private static CouchDbClient sofa;

    private static String databaseType = "";

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
        if(databaseType.isEmpty()) {
            System.out.println("Database type is not set. Set it up!");
            Thread.sleep(5000);
            checkDatabase();
        }
        else{
            if(!anyWorkingClient()) {
                System.out.println("Database type is set to " + databaseType + " but no credentials were set. Set it up!");
                Thread.sleep(5000);
                checkDatabase();
            }else{
                System.out.println("All set! Starting check thread.");
            }
        }
    }

    private static boolean anyWorkingClient() {
        if(sofa != null) {
            return true;
        }
        return false;
    }

    public static void setupServer() {
        server = Javalin.create(config -> {
            config.routes.post("/databasetype", ctx -> {
                if(ctx.body().equals("couchdb")) {
                    databaseType = ctx.body();
                    ctx.status(201).result("Done!");
                }
            });
            
        }).start(PORT);


        System.out.println("Server is running on port " + PORT);
        
    }

}
