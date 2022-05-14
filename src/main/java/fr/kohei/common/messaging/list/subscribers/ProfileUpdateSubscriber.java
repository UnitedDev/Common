package fr.kohei.common.messaging.list.subscribers;

import fr.kohei.common.RedisProvider;
import fr.kohei.common.messaging.list.packets.ProfileUpdatePacket;
import fr.kohei.common.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.messaging.pigdin.PacketListener;

public class ProfileUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(ProfileUpdatePacket packet) {
        RedisProvider.redisProvider.players.put(packet.getUuid(), packet.getProfileData());
    }

}
