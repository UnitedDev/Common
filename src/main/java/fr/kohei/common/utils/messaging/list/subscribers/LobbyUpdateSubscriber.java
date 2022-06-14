package fr.kohei.common.utils.messaging.list.subscribers;

import fr.kohei.common.CommonProvider;
import fr.kohei.common.utils.messaging.list.packets.LobbyUpdatePacket;
import fr.kohei.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.utils.messaging.pigdin.PacketListener;

public class LobbyUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(LobbyUpdatePacket packet) {
        CommonProvider.getInstance().getServerCache().updateLobbyServer(packet.getLobbyServer());
    }

}
