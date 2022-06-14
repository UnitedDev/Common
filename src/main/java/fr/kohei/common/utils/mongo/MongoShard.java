package fr.kohei.common.utils.mongo;

public class MongoShard {

    private final String host;
    private final int port;
    private final boolean auth;
    private final String username;
    private final String password;

    public MongoShard(String host, int port) {
        this.host = host;
        this.port = port;
        this.auth = false;
        this.username = null;
        this.password = null;
    }

    public MongoShard(String host, int port, String username, String password) {
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
