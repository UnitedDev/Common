package fr.kohei.common.cache.server;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CTFServer {

    private final int port;

    private final int start;
    private final int players;
    private final ServerStatus status;

    public enum ServerStatus {
        WAITING,
        PLAYING,
        ENDING
    }

}
