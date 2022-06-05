package fr.kohei.common.cache.server;


public interface IServer {

    boolean isWhitelisted();

    ServerType type();

}
