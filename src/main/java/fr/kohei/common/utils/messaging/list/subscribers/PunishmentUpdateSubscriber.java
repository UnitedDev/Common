package fr.kohei.common.utils.messaging.list.subscribers;

import fr.kohei.common.CommonProvider;
import fr.kohei.common.utils.messaging.list.packets.PunishmentUpdatePacket;
import fr.kohei.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.utils.messaging.pigdin.PacketListener;

public class PunishmentUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(PunishmentUpdatePacket packet) {
        CommonProvider.getInstance().punishments.removeIf(pd -> pd.getPunishmentId().equals(packet.getPunishmentData().getPunishmentId()));

        if (!packet.isDelete())
            CommonProvider.getInstance().punishments.add(packet.getPunishmentData());
    }

}
