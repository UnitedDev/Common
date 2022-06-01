package fr.kohei.common.cache.server;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public class LobbyServer {

    private final int port;

    private final boolean restricted;
    private final int players;
    private final HashMap<String, Integer> ranks;

}
