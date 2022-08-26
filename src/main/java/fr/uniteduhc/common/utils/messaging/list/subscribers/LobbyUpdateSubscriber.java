package fr.uniteduhc.common.utils.messaging.list.subscribers;

import fr.uniteduhc.common.CommonProvider;
import fr.uniteduhc.common.utils.messaging.list.packets.LobbyUpdatePacket;
import fr.uniteduhc.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.uniteduhc.common.utils.messaging.pigdin.PacketListener;

public class LobbyUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(LobbyUpdatePacket packet) {
        CommonProvider.getInstance().getServerCache().updateLobbyServer(packet.getLobbyServer());
    }

}
