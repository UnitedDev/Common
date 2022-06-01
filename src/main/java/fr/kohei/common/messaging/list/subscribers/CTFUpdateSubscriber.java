package fr.kohei.common.messaging.list.subscribers;

import fr.kohei.common.RedisProvider;
import fr.kohei.common.messaging.list.packets.CTFUpdatePacket;
import fr.kohei.common.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.messaging.pigdin.PacketListener;

public class CTFUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(CTFUpdatePacket packet) {
        RedisProvider.redisProvider.getServerCache().updateCTFServer(packet.getCtfServer());
    }

}
