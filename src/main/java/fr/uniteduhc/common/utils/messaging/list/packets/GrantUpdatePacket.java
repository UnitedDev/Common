package fr.uniteduhc.common.utils.messaging.list.packets;

import fr.uniteduhc.common.cache.rank.Grant;
import fr.uniteduhc.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GrantUpdatePacket implements Packet {

    private final Grant grant;
    private final boolean remove;

}
