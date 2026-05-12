package org.daviipkp.smartstevedbm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.daviipkp.smartstevedbm.structure.abstraction.AbstractDatabase;
import org.daviipkp.smartstevedbm.structure.abstraction.AbstractDatabaseDTO;
import org.daviipkp.smartstevedbm.structure.abstraction.Models;
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
    private static EntityDataStore<Persistable> dataStore;

    private static List<CouchDBDatabase> couchDb_databases = new ArrayList<>();
    private static final int PORT = 8005;

    private static ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    private static long time;

    public static void main(String[] args ) {
        System.out.println("Starting...");
        setupServer();
        try {
            checkDatabases();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        time = System.currentTimeMillis();
        while (true) {
            if((System.currentTimeMillis() - time) >= 1000) {
                tick();
            }
        }
    }

    public static void tick() {
        List<AbstractDatabase> changed = checkForChanges();
        if(!changed.isEmpty() && pool.getTaskCount() == 0) {
            for(AbstractDatabase db : changed) {
                new Thread(() -> analyzeDatabase(db)).start();
            }
            clearChanges();
        }
    }

    public static List<AbstractDatabase> checkForChanges() {
        return null; //Must return all changed databases!
    }

    public static void clearChanges() {
        //Sinalizar que all the changes were caught up
    }
    
    public static void analyzeDatabase(AbstractDatabase arg0) {

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
                AbstractDatabaseDTO dto = ctx.bodyAsClass(AbstractDatabaseDTO.class);
                if(dto.type().equals("couchdb")) {
                    couchDb_databases.add(new CouchDBDatabase(dto.name(), dto.host(), dto.port(), dto.user(), dto.passwd()));
                }
            });

            
        }).start(PORT);

         
        System.out.println("Server is running on port " + PORT);
        
    }

    private static void setupLocalDB() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:main.db"); 

        EntityModel model = Models.DEFAULT; 

        Configuration configuration = new ConfigurationBuilder(dataSource, model)
                .useDefaultLogging()
                .build();
                
        SchemaModifier tables = new SchemaModifier(dataSource, model);
        tables.createTables(TableCreationMode.CREATE_NOT_EXISTS);

        dataStore = new EntityDataStore<>(configuration);
    }

}
