package fr.uniteduhc.common.utils.messaging.list.subscribers;

import fr.uniteduhc.common.CommonProvider;
import fr.uniteduhc.common.utils.messaging.list.packets.WarnUpdatePacket;
import fr.uniteduhc.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.uniteduhc.common.utils.messaging.pigdin.PacketListener;

public class WarnUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(WarnUpdatePacket packet) {
        if (CommonProvider.getInstance().getWarns().stream()
                .noneMatch(warn -> warn.getWarnId().equals(packet.getWarn().getWarnId()))) {
            CommonProvider.getInstance().getWarns().add(packet.getWarn());
        }
    }

}
