package fr.uniteduhc.common.utils.messaging.list.subscribers;

import fr.uniteduhc.common.CommonProvider;
import fr.uniteduhc.common.utils.messaging.list.packets.GrantUpdatePacket;
import fr.uniteduhc.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.uniteduhc.common.utils.messaging.pigdin.PacketListener;

public class GrantUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(GrantUpdatePacket packet) {
        CommonProvider.getInstance().grants.removeIf(grant -> grant.getGrantId().equals(packet.getGrant().getGrantId()));

        if (!packet.isRemove())
            CommonProvider.getInstance().grants.add(packet.getGrant());
    }

}
