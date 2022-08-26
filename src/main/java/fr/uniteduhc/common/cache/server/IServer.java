package fr.uniteduhc.common.cache.server;


public interface IServer {

    boolean isWhitelisted();

    ServerType type();

}
