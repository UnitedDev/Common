package fr.kohei.common.utils.messaging.list.subscribers;

import fr.kohei.common.CommonProvider;
import fr.kohei.common.utils.messaging.list.packets.ProfileUpdatePacket;
import fr.kohei.common.utils.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.utils.messaging.pigdin.PacketListener;

public class ProfileUpdateSubscriber implements PacketListener {

    @IncomingPacketHandler
    public void onReceive(ProfileUpdatePacket packet) {
        CommonProvider.getInstance().players.put(packet.getUuid(), packet.getProfileData());
    }

}