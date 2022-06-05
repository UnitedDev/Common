package fr.kohei.common.cache.server.impl;

import fr.kohei.common.cache.server.IServer;
import fr.kohei.common.cache.server.ServerType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CTFServer implements IServer {

    private final int port;

    private final int start;
    private final int players;
    private final ServerStatus status;

    @Override
    public boolean isWhitelisted() {
        return false;
    }

    @Override
    public ServerType type() {
        return ServerType.CTF;
    }

    public enum ServerStatus {
        WAITING,
        PLAYING,
        ENDING
    }

}
