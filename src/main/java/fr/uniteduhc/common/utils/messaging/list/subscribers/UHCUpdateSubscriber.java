package fr.uniteduhc.common.utils.messaging.list.subscribers;

import fr.uniteduhc.common.CommonProvider;
import fr.uniteduhc.common.utils.messaging.list.packets.UHCUpdatePacket;
import fr.uniteduhc.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.uniteduhc.common.utils.messaging.pigdin.PacketListener;

public class UHCUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(UHCUpdatePacket packet) {
        CommonProvider.getInstance().getServerCache().updateUhcServer(packet.getUhcServer());
    }

}
