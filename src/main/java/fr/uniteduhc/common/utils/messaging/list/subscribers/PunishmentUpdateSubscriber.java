package fr.uniteduhc.common.utils.messaging.list.subscribers;

import fr.uniteduhc.common.CommonProvider;
import fr.uniteduhc.common.utils.messaging.list.packets.PunishmentUpdatePacket;
import fr.uniteduhc.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.uniteduhc.common.utils.messaging.pigdin.PacketListener;

public class PunishmentUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(PunishmentUpdatePacket packet) {
        CommonProvider.getInstance().punishments.removeIf(pd -> pd.getPunishmentId().equals(packet.getPunishmentData().getPunishmentId()));

        if (!packet.isDelete())
            CommonProvider.getInstance().punishments.add(packet.getPunishmentData());
    }

}
