package fr.kohei.common.messaging.list.subscribers;

import fr.kohei.common.RedisProvider;
import fr.kohei.common.messaging.list.packets.LobbyUpdatePacket;
import fr.kohei.common.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.messaging.pigdin.PacketListener;

public class LobbyUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(LobbyUpdatePacket packet) {
        RedisProvider.redisProvider.getServerCache().updateLobbyServer(packet.getLobbyServer());
    }

}
