package fr.kohei.common.cache;

import fr.kohei.common.RedisProvider;
import fr.kohei.common.cache.server.impl.CTFServer;
import fr.kohei.common.cache.server.impl.LobbyServer;
import fr.kohei.common.cache.server.impl.UHCServer;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class ServerCache {

    private ConcurrentHashMap<UUID, String> players;
    private final HashMap<Integer, UHCServer> uhcServers;
    private final HashMap<Integer, LobbyServer> lobbyServers;
    private final HashMap<Integer, CTFServer> ctfServers;

    public ServerCache() {
        this.players = new ConcurrentHashMap<>();
        this.uhcServers = new HashMap<>();
        this.lobbyServers = new HashMap<>();
        this.ctfServers = new HashMap<>();
    }

    public void updateUhcServer(UHCServer server) {
        this.uhcServers.put(server.getPort(), server);
    }

    public void updateLobbyServer(LobbyServer server) {
        this.lobbyServers.put(server.getPort(), server);
    }

    public void updateCTFServer(CTFServer server) {
        this.ctfServers.put(server.getPort(), server);
    }

    public void attemptDeletePort(int port) {
        this.uhcServers.remove(port);
        this.lobbyServers.remove(port);
        this.ctfServers.remove(port);
    }

    public LobbyServer findBestLobby() {

        LobbyServer toReturn = null;

        for (LobbyServer lobbyServer : lobbyServers.values()) {
            if (toReturn == null) toReturn = lobbyServer;
            else {
                if (toReturn.getPlayers() <= lobbyServer.getPlayers()) continue;
                if (lobbyServer.isRestricted()) continue;
                toReturn = lobbyServer;
            }
        }
        return toReturn;
    }

    public LobbyServer findBestLobbyFor(UUID uuid) {

        LobbyServer toReturn = null;

        if (RedisProvider.redisProvider.getProfile(uuid).getRank().permissionPower() >= 5) {
            if (lobbyServers.values().stream().anyMatch(LobbyServer::isRestricted))
                return lobbyServers.values().stream().filter(LobbyServer::isRestricted).findFirst().orElse(null);
        }
        for (LobbyServer lobbyServer : lobbyServers.values()) {
            if (toReturn == null) toReturn = lobbyServer;
            else {
                if (toReturn.getPlayers() <= lobbyServer.getPlayers()) continue;
                if (lobbyServer.isRestricted()) continue;
                toReturn = lobbyServer;
            }
        }
        return toReturn;
    }
}
