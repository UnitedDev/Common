package fr.uniteduhc.common.utils.messaging.list.subscribers;

import fr.uniteduhc.common.CommonProvider;
import fr.uniteduhc.common.utils.messaging.list.packets.CTFUpdatePacket;
import fr.uniteduhc.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.uniteduhc.common.utils.messaging.pigdin.PacketListener;

public class CTFUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(CTFUpdatePacket packet) {
        CommonProvider.getInstance().getServerCache().updateCTFServer(packet.getCtfServer());
    }

}
