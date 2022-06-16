package fr.kohei.common.utils.messaging.list.packets;

import fr.kohei.common.cache.data.Warn;
import fr.kohei.common.cache.rank.Grant;
import fr.kohei.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GrantUpdatePacket implements Packet {

    private final Grant grant;
    private final boolean remove;

}
