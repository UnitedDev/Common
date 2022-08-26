package fr.uniteduhc.common.utils.messaging.list.packets;

import fr.uniteduhc.common.cache.server.impl.LobbyServer;
import fr.uniteduhc.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LobbyUpdatePacket implements Packet {

    private final LobbyServer lobbyServer;

}
