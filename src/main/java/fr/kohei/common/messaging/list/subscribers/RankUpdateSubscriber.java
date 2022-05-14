package fr.kohei.common.messaging.list.subscribers;

import fr.kohei.common.RedisProvider;
import fr.kohei.common.messaging.list.packets.ProfileUpdatePacket;
import fr.kohei.common.messaging.list.packets.RankUpdatePacket;
import fr.kohei.common.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.messaging.pigdin.PacketListener;

public class RankUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(RankUpdatePacket packet) {
        RedisProvider.redisProvider.ranks.removeIf(rank -> rank.getToken().equals(packet.getRank().getToken()));

        if(!packet.isDelete())
            RedisProvider.redisProvider.ranks.add(packet.getRank());
    }

}
