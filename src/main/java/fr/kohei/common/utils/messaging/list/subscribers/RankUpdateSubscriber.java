package fr.kohei.common.utils.messaging.list.subscribers;

import fr.kohei.common.CommonProvider;
import fr.kohei.common.utils.messaging.list.packets.RankUpdatePacket;
import fr.kohei.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.utils.messaging.pigdin.PacketListener;

public class RankUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(RankUpdatePacket packet) {
        CommonProvider.getInstance().ranks.removeIf(rank -> rank.getToken().equals(packet.getRank().getToken()));

        if(!packet.isDelete())
            CommonProvider.getInstance().ranks.add(packet.getRank());
    }

}
