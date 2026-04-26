package org.daviipkp.smartstevedbm;

import java.util.ArrayList;
import java.util.List;

import org.daviipkp.smartstevedbm.structure.implementation.CouchDBDatabase;
import org.sqlite.SQLiteDataSource;

import io.javalin.Javalin;
import io.requery.Persistable;
import io.requery.meta.EntityModel;
import io.requery.meta.EntityModelBuilder;
import io.requery.sql.Configuration;
import io.requery.sql.ConfigurationBuilder;
import io.requery.sql.EntityDataStore;
import io.requery.sql.SchemaModifier;
import io.requery.sql.TableCreationMode;

public class App {

    private static Javalin server;

    private static List<CouchDBDatabase> couchDb_databases = new ArrayList<>();
    private static final int PORT = 8005;

    public static void main(String[] args ) {
        System.out.println("Starting...");
        setupServer();
        try {
            checkDatabases();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void checkDatabases() throws InterruptedException {
        if(couchDb_databases.isEmpty()) {
            System.out.println("There is no Database set. Set it up!");
            Thread.sleep(5000);
            checkDatabases();
        }
        else{
            if(!anyWorkingClient()) {
               
            }else{
                System.out.println("All set! Starting check thread.");
            }
        }
    }

    private static boolean anyWorkingClient() {
        if(!couchDb_databases.isEmpty()) {
            return true;
        }
        return false;
    }

    public static void setupServer() {
        server = Javalin.create(config -> {
            config.routes.post("/create", ctx -> {
                if(!validName(ctx.body())) {
                    ctx.result("INVALID NAME");
                }
                couchDb_databases.add(new CouchDBDatabase(ctx.body()));
                ctx.result("DATABASE CREATED!");
            });

            
        }).start(PORT);


        System.out.println("Server is running on port " + PORT);
        
    }

    private static boolean validName(String arg0) {
        return true;
    }


    private static void setupLocalDB() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:banco_teste.db"); 

        Configuration configuration = new ConfigurationBuilder(dataSource)
                .useDefaultLogging()
                .build();
        SchemaModifier tables = new SchemaModifier(dataSource);
        tables.createTables(TableCreationMode.CREATE_NOT_EXISTS);

        EntityDataStore<Persistable> dataStore = new EntityDataStore<>(configuration);
    }

}
