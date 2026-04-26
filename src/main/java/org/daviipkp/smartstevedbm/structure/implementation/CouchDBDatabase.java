package org.daviipkp.smartstevedbm.structure.implementation;

import org.daviipkp.smartstevedbm.structure.abstraction.AbstractDatabase;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;

public class CouchDBDatabase extends AbstractDatabase {

    private static CouchDbClient client;

    public CouchDBDatabase(String arg0) {
        super(arg0);
    }

    public void setupClient() {
        if(this.checkIssue() != -1) {
            throw new IllegalStateException("Can't setup client without all parameters defined!");
        }
        client = new CouchDbClient(new CouchDbProperties(getName(), false, "https", getHost(), getPort(), getUsername(), getPassword()));
    }

    private boolean checkConnection() {
        if(client == null) return false;
        if(client == null) return false;
        return true;
    }   

}
