package fr.uniteduhc.common.utils.messaging.list.packets;

import fr.uniteduhc.common.cache.data.ProfileData;
import fr.uniteduhc.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ProfileUpdatePacket implements Packet {

    private final UUID uuid;
    private final ProfileData profileData;

}

