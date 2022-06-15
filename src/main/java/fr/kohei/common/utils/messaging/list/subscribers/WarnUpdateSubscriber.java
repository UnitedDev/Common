package fr.kohei.common.utils.messaging.list.subscribers;

import fr.kohei.common.CommonProvider;
import fr.kohei.common.cache.data.Warn;
import fr.kohei.common.utils.messaging.list.packets.ReportUpdatePacket;
import fr.kohei.common.utils.messaging.list.packets.WarnUpdatePacket;
import fr.kohei.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.utils.messaging.pigdin.PacketListener;

public class WarnUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(WarnUpdatePacket packet) {
        if (CommonProvider.getInstance().getWarns().stream()
                .noneMatch(warn -> warn.getWarnId().equals(packet.getWarn().getWarnId()))) {
            CommonProvider.getInstance().addWarn(packet.getWarn());
        }
    }

}
