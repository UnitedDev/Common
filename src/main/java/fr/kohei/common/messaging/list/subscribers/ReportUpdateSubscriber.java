package fr.kohei.common.messaging.list.subscribers;

import fr.kohei.common.RedisProvider;
import fr.kohei.common.messaging.list.packets.RankUpdatePacket;
import fr.kohei.common.messaging.list.packets.ReportUpdatePacket;
import fr.kohei.common.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.messaging.pigdin.PacketListener;

public class ReportUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(ReportUpdatePacket packet) {
        RedisProvider.redisProvider.updateReport(packet.getReport());
    }

}
