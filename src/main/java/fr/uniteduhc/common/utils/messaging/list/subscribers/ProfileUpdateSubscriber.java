package fr.uniteduhc.common.utils.messaging.list.subscribers;

import fr.uniteduhc.common.CommonProvider;
import fr.uniteduhc.common.utils.messaging.list.packets.ProfileUpdatePacket;
import fr.uniteduhc.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.uniteduhc.common.utils.messaging.pigdin.PacketListener;

public class ProfileUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(ProfileUpdatePacket packet) {
        CommonProvider.getInstance().players.put(packet.getUuid(), packet.getProfileData());
    }

}
