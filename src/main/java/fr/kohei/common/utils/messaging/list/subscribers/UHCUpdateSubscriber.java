package fr.kohei.common.utils.messaging.list.subscribers;

import fr.kohei.common.CommonProvider;
import fr.kohei.common.utils.messaging.list.packets.UHCUpdatePacket;
import fr.kohei.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.utils.messaging.pigdin.PacketListener;

public class UHCUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(UHCUpdatePacket packet) {
        CommonProvider.getInstance().getServerCache().updateUhcServer(packet.getUhcServer());
    }

}
