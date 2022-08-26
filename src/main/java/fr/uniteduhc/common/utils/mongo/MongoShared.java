package fr.uniteduhc.common.utils.mongo;

public class MongoShared {

    private final String host;
    private final int port;
    private final boolean auth;
    private final String username;
    private final String password;

    public MongoShared(String host, int port) {
        this.host = host;
        this.port = port;
        this.auth = false;
        this.username = null;
        this.password = null;
    }

    public MongoShared(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.auth = true;
        this.username = username;
        this.password = password;
    }

    public String getURI() {
        if (!auth) return "mongodb://" + host + ":" + port;
        return "mongodb://" + username + ":" + password + "@" + host + ":" + port;
    }

}
