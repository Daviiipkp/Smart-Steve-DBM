package org.daviipkp.smartstevedbm.structure.abstraction;

public class AbstractDatabase {

    private String name;

    private String host;
    private int port;
    private String username;
    private String password;

    public AbstractDatabase(String arg0) {
        name = arg0;
    }

    public int checkIssue() {
        if(!host.isBlank()) return 0;
        if(!username.isBlank()) return 1;
        if(!password.isBlank()) return 2;
        
        return -1;
    }

    public String getHost() {
        return host;
    }


    public void setHost(String host) {
        if(host.contains(":")) {
            String a = host.split(":")[0];
            String b = host.split(":")[1];
            this.host = a;
            this.port = Integer.parseInt(b);
        } else{
            this.host = host;
        }
        
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }


}
