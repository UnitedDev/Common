package fr.kohei.common.cache.server;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UHCServer {

    private final int port;
    private final ServerType type;
    private final String customName;

    private final String host;
    private final int slots;
    private final ServerStatus status;
    private final int players;
    private final int teamSize;
    private final int borderStartSize;
    private final int borderEndSize;
    private final int pvpTimer;
    private final int borderTimer;
    private final List<String> enabledScenarios;
    private final List<UUID> uuids;

    @Getter
    @RequiredArgsConstructor
    public enum ServerStatus {
        FULL("&cFull"),
        CLOSED("&cWhitelist"),
        OPENED("&aOuvert"),
        PLAYING("&6En Jeu");

        private final String name;
    }

    @Getter
    @RequiredArgsConstructor
    public enum ServerType {
        MHA("MHA"),
        MUGIWARA("Mugiwara");

        private final String name;
    }
}
