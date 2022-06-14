package fr.kohei.common.utils.messaging.list.subscribers;

import fr.kohei.common.CommonProvider;
import fr.kohei.common.utils.messaging.list.packets.CTFUpdatePacket;
import fr.kohei.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.utils.messaging.pigdin.PacketListener;

public class CTFUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(CTFUpdatePacket packet) {
        CommonProvider.getInstance().getServerCache().updateCTFServer(packet.getCtfServer());
    }

}
