package fr.kohei.common.utils.messaging.list.packets;

import fr.kohei.common.cache.server.impl.LobbyServer;
import fr.kohei.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LobbyUpdatePacket implements Packet {

    private final LobbyServer lobbyServer;

}
