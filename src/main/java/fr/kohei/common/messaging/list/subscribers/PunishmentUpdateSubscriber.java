package fr.kohei.common.messaging.list.subscribers;

import fr.kohei.common.RedisProvider;
import fr.kohei.common.messaging.list.packets.PunishmentUpdatePacket;
import fr.kohei.common.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.messaging.pigdin.PacketListener;

public class PunishmentUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(PunishmentUpdatePacket packet) {
        RedisProvider.redisProvider.punishments.removeIf(pd -> pd.getPunishmentId().equals(packet.getPunishmentData().getPunishmentId()));

        if (!packet.isDelete())
            RedisProvider.redisProvider.punishments.add(packet.getPunishmentData());
    }

}
