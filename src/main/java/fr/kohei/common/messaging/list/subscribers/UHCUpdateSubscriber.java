package fr.kohei.common.messaging.list.subscribers;

import fr.kohei.common.RedisProvider;
import fr.kohei.common.messaging.list.packets.UHCUpdatePacket;
import fr.kohei.common.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.messaging.pigdin.PacketListener;

public class UHCUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(UHCUpdatePacket packet) {
        RedisProvider.redisProvider.getServerCache().updateUhcServer(packet.getUhcServer());
    }

}
